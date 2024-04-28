package org.fiap.com.br.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Log4jLogger<T> implements _Logger<T> {
    private final Logger logger;

    public Log4jLogger(Class<T> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    @Override
    public void logCreate(T entity) {
        logger.info("Create: "+ entity);
    }

    @Override
    public void logGet(T entity) {
        logger.info("Get: "+ entity);
    }

    @Override
    public void logGetAll(T entity) {
        logger.info("GetAll: "+ entity);
    }

    @Override
    public void logUpdate(T entity) {
        logger.info("Update: "+ entity);
    }

    @Override
    public void logDelete(T entity) {
        logger.info("Deletando: "+ entity);
    }
}
