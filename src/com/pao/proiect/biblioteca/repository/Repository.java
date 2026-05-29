package com.pao.proiect.biblioteca.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
// intergata generica
public interface Repository<T, ID> {
    void save(T entity) throws SQLException;

    Optional<T> findById(ID id);

    List<T> findAll();

    void update(T entity);

    void delete(ID id);
}