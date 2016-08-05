package com.social.network.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;


public interface GenericDao <T, PK extends Serializable> {

    List<T> getAll();
    T get(PK id);
    boolean exists(PK id);
    long save(T object);
    void persist(T entity);
    void saveOrUpdate(T entity);
    void remove(PK id);
    void delete(T entity);
    void refresh(T entity);
    void update(T entity);
    T merge(T entity);
    T load(PK id);
    Session getCurrentSession();
}
