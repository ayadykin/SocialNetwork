package com.social.network.dao.impl;

import com.social.network.dao.ProfileDao;
import com.social.network.model.Profile;
import org.springframework.stereotype.Repository;

/**
 * Created by andrii.perylo on 5/16/2016.
 */
@Repository
public class ProfileDaoImpl extends GenericDaoHibernate<Profile, Long> implements ProfileDao {

    public ProfileDaoImpl() {
        super(Profile.class);
    }
}
