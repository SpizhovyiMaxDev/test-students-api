package code_env.service;

import code_env.dto.StudentDto;
import code_env.model.Student;
import code_env.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository repository;

    public List<StudentDto> saveStudents(List<StudentDto> studentDtos) {
        List<Student> toSave = studentDtos.stream()
                .map(dto -> Student.builder()
                        .name(dto.name())
                        .email(dto.email())
                        .age(dto.age())
                        .build())
                .toList();

        List<Student> saved = repository.saveAll(toSave);

        return saved.stream()
                .map(s -> new StudentDto(s.getName(), s.getEmail(), s.getAge()))
                .toList();
    }



    public List<Student> getStudents(){
        return repository.findAll();
    }
}
