package com.ifsp.tavinho.smt_backend.RN02;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

import com.ifsp.tavinho.smt_backend.application.dtos.input.EventDTO;
import com.ifsp.tavinho.smt_backend.application.services.admin.EventService;
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
import com.ifsp.tavinho.smt_backend.shared.errors.AppError;

/**
 * Testes para a regra de negócio RN02:
 * Salas só podem estar alocados a um único evento em um mesmo dia da semana/horário.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RN02 - Validação de Conflito de Horário de Salas")
class RN02_ClassroomConflictTest {

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

    @InjectMocks
    private EventService eventService;

    private EventDTO eventDTO;
    private Professor professor;
    private Classroom classroom;
    private Course course;
    private Discipline discipline;

    @BeforeEach
    void setUp() {
        professor = new Professor("Ada Wong", "ada.wong@umbrella.com");
        professor.setId("prof456");

        classroom = new Classroom("T402", "T", "4", 40, "Sala teste");
        classroom.setId("class789");

        course = new Course("T-Virus 101", "V101");
        course.setId("course456");

        discipline = new Discipline("Arte da Mentira", "ATM", "course456");
        discipline.setId("disc789");

        eventDTO = new EventDTO(
            "Introdução a Lábia",
            Weekday.WEDNESDAY,
            "14:00",
            "16:00",
            "class789",
            "prof456",
            "disc789",
            "course456"
        );

        // Mock padrão para os repositórios
        // Retorna um 'Optional' só para garantir a execução e evitar um lançamento de exception no EventService
        when(classroomRepository.findById(anyString())).thenReturn(Optional.of(classroom));
        when(professorRepository.findById(anyString())).thenReturn(Optional.of(professor));
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        when(disciplineRepository.findById(anyString())).thenReturn(Optional.of(discipline));
    }

    @Test
    @DisplayName("Deve permitir criar evento quando sala não tem conflito de horário")
    void shouldAllowEventCreationWhenNoClassroomConflict() {

        // Arrange
        // Simula que não há eventos existentes para essa sala retornando um array vazio
        when(eventRepository.findByClassroomId(anyString())).thenReturn(Arrays.asList());

        // Simula o retorno da entidade do banco pegando o mesmo argumento enviado no método
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        assertEquals(eventDTO.classroomId(), result.getClassroomId());
        verify(eventRepository, times(1)).save(any(Event.class)); // Verifica que o save foi chamado uma vez
    }

    @Test
    @DisplayName("Deve lançar exceção quando sala tem conflito de horário no mesmo dia")
    void shouldThrowExceptionWhenClassroomHasExactConflict() {

        // Arrange
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("14:00"),
            LocalTime.parse("16:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));

        // Act & Assert
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertTrue(exception.getMessage().equals("This classroom is already attached to an event at the desired day and time."));
        verify(eventRepository, never()).save(any(Event.class)); // Verifica se nunca chamou o método save do repository
    }

    @Test
    @DisplayName("Deve lançar exceção quando sala tem conflito com sobreposição parcial no horário de início")
    void shouldThrowExceptionWhenClassroomHasPartialConflictAtStart() {

        // Arrange - evento existente de 13:00 às 15:00
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("13:00"),
            LocalTime.parse("15:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));
        
        // Act & Assert
        // Tentando criar evento de 14:00 às 16:00
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando sala tem conflito com sobreposição parcial no horário de fim")
    void shouldThrowExceptionWhenClassroomHasPartialConflictAtEnd() {

        // Arrange - evento existente de 15:00 às 17:00
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("15:00"),
            LocalTime.parse("17:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));
        
        // Act & Assert
        // Tentando criar evento de 14:00 às 16:00
        AppError exception = assertThrows(AppError.class, () -> {
            eventService.create(eventDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @DisplayName("Deve permitir criar evento com a sala em dia diferente mesmo com horário igual")
    void shouldAllowEventCreationOnDifferentDaySameTime() {

        // Arrange - evento existente na terça-feira
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.TUESDAY, // Dia diferente
            LocalTime.parse("14:00"),
            LocalTime.parse("16:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));

        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("Deve permitir criar evento em horário imediatamente após sem sobreposição")
    void shouldAllowEventCreationImmediatelyAfter() {

        // Arrange - evento existente de 12:00 às 14:00
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("12:00"),
            LocalTime.parse("14:00"), // Termina quando o novo (eventDTO) começa
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));

        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

        @Test
    @DisplayName("Deve permitir criar evento em horário imediatamente antes sem sobreposição")
    void shouldAllowEventCreationImmediatelyBefore() {

        // Arrange - evento existente de 12:00 às 14:00
        Event existingEvent = new Event(
            "Evento Existente",
            Weekday.WEDNESDAY,
            LocalTime.parse("16:00"), // Inícia quando o novo (eventDTO) termina
            LocalTime.parse("18:00"),
            "class789",
            "prof999",
            "disc999",
            "course456"
        );

        when(eventRepository.findByClassroomId("class789")).thenReturn(Arrays.asList(existingEvent));

        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Event result = eventService.create(eventDTO);

        // Assert
        assertNotNull(result);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

}