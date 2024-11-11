package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.Exception.GenerallyNotFoundException;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.model.Teacher;
import ir.maktabSharif.repository.TeacherRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TeacherRepositoryImpl implements TeacherRepository {
    private EntityManagerProvider entityManagerProvider;

    public List<Teacher> FindTeachersByFirstName (String name){
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        TypedQuery<Teacher> teacherTypedQuery = entityManager.createNamedQuery("Teacher.findByFirstname", Teacher.class);
        teacherTypedQuery.setParameter(1,name);
        return teacherTypedQuery.getResultList();
    }

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

    @Override
    public void secondUpdate(Teacher object) throws Exception {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Optional<Teacher> foundedTeacher = findById(object.getId());
            if (!foundedTeacher.isPresent()) {
                throw new GenerallyNotFoundException("Student not found");
            }
            transaction.begin();
            foundedTeacher.get().setDob(object.getDob());
            foundedTeacher.get().setFirstName(object.getFirstName());
            foundedTeacher.get().setLastName(object.getLastName());
            foundedTeacher.get().setNationalCode(object.getNationalCode());
            foundedTeacher.get().setCreateDate(object.getCreateDate());
            foundedTeacher.get().setAddress(object.getAddress());
            foundedTeacher.get().setCourse(object.getCourse());
            foundedTeacher.get().setGender(object.getGender());
            entityManager.persist(foundedTeacher);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
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
    public void delete(Long id) throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Teacher> optionalTeacher = findById(id);
        if (!this.findById(id).isPresent()) {
            throw new GenerallyNotFoundException("teacher not found");
        }
        try {
            transaction.begin();
            entityManager.remove(optionalTeacher);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<Teacher> optionalTeacher = Optional.ofNullable(entityManager.find(Teacher.class, id));
        return optionalTeacher;


    }

    @Override
    public List<Teacher> getAll() throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query = null;
        try {
            query = entityManager.createQuery("select c from Teacher c");
        } catch (Exception e) {
            throw new GenerallyNotFoundException("Teachers not found");
        }
        return query.getResultList();
    }

}
