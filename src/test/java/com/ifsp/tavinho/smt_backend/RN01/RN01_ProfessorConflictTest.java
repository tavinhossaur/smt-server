package com.ifsp.tavinho.smt_backend.RN01;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.ifsp.tavinho.smt_backend.application.services.admin.EventService;
import com.ifsp.tavinho.smt_backend.domain.dtos.input.entities.EventDTO;
import com.ifsp.tavinho.smt_backend.domain.entities.Classroom;
import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.entities.Discipline;
import com.ifsp.tavinho.smt_backend.domain.entities.Event;
import com.ifsp.tavinho.smt_backend.domain.entities.Professor;
import com.ifsp.tavinho.smt_backend.domain.enums.Weekday;
import com.ifsp.tavinho.smt_backend.domain.repositories.ClassroomRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.DisciplineRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.EventRepository;
import com.ifsp.tavinho.smt_backend.domain.repositories.ProfessorRepository;
import com.ifsp.tavinho.smt_backend.domain.usecases.event.CreateEventUseCase;
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes para a regra de negócio RN01:
 * Professores só podem estar alocados a um único evento em um mesmo dia da semana/horário.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RN01 - Validação de Conflito de Horário de Professores")
class RN01_ProfessorConflictTest {

    @Mock
    private EventRepository eventRepository;
    
    @Mock
    private ClassroomRepository classroomRepository;
    
    @Mock
    private ProfessorRepository professorRepository;
    
    @Mock
    private CourseRepository courseRepository;
    
    @Mock
    private DisciplineRepository disciplineRepository;

    @Mock
    private CreateEventUseCase createEventUseCase;

    @InjectMocks
    private EventService eventService;

    private EventDTO eventDTO;
    private Professor professor;
    private Classroom classroom;
    private Course course;
    private Discipline discipline;

    @BeforeEach
    void setUp() {
        professor = new Professor("Albert Wesker", "albert.wesker@umbrella.com");
        professor.setId("prof123");

        classroom = new Classroom("T401", "T", "4", 40, "Sala teste");
        classroom.setId("class123");

        course = new Course("T-Virus 101", "V101");
        course.setId("course123");

        discipline = new Discipline("Moda 1", "MD1", "course123");
        discipline.setId("disc123");

        eventDTO = new EventDTO(
            "Introdução ao Gotejamento",
            Weekday.MONDAY,
            "08:00",
            "10:00",
            "class123",
            "prof123",
            "disc123",
            "course123"
        );

        // Mock padrão para os repositórios
        // Retorna um 'Optional' só para garantir a execução e evitar uma exception presente no CreateEventUseCase
        // que verifica se o ID da entidade relacionada ao evento já existe no banco.
        when(classroomRepository.findById(anyString())).thenReturn(Optional.of(classroom));
        when(professorRepository.findById(anyString())).thenReturn(Optional.of(professor));
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        when(disciplineRepository.findById(anyString())).thenReturn(Optional.of(discipline));
    }

