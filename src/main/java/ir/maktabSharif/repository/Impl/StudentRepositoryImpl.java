package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.model.Student;
import ir.maktabSharif.repository.StudentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentRepositoryImpl implements StudentRepository {
    @Override
    public void saveOrUpdate(Student object) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Optional<Student> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Student> getAll() {
        return Collections.emptyList();
    }
}
