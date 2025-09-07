package code_env.controllers;

import code_env.dto.StudentDto;
import code_env.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class StudentController {
    private StudentService service;

    @PostMapping("/students")
    public ResponseEntity<?> saveStudents(@RequestBody List<StudentDto> students){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveStudents(students));
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudents(){
        return ResponseEntity.ok(service.getStudents());
    }
    
}
