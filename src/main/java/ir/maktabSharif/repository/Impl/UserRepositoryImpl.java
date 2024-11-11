package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.Exception.GenerallyNotFoundException;
import ir.maktabSharif.model.User;
import ir.maktabSharif.repository.UserRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private EntityManagerProvider entityManagerProvider;

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findByUsernameAndPassword", User.class);
        typedQuery.setParameter(1,username);
        typedQuery.setParameter(2,password);
        return typedQuery.getSingleResult();
    }

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

    @Override
    public void secondUpdate(User object) throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            Optional<User> foundedUser = findById(object.getId());
            if (!foundedUser.isPresent()) {
              throw new GenerallyNotFoundException("User not found");
            }
            transaction.begin();
           foundedUser.get().setUsername(object.getUsername());
           foundedUser.get().setPassword(object.getPassword());
           foundedUser.get().setUserRole(object.getUserRole());
           foundedUser.get().setCreateDate(object.getCreateDate());
           entityManager.persist(foundedUser);
           transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally{
            entityManager.close();
        }

    }

    private void updateUser(User object) {
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
