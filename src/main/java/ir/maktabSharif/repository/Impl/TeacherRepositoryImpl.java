package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.Exception.TeacherNotFoundException;
import ir.maktabSharif.model.Teacher;
import ir.maktabSharif.repository.TeacherRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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
    public void delete(Integer id) throws TeacherNotFoundException {
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
          throw new TeacherNotFoundException("teacher not found");
        }
    }

    @Override
    public Optional<Teacher> findById(Integer id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();

        Optional<Teacher> optionalTeacher = Optional.empty();
        Teacher teacher = entityManager.find(Teacher.class, id);
        return optionalTeacher = Optional.of(teacher);


    }

    @Override
    public List<Teacher> getAll() throws TeacherNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query = null;
        try {
            query = entityManager.createQuery("select c from Teacher c");
        } catch (Exception e) {
            throw new TeacherNotFoundException("Teachers not found");
        }
        return query.getResultList();
    }

}
