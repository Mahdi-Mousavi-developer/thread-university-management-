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
//      (This query is placed in model.Student )
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
    public void secondUpdate(Student object) throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Optional<Student> foundedStudent = findById(object.getId());
            if (!foundedStudent.isPresent()) {
                throw new GenerallyNotFoundException("Student not found");
            }
            transaction.begin();
            foundedStudent.get().setDob(object.getDob());
            foundedStudent.get().setFirstName(object.getFirstName());
            foundedStudent.get().setLastName(object.getLastName());
            foundedStudent.get().setNationalCode(object.getNationalCode());
            foundedStudent.get().setCreateDate(object.getCreateDate());
            foundedStudent.get().setCourseList(object.getCourseList());
            foundedStudent.get().setExamList(object.getExamList());
            foundedStudent.get().setAddress(object.getAddress());
            foundedStudent.get().setGender(object.getGender());
            entityManager.persist(foundedStudent);
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
