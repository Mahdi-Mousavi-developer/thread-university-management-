package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.Exception.StudentNotFoundException;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.repository.StudentRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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
            entityManager.merge(object);
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
    public void delete(Integer id) throws StudentNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Student> optionalStudent = findById(id);
        if (this.findById(id).isPresent()) {
            try {
                transaction.begin();
                entityManager.remove(optionalStudent);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            } finally {
                entityManager.close();
            }
        } else {
           throw new StudentNotFoundException("student not found");
        }
    }

    @Override
    public Optional<Student> findById(Integer id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<Student> optionalStudent = Optional.empty();
            Student student = entityManager.find(Student.class, id);
        return optionalStudent = Optional.of(student);

    }

    @Override
    public List<Student> getAll() throws StudentNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query = null;
        try{
            query= entityManager.createQuery("select c from Student c");

        }catch(Exception e){
            throw new StudentNotFoundException("students not found");
        }
        return query.getResultList();
    }

}
