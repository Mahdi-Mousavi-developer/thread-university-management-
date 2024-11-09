package ir.maktabSharif;

import ir.maktabSharif.model.Course;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.repository.Impl.CourseRepositoryImpl;
import ir.maktabSharif.repository.Impl.StudentRepositoryImpl;
import ir.maktabSharif.thread.CountOfAllStudent;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

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


        //thread
//        Thread thread = new Thread(new CountOfAllStudent(studentRepository));
//        thread.start();
//        try {
//            Thread.sleep(6000, 1);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Student student = Student
                .builder()
                .firstName("aliReza")
                .lastName("mobasher").build();
        student.setId(6L);
        studentRepository.saveOrUpdate(student);
        System.out.println("student added");
    }
}
