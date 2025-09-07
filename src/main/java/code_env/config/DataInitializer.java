package code_env.config;

import code_env.model.Student;
import code_env.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final StudentRepository studentRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (studentRepository.count() == 0) {
            System.out.println("Initializing sample data...");
            
            // Create sample students
            Student student1 = Student.builder()
                    .name("Max")
                    .email("max@gmail.com")
                    .age(20)
                    .build();
                    
            Student student2 = Student.builder()
                    .name("Tatsuki")
                    .email("tatsuki@gmail.com")
                    .age(21)
                    .build();
                    
            Student student3 = Student.builder()
                    .name("Michel")
                    .email("Michel@gmail.com")
                    .age(22)
                    .build();
            
            // Save students
            studentRepository.save(student1);
            studentRepository.save(student2);
            studentRepository.save(student3);
            
            System.out.println("Sample data initialized successfully!");
        } else {
            System.out.println("Data already exists, skipping initialization.");
        }
    }
}
