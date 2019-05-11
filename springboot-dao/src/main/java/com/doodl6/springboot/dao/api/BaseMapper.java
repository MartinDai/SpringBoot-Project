package com.doodl6.springboot.dao.api;

public interface BaseMapper<T> {

    void insert(T entity);

    T getById(long id);

}
