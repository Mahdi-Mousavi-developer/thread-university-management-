package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.model.Exam;
import ir.maktabSharif.repository.ExamRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ExamRepositoryImpl implements ExamRepository {
    @Override
    public void saveOrUpdate(Exam object) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Optional<Exam> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Exam> getAll() {
        return Collections.emptyList();
    }
}
