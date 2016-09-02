package com.social.network.domain.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.social.network.domain.dao.FriendDao;
import com.social.network.domain.model.Friend;
import com.social.network.domain.util.Constants;

/**
 * Created by andrii.perylo on 5/13/2016.
 */

@Repository
public class FriendDaoImpl extends GenericDaoHibernate<Friend, Long> implements FriendDao {
    private final static Logger logger = LoggerFactory.getLogger(FriendDao.class);

    public FriendDaoImpl() {
        super(Friend.class);
    }

    @Override
    public Friend findByFriendAndOwner(long invitee, long inviter) {
        try {
            return (Friend) getCurrentSession().getNamedQuery(Constants.FIND_BY_FRIEND_AND_OWNER).setLong("invitee", invitee)
                    .setLong("inviter", inviter).uniqueResult();
        } catch (NonUniqueResultException e) {
            logger.debug("findByOwner NonUniqueResultException : {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.debug("findByOwner Exception : {}", e.getMessage());
            return null;
        }
    }
}
