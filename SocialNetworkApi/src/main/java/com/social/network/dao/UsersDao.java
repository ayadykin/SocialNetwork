package com.social.network.dao;

import java.util.List;

import com.social.network.model.User;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
public interface UsersDao extends GenericDao<User, Long> {
    List<User> searchUser(String firstName, String lastName, String city, String country);
}
