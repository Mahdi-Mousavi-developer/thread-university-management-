package ir.maktabSharif.repository.Impl;

import ir.maktabSharif.model.User;
import ir.maktabSharif.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public void saveOrUpdate(User object) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return Collections.emptyList();
    }
}
