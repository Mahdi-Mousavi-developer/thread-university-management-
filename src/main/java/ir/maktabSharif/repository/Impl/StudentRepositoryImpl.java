package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.model.Course;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.repository.StudentRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentRepositoryImpl implements StudentRepository {
    private EntityManagerProvider entityManagerProvider;

    public StudentRepositoryImpl(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void saveOrUpdate(Student object) {
        if (object.getId() == null) {
            saveStudent(object);
        } else {
            updateStudent(object);
        }
    }

    private void updateStudent(Student object) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.refresh(object);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private void saveStudent(Student object) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(object);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Student> optionalStudent = findById(id);
        if (this.findById(id).isPresent()) {
            try {
                transaction.begin();
                entityManager.merge(optionalStudent);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            } finally {
                entityManager.close();
            }
        } else {
            System.out.println("student not found");
        }
    }

    @Override
    public Optional<Student> findById(Integer id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Student> optionalStudent = Optional.empty();
        try {
            transaction.begin();
            Student student = entityManager.find(Student.class, id);
            transaction.commit();
            optionalStudent = Optional.of(student);

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return optionalStudent;
    }

    @Override
    public List<Student> getAll() {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Query query = null;
        try{
            transaction.begin();
            query= entityManager.createQuery("select c from Student c");
            transaction.commit();
        }catch(Exception e){
            System.out.println("students not found");
        }
        return query.getResultList();
    }

}
