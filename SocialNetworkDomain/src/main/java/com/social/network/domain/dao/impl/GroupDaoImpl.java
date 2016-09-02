package com.social.network.domain.dao.impl;

import java.util.Collections;
import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.social.network.domain.dao.GroupDao;
import com.social.network.domain.model.Group;
import com.social.network.domain.model.User;
import com.social.network.domain.util.Constants;

/**
 * Created by Yadykin Andrii on 5/13/2016.
 */
@Repository
public class GroupDaoImpl extends GenericDaoHibernate<Group, Long> implements GroupDao {
    private final static Logger logger = LoggerFactory.getLogger(GroupDao.class);

    public GroupDaoImpl() {
        super(Group.class);
    }

    @Override
    public List<Group> findByOwner(User user) {
        try {
            return getCurrentSession().getNamedQuery(Constants.FIND_GROUP_BY_OWNER).setEntity("user", user).list();
        } catch (NonUniqueResultException e) {
            logger.error("findByOwner NonUniqueResultException : {}", e.getMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("findByOwner Exception : {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
