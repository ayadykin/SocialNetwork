package com.social.network.domain.dao;

import java.util.List;

import com.social.network.domain.model.Group;
import com.social.network.domain.model.User;

/**
 * Created by Yadykin Andrii on 5/13/2016.
 */
public interface GroupDao extends GenericDao<Group, Long> {

    List<Group> findByOwner(User user);
}
