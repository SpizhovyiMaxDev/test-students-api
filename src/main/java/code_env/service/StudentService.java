package code_env.service;

import code_env.dto.StudentDto;
import code_env.dto.StudentResponseDto;
import code_env.model.Student;
import code_env.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository repository;

    public StudentResponseDto saveStudent(StudentDto studentDto) {
        Student newStudent = Student.builder()
                .name(studentDto.name())
                .email(studentDto.email())
                .age(studentDto.age())
                .build();

        Student savedStudent = repository.save(newStudent);
        return new StudentResponseDto(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getEmail(),
                savedStudent.getAge()
        );
    }

    public List<StudentResponseDto> saveStudents(List<StudentDto> studentDtos) {
        List<Student> toSave = studentDtos.stream()
                .map(dto -> Student.builder()
                        .name(dto.name())
                        .email(dto.email())
                        .age(dto.age())
                        .build())
                .toList();

        List<Student> saved = repository.saveAll(toSave);

        return saved.stream()
                .map(s -> new StudentResponseDto(s.getId(), s.getName(), s.getEmail(), s.getAge()))
                .toList();
    }

    public List<StudentResponseDto> getAllStudents() {
        return repository.findAll().stream()
                .map(s -> new StudentResponseDto(s.getId(), s.getName(), s.getEmail(), s.getAge()))
                .toList();
    }

    public StudentResponseDto getStudentById(int id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return new StudentResponseDto(student.getId(), student.getName(), student.getEmail(), student.getAge());
    }

    public StudentResponseDto updateStudent(int id, StudentDto studentDto) {
        Student existingStudent = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        existingStudent.setName(studentDto.name());
        existingStudent.setEmail(studentDto.email());
        existingStudent.setAge(studentDto.age());
        
        Student updated = repository.save(existingStudent);
        return new StudentResponseDto(updated.getId(), updated.getName(), updated.getEmail(), updated.getAge());
    }

    public void deleteStudent(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
