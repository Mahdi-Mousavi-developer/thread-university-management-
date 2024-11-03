package ir.maktabSharif;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import ir.maktabSharif.model.Course;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.repository.Impl.CourseRepositoryImpl;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jdbc-postgres");
        EntityManager em = emf.createEntityManager();
        //   // add kardan student be database
        //   Student student = Student
        //           .builder()
        //           .firstName("ali")
        //           .lastName("ezati").build();
        //   em.getTransaction().begin();
        //   em.persist(student);
        //   em.getTransaction().commit();*/
        //   // payan add kardan student be database
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
CourseRepositoryImpl courseRepository = new CourseRepositoryImpl(entityManagerProvider);
List<Course> cours =courseRepository.getAll();
        System.out.println(cours);
    }
}
