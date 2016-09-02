package com.social.network.domain.dao;

import java.util.List;

import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii on 5/13/2016.
 */
public interface UsersDao extends GenericDao<User, Long> {
    List<User> searchUser(String firstName, String lastName, String city, String country);
}
