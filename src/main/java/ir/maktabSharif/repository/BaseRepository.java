package ir.maktabSharif.repository;

import java.util.Optional;
import java.util.Set;

public interface BaseRepository<T> {
    void saveOrUpdate(T object);
    void delete(Integer id);
    Optional<T> findById(Integer id);
    Set<T> getAll();
}
