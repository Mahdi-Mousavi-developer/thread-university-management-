package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.model.Teacher;
import ir.maktabSharif.repository.TeacherRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TeacherRepositoryImpl implements TeacherRepository {
    @Override
    public void saveOrUpdate(Teacher object) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Optional<Teacher> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Teacher> getAll() {
        return Collections.emptyList();
    }
}
