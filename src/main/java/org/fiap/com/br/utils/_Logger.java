package org.fiap.com.br.utils;

public interface _Logger<T>{
    void logCreate(T entity);
    void logGet(T entity);
    void logGetAll(T entity);
    void logUpdate(T entity);
    void logDelete(T entity);
}
