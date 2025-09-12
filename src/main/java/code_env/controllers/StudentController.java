package code_env.controllers;

import code_env.dto.StudentDto;
import code_env.dto.StudentResponseDto;
import code_env.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService service;

    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentDto student) {
        StudentResponseDto savedStudent = service.saveStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<StudentResponseDto>> createStudents(@Valid @RequestBody List<StudentDto> students) {
        List<StudentResponseDto> savedStudents = service.saveStudents(students);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudents);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        List<StudentResponseDto> students = service.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable int id) {
        StudentResponseDto student = service.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable int id, @Valid @RequestBody StudentDto student) {
        StudentResponseDto updatedStudent = service.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}