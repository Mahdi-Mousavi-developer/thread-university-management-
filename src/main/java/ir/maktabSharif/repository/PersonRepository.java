package ir.maktabSharif.repository;

import ir.maktabSharif.model.Person;

public interface PersonRepository extends BaseRepository<Person> {
    long countOfPerson()throws Exception;
}
