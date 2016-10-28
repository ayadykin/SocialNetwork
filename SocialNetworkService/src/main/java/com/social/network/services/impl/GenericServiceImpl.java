package com.social.network.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.social.network.domain.dao.GenericDao;
import com.social.network.services.GenericService;

/**
 * Created by Yadykin Andrii Oct 25, 2016
 *
 */

public class GenericServiceImpl<T> implements GenericService<T> {

    @Autowired
    private GenericDao<T, Long> genericDao;

    @Override
    public T save(T object) {
        genericDao.save(object);
        return object;
    }
}
