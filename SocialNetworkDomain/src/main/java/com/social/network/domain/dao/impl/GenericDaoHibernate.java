package com.social.network.domain.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.social.network.domain.dao.GenericDao;
import com.social.network.domain.exceptions.hibernate.GenericDaoException;

public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {
    private final Logger logger = LoggerFactory.getLogger(GenericDao.class);

    private final Class<T> persistentClass;
    @Autowired
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public List<T> getAll() {
        return sessionFactory.getCurrentSession().createCriteria(persistentClass).list();
    }

    public T get(PK id) {
        return (T) sessionFactory.getCurrentSession().get(persistentClass, id);
    }

    public T merge(T entity) {
        return (T) sessionFactory.getCurrentSession().merge(entity);
    }

    @SuppressWarnings("unchecked")
    public T load(PK id) throws GenericDaoException {
        try {
            return (T) sessionFactory.getCurrentSession().load(persistentClass, id);
        } catch (ObjectNotFoundException e) {
            throw new GenericDaoException();
        }
    }

    @SuppressWarnings("unchecked")
    public long save(T entity) {
        return (long) sessionFactory.getCurrentSession().save(entity);
    }

    @SuppressWarnings("unchecked")
    public void refresh(T entity) {
        sessionFactory.getCurrentSession().refresh(entity);
    }

    public void persist(T entity) {
        sessionFactory.getCurrentSession().persist(entity);
    }

    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @SuppressWarnings("unchecked")
    public void saveOrUpdate(T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public void remove(PK id) {
        T obj = this.get(id);
        sessionFactory.getCurrentSession().delete(obj);
    }
}
