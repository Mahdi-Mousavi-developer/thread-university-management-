package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.Exception.GenerallyNotFoundException;
import ir.maktabSharif.model.Course;
import ir.maktabSharif.model.Person;
import ir.maktabSharif.repository.PersonRepository;
import ir.maktabSharif.util.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PersonRepositoryImpl implements PersonRepository {
    private EntityManagerProvider entityManagerProvider;

    public PersonRepositoryImpl(EntityManagerProvider entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    @Override
    public void saveOrUpdate(Person object) {
        if (object.getId() == null) {
            savePerson(object);
        } else {
            updatePerson(object);
        }
    }

    private void savePerson(Person object) {
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

    private void updatePerson(Person object) {
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

    @Override
    public void delete(Long id) throws Exception {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Optional<Person> optionalPerson = findById(id);
        if (!this.findById(id).isPresent()) {
            throw new GenerallyNotFoundException("Person not found");
        }
        try {
            transaction.begin();
            entityManager.remove(optionalPerson);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Person> findById(Long id) {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<Person> optionalPerson = Optional.ofNullable(entityManager.find(Person.class, id));
        return optionalPerson;
    }

    @Override
    public List<Person> getAll() throws Exception {
        {
            EntityManager entityManager = entityManagerProvider.getEntityManager();
            Query query = null;
            try {
                query = entityManager.createQuery("select c from Person c");
            } catch (Exception e) {
                throw new GenerallyNotFoundException("Person not found");
            }
            return query.getResultList();
        }
    }
    @Override
    public void secondUpdate(Person object) throws Exception {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Optional<Person> foundedPerson = this.findById(object.getId());
            if (!foundedPerson.isPresent()) {
                throw new GenerallyNotFoundException("Person not found");
            }
            transaction.begin();
            foundedPerson.get().setDob(object.getDob());
            foundedPerson.get().setFirstName(object.getFirstName());
            foundedPerson.get().setLastName(object.getLastName());
            foundedPerson.get().setNationalCode(object.getNationalCode());
            foundedPerson.get().setCreateDate(object.getCreateDate());
            foundedPerson.get().setAddress(object.getAddress());
            foundedPerson.get().setGender(object.getGender());
            entityManager.persist(foundedPerson);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public long countOfPerson() throws GenerallyNotFoundException {
        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Query query;
        try {

            query = entityManager.createQuery("select count(c) from Person c");

        } catch (Exception e) {
            throw new GenerallyNotFoundException("users not found");
        }
        return (Long) query.getSingleResult();
    }

}
