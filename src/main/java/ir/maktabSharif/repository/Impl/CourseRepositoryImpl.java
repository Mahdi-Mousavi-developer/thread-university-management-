package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.model.Course;
import ir.maktabSharif.repository.CourseRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.*;

public class CourseRepositoryImpl implements CourseRepository  {
    private EntityManagerProvider entityManagerProvider;

    public CourseRepositoryImpl(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void saveOrUpdate(Course object) {

        if (object.getId() == null) {
            saveCourse(object);
        } else {
            updateCourse(object);
        }
    }

    public void saveCourse(Course course) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(course);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public void updateCourse(Course course) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.refresh(course);
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
        Optional<Course> optionalCourse = findById(id);
        if (this.findById(id).isPresent()) {
            try {
                transaction.begin();
                entityManager.remove(optionalCourse);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            } finally {
                entityManager.close();
            }
        } else {
            System.out.println("Course not found");
        }
    }


    @Override
    public Optional<Course> findById(Integer id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Course> optionalCourse = Optional.empty();
        try {
            transaction.begin();
            Course course = entityManager.find(Course.class, id);
            transaction.commit();
            optionalCourse = Optional.of(course);

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return optionalCourse;
    }

    @Override
    public List<Course> getAll() {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query = entityManager.createQuery("select c from Course c");
        return query.getResultList();


    }
}

