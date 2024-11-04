package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.model.Course;
import ir.maktabSharif.model.Teacher;
import ir.maktabSharif.repository.TeacherRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TeacherRepositoryImpl implements TeacherRepository {
    private EntityManagerProvider entityManagerProvider;

    public TeacherRepositoryImpl(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void saveOrUpdate(Teacher object) {
        if (object.getId() == null) {
            saveTeacher(object);
        } else {
            updateTeacher(object);
        }
    }

    private void updateTeacher(Teacher object) {
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

    private void saveTeacher(Teacher object) {
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
        Optional<Teacher> optionalTeacher = findById(id);
        if (this.findById(id).isPresent()) {
            try {
                transaction.begin();
                entityManager.remove(optionalTeacher);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            } finally {
                entityManager.close();
            }
        } else {
            System.out.println("teacher not found");
        }
    }

    @Override
    public Optional<Teacher> findById(Integer id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Teacher> optionalTeacher = Optional.empty();
        try {
            transaction.begin();
            Teacher teacher = entityManager.find(Teacher.class, id);
            transaction.commit();
            optionalTeacher = Optional.of(teacher);

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return optionalTeacher;
    }

    @Override
    public List<Teacher> getAll() {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Query query = null;
        try{
            transaction.begin();
            query= entityManager.createQuery("select c from Teacher c");
            transaction.commit();
        }catch(Exception e){
            System.out.println("Teachers not found");
        }finally{
            entityManager.close();
        }
        return query.getResultList();
    }

}
