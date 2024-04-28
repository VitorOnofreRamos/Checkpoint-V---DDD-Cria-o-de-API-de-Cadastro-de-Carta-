package org.fiap.com.br.repositories;

import org.fiap.com.br.entities._BaseEntity;

import java.util.List;

public interface _BaseRepository<T extends _BaseEntity>{
    void create(T obj);
    T get (int id);
    List<T> getAll();
    boolean update (int id, T obj);
    boolean delete(int id);
}
