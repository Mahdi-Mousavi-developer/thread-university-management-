package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.model.Course;
import ir.maktabSharif.model.User;
import ir.maktabSharif.repository.UserRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private EntityManagerProvider entityManagerProvider;

    public UserRepositoryImpl(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void saveOrUpdate(User object) {
        if (object.getId() == null) {
            saveUser(object);
        } else {
            updateUser(object);
        }
    }

    private void updateUser(User object) {
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

    private void saveUser(User object) {
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
        Optional<User> optionalUser = findById(id);
        if (this.findById(id).isPresent()) {
            try {
                transaction.begin();
                entityManager.remove(optionalUser);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            } finally {
                entityManager.close();
            }
        } else {
            System.out.println("User not found");
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<User> optionalUser = Optional.empty();
        try {
            transaction.begin();
           User user = entityManager.find(User.class, id);
            transaction.commit();
            optionalUser = Optional.of(user);

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return optionalUser;
    }

    @Override
    public List<User> getAll() {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query = entityManager.createQuery("select c from User c");
        return query.getResultList();
    }
}
