package com.social.network.domain.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import com.social.network.domain.exceptions.hibernate.GenericDaoException;


public interface GenericDao <T, PK extends Serializable> {

    List<T> getAll();
    T get(PK id);
    long save(T object);
    void persist(T entity);
    void saveOrUpdate(T entity);
    void remove(PK id);
    void delete(T entity);
    void refresh(T entity);
    void update(T entity);
    T merge(T entity);
    T load(PK id) throws GenericDaoException;
    Session getCurrentSession();
}
