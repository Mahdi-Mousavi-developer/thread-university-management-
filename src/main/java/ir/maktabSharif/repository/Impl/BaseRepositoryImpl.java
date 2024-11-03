package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.repository.BaseRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BaseRepositoryImpl implements BaseRepository {
    @Override
    public void saveOrUpdate(Object object) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Optional findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return Collections.emptyList();
    }
}
