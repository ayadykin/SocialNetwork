package com.social.network.dao;

import java.util.List;

import com.social.network.model.Group;
import com.social.network.model.User;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
public interface GroupDao extends GenericDao<Group, Long> {

    List<Group> findByOwner(User user);
}
