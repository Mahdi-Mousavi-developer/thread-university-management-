package ir.maktabSharif.repository;

import ir.maktabSharif.model.User;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    void saveOrUpdate(T object);
    void delete(Long id) throws Exception;
    Optional<T> findById(Long id);
    List<T> getAll() throws Exception;
    void secondUpdate(T object) throws Exception;
}
