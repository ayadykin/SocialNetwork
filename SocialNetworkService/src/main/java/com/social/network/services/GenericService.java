package com.social.network.services;

/**
 * Created by Yadykin Andrii Oct 25, 2016
 *
 */

public interface GenericService<T> {

    T save(T object);
}
