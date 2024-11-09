package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.Exception.GenerallyNotFoundException;
import ir.maktabSharif.model.Student;
import ir.maktabSharif.model.User;
import ir.maktabSharif.repository.UserRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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
        //      User user1 = entityManager.find(User.class, object.getId());
        try {
            transaction.begin();
            entityManager.merge(object);
//            user1.setUsername(object.getUsername());
//            user1.setPassword(object.getPassword());
//            user1.setUserRole(object.getUserRole());
//            user1.setCreateDate(object.getCreateDate());
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
    public void delete(Long id) throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<User> optionalUser = findById(id);
        if (!this.findById(id).isPresent()) {
            throw new GenerallyNotFoundException("User not found");
        }

        try {
            transaction.begin();
            entityManager.remove(optionalUser);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<User> optionalUser= Optional.ofNullable(entityManager.find(User.class, id));
        return optionalUser;


    }

    @Override
    public List<User> getAll() throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query;
        try {

            query = entityManager.createQuery("select c from User c");

        } catch (Exception e) {
            throw new GenerallyNotFoundException("users not found");
        }
        return query.getResultList();
    }

}
