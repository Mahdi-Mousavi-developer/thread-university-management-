package ir.maktabSharif;

import ir.maktabSharif.model.Address;
import ir.maktabSharif.model.Course;
import ir.maktabSharif.model.Gender;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.repository.Impl.CourseRepositoryImpl;
import ir.maktabSharif.repository.Impl.StudentRepositoryImpl;
import ir.maktabSharif.thread.CountOfAllStudent;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class  Main {
    public static void main(String[] args) {












        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jdbc-postgres");
        EntityManager em = emf.createEntityManager();





        //test getAll()
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        CourseRepositoryImpl courseRepository = new CourseRepositoryImpl(entityManagerProvider);
        List<Course> cours = null;
        try {
            cours = courseRepository.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(cours);


        StudentRepositoryImpl studentRepository = new StudentRepositoryImpl(entityManagerProvider);


        Address address = Address.builder()
                .country("Iran").city("tehran").street("damavandi").zipCode("12344567891").build();
        Student student = Student
                .builder()
                .firstName("amirReza")
                .lastName("kia").gender(Gender.MALE).address(address).build();
        student.setId(3L);
       // studentRepository.saveOrUpdate(student);
       List<Student> students= studentRepository.FindStudentsByFirstName("mahdi");
        System.out.println(students);
    }
}
