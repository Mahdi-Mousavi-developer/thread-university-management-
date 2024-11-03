package ir.maktabSharif;

import ir.maktabSharif.model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("jdbc-postgres");
       EntityManager em =  emf.createEntityManager();
     //   // add kardan student be database
     //   Student student = Student
     //           .builder()
     //           .firstName("ali")
     //           .lastName("ezati").build();
     //   em.getTransaction().begin();
     //   em.persist(student);
     //   em.getTransaction().commit();*/
     //   // payan add kardan student be database
    emf.close();
    }
}
