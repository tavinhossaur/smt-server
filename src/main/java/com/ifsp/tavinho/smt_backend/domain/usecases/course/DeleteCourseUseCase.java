package com.ifsp.tavinho.smt_backend.domain.usecases.course;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifsp.tavinho.smt_backend.domain.entities.Course;
import com.ifsp.tavinho.smt_backend.domain.enums.Status;
import com.ifsp.tavinho.smt_backend.domain.repositories.CourseRepository;
import com.ifsp.tavinho.smt_backend.infra.exceptions.EntityNotFoundException;
import com.ifsp.tavinho.smt_backend.infra.interfaces.UseCase;
import com.ifsp.tavinho.smt_backend.shared.responses.ApiResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteCourseUseCase implements UseCase<String, ApiResponse<Void>> {

    private final CourseRepository repository;

    @Override
    public ResponseEntity<ApiResponse<Void>> execute(String id) {
        Course course = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        this.repository.delete(course);
        
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ApiResponse.<Void>builder()
                    .status(Status.SUCCESS)
                    .message("Course deleted successfully.")
                    .build()
            );
    }
    
}
