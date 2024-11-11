package ir.maktabSharif;

import ir.maktabSharif.model.Address;
import ir.maktabSharif.model.Course;
import ir.maktabSharif.model.Gender;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.repository.Impl.CourseRepositoryImpl;
import ir.maktabSharif.repository.Impl.PersonRepositoryImpl;
import ir.maktabSharif.repository.Impl.StudentRepositoryImpl;
import ir.maktabSharif.repository.PersonRepository;
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


        System.out.println("git test");
        //test count of all persons
        PersonRepository personRepository = new PersonRepositoryImpl(entityManagerProvider);
        try {
            Long count = personRepository.countOfPerson();
            System.out.println(count);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
