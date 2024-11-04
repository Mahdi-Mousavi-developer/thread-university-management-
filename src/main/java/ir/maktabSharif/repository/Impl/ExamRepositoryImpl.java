package ir.maktabSharif.repository.Impl;


import ir.maktabSharif.model.Exam;
import ir.maktabSharif.repository.ExamRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import java.util.List;
import java.util.Optional;

public class ExamRepositoryImpl implements ExamRepository {

    private EntityManagerProvider entityManagerProvider;

    public ExamRepositoryImpl(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void saveOrUpdate(Exam object) {
        if (object.getId() == null) {
            saveExam(object);
        } else {
            updateExam(object);
        }
    }

    private void updateExam(Exam object) {
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


    private void saveExam(Exam object) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManagerProvider.getEntityManager().getTransaction();
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
        Optional<Exam> optionalExam = findById(id);
        if (this.findById(id).isPresent()) {
            try {
                transaction.begin();
                entityManager.merge(optionalExam);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            } finally {
                entityManager.close();
            }
        } else {
            System.out.println("exam not found");
        }

    }

    @Override
    public Optional<Exam> findById(Integer id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Exam> optionalExam = Optional.empty();
        try {
            transaction.begin();
            Exam exam = entityManager.find(Exam.class, id);
            transaction.commit();
            optionalExam = Optional.of(exam);

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return optionalExam;
    }

    @Override
    public List<Exam> getAll() {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Query query = null;
        try{
            transaction.begin();
            query= entityManager.createQuery("select c from Exam c");
            transaction.commit();
        }catch(Exception e){
            System.out.println("exams not found");
        }
        return query.getResultList();
    }
}
