package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.Exception.GenerallyNotFoundException;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.repository.StudentRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
public class StudentRepositoryImpl implements StudentRepository {
    private EntityManagerProvider entityManagerProvider;
    public List<Student> FindStudentsByFirstName (String name){
        EntityManager entityManager = entityManagerProvider.getEntityManager();
//      (queryish to model,student hast)
        TypedQuery<Student> students = entityManager.createNamedQuery("Student.findByFirstname", Student.class);
        students.setParameter(1,name);
        return students.getResultList();
    }

    public StudentRepositoryImpl(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void saveOrUpdate(Student object) {
        if (object.getId() == null) {
            saveStudent(object);
        } else {
            try {
                updateStudent(object);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void secUpdate(Student object) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Student> student1 = findById(object.getId());
        try {
            transaction.begin();
            student1.get().setDob(object.getDob());
            student1.get().setFirstName(object.getFirstName());
            student1.get().setLastName(object.getLastName());
            student1.get().setNationalCode(object.getNationalCode());
            student1.get().setCreateDate(object.getCreateDate());
            student1.get().setCourseList(object.getCourseList());
            student1.get().setExamList(object.getExamList());
            entityManager.persist(student1);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private void updateStudent(Student object) throws Exception {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Student> student1 = findById(object.getId());
        if (!student1.isPresent()) {
            throw new GenerallyNotFoundException("this student not even exist");
        }
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
    public void delete(Long id) throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Student> optionalStudent = findById(id);
        if (!this.findById(id).isPresent()) {
            throw new GenerallyNotFoundException("student not found");
        }
        try {
            transaction.begin();
            entityManager.remove(optionalStudent);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Optional<Student> findById(Long id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<Student> optionalStudent = Optional.ofNullable(entityManager.find(Student.class, id));
        return optionalStudent;

    }

    @Override
    public List<Student> getAll() throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query = null;
        try {
            query = entityManager.createQuery("select c from Student c");

        } catch (Exception e) {
            throw new GenerallyNotFoundException("students not found");
        }
        return query.getResultList();
    }

}
