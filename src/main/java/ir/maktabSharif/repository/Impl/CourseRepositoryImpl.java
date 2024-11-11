package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.Exception.GenerallyNotFoundException;
import ir.maktabSharif.model.Course;
import ir.maktabSharif.model.Exam;
import ir.maktabSharif.repository.CourseRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.*;

public class CourseRepositoryImpl implements CourseRepository {
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
            entityManager.persist(course);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void secondUpdate(Course object) throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Optional<Course> foundedCourse = this.findById(object.getId());
            if (!foundedCourse.isPresent()) {
                throw new GenerallyNotFoundException("Course not found");
            }
            transaction.begin();
            foundedCourse.get().setCreateDate(object.getCreateDate());
            foundedCourse.get().setExams(object.getExams());
            foundedCourse.get().setUnit(object.getUnit());
            foundedCourse.get().setTitle(object.getTitle());
            foundedCourse.get().setStudents(object.getStudents());
            entityManager.persist(foundedCourse);
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
            entityManager.merge(course);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void delete(Long id) throws Exception {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Course> optionalCourse = findById(id);
        if (!this.findById(id).isPresent()) {
            throw new GenerallyNotFoundException("Course not found");
        }
        try {
            transaction.begin();
            entityManager.remove(optionalCourse);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Optional<Course> findById(Long id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<Course> optionalCourse = Optional.ofNullable(entityManager.find(Course.class, id));
        return optionalCourse;
    }

    @Override
    public List<Course> getAll() throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query = null;
        try {
            query = entityManager.createQuery("select c from Course c");
        } catch (Exception e) {
            throw new GenerallyNotFoundException("course not found");
        }
        return query.getResultList();
    }
}