    @Test
    @DisplayName("Deve permitir criar evento quando professor não tem conflito de horário")
    void shouldAllowEventCreationWhenNoProfessorConflict() {
        
        // Arrange
        // Retorna sempre um array vazio de eventos relacionados aquele professor, mesmo que o professor
        // tivesse de fato, eventos cadastrados e relacionados a ele.
        when(eventRepository.findByProfessorId(anyString())).thenReturn(Arrays.asList());

        // Cria uma entidade do evento que o banco retornaria
        Event savedEvent = new Event(
            eventDTO.description(),
            eventDTO.weekday(),
            LocalTime.parse(eventDTO.startTime()),
            LocalTime.parse(eventDTO.endTime()),
            eventDTO.classroomId(),
            eventDTO.professorId(),
            eventDTO.disciplineId(),
            eventDTO.courseId()
        );

        // Quando ele chamar o método para registrar no banco, ele retorna a entidade criada acima
        when(createEventUseCase.execute(any(EventDTO.class))).thenReturn(savedEvent);

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        assertEquals(eventDTO.professorId(), result.getProfessorId());
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class)); // Verifica se chamou o método execute uma única vez
    }

    @Test
    @DisplayName("Deve lançar exceção quando professor tem conflito de horário no mesmo dia")
    void shouldThrowExceptionWhenProfessorHasConflictSameTime() {

        // Arrange
        Event existingEvent = new Event(
            "Aula Existente",
            Weekday.MONDAY,
            LocalTime.parse("08:00"),
            LocalTime.parse("10:00"),
            "class456",
            "prof123",
            "disc456",
            "course123"
        );

        when(eventRepository.findByProfessorId("prof123")).thenReturn(Arrays.asList(existingEvent));

        // Act & Assert
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertTrue(exception.getMessage().equals("This professor is already attached to an event at the desired day and time."));
        verify(createEventUseCase, never()).execute(any(EventDTO.class)); // Verifica se nunca chamou o método execute do createEventUseCase
    }

    @Test
    @DisplayName("Deve lançar exceção quando professor tem conflito com sobreposição parcial no horário de início")
    void shouldThrowExceptionWhenClassroomHasPartialConflictAtStart() {

        // Arrange - evento existente de 07:00 às 09:00
        Event existingEvent = new Event(
            "Aula Existente",
            Weekday.MONDAY,
            LocalTime.parse("07:00"),
            LocalTime.parse("09:00"),
            "class456",
            "prof123",
            "disc456",
            "course123"
        );
        
        // Retorna um array com o evento existente criado anteriormente
        when(eventRepository.findByProfessorId("prof123")).thenReturn(Arrays.asList(existingEvent));
        
        // Act & Assert
        // Tentando criar evento de 08:00 às 10:00
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertTrue(exception.getMessage().equals("This professor is already attached to an event at the desired day and time."));
        verify(createEventUseCase, never()).execute(any(EventDTO.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando professor tem conflito com sobreposição parcial no horário de fim")
    void shouldThrowExceptionWhenClassroomHasPartialConflictAtEnd() {

        // Arrange - evento existente de 07:00 às 09:00
        Event existingEvent = new Event(
            "Aula Existente",
            Weekday.MONDAY,
            LocalTime.parse("09:00"),
            LocalTime.parse("11:00"),
            "class456",
            "prof123",
            "disc456",
            "course123"
        );
        
        when(eventRepository.findByProfessorId("prof123")).thenReturn(Arrays.asList(existingEvent));
        
        // Act & Assert
        // Tentando criar evento de 08:00 às 10:00
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertTrue(exception.getMessage().equals("This professor is already attached to an event at the desired day and time."));
        verify(createEventUseCase, never()).execute(any(EventDTO.class));
    }

    @Test
    @DisplayName("Deve permitir criar evento para o professor em dia diferente mesmo com horário igual")
    void shouldAllowEventCreationOnDifferentDaySameTime() {

        // Arrange - evento existente na segunda-feira
        Event existingEvent = new Event(
            "Aula Existente",
            Weekday.TUESDAY, // Dia diferente
            LocalTime.parse("08:00"),
            LocalTime.parse("10:00"),
            "class456",
            "prof123",
            "disc456",
            "course123"
        );

        when(eventRepository.findByProfessorId("prof123")).thenReturn(Arrays.asList(existingEvent));
        
        Event savedEvent = new Event(
            eventDTO.description(),
            eventDTO.weekday(),
            LocalTime.parse(eventDTO.startTime()),
            LocalTime.parse(eventDTO.endTime()),
            eventDTO.classroomId(),
            eventDTO.professorId(),
            eventDTO.disciplineId(),
            eventDTO.courseId()
        );
        
        when(createEventUseCase.execute(any(EventDTO.class))).thenReturn(savedEvent);

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class));
    }

    @Test
    @DisplayName("Deve permitir criar evento em horário imediatamente após sem sobreposição")
    void shouldAllowEventCreationImmediatelyAfter() {

        // Arrange - evento existente de 06:00 às 08:00
        Event existingEvent = new Event(
            "Aula Existente",
            Weekday.MONDAY,
            LocalTime.parse("06:00"),
            LocalTime.parse("08:00"), // Termina quando o novo (eventDTO) começa
            "class456",
            "prof123",
            "disc456",
            "course123"
        );

        when(eventRepository.findByProfessorId("prof123")).thenReturn(Arrays.asList(existingEvent));
        
        Event savedEvent = new Event(
            eventDTO.description(),
            eventDTO.weekday(),
            LocalTime.parse(eventDTO.startTime()),
            LocalTime.parse(eventDTO.endTime()),
            eventDTO.classroomId(),
            eventDTO.professorId(),
            eventDTO.disciplineId(),
            eventDTO.courseId()
        );

        when(createEventUseCase.execute(any(EventDTO.class))).thenReturn(savedEvent);

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class));
    }

        @Test
    @DisplayName("Deve permitir criar evento em horário imediatamente antes sem sobreposição")
    void shouldAllowEventCreationImmediatelyBefore() {

        // Arrange - evento existente de 06:00 às 08:00
        Event existingEvent = new Event(
            "Aula Existente",
            Weekday.MONDAY,
            LocalTime.parse("10:00"), // Inícia quando o novo (eventDTO) termina
            LocalTime.parse("12:00"),
            "class456",
            "prof123",
            "disc456",
            "course123"
        );

        when(eventRepository.findByProfessorId("prof123")).thenReturn(Arrays.asList(existingEvent));
        
        Event savedEvent = new Event(
            eventDTO.description(),
            eventDTO.weekday(),
            LocalTime.parse(eventDTO.startTime()),
            LocalTime.parse(eventDTO.endTime()),
            eventDTO.classroomId(),
            eventDTO.professorId(),
            eventDTO.disciplineId(),
            eventDTO.courseId()
        );

        when(createEventUseCase.execute(any(EventDTO.class))).thenReturn(savedEvent);

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        verify(createEventUseCase, times(1)).execute(any(EventDTO.class));
    }
}