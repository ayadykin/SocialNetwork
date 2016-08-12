package com.social.network.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.social.network.dao.UsersDao;
import com.social.network.model.User;

/**
 * Created by andrii.perylo on 5/13/2016.
 */

@Repository
public class UsersDaoImpl extends GenericDaoHibernate<User, Long> implements UsersDao {

    public UsersDaoImpl() {
        super(User.class);
    }

    @Override
    public List<User> searchUser(String firstName, String lastName, String city, String country) {
        Criteria criteria = getCurrentSession().createCriteria(User.class, "user");
        criteria.createAlias("user.profile", "profile");
        if (StringUtils.isNotEmpty(firstName)) {
            criteria.add(Restrictions.eq("profile.firstName", firstName));
        }
        if (StringUtils.isNotEmpty(lastName)) {
            criteria.add(Restrictions.eq("profile.lastName", lastName));
        }
        if (StringUtils.isNotEmpty(city)) {
            criteria.add(Restrictions.eq("profile.city", city));
        }
        if (StringUtils.isNotEmpty(country)) {
            criteria.add(Restrictions.eq("profile.country", country));
        }
        return criteria.list();

    }

}
