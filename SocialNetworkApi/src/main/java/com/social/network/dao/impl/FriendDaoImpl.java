package com.social.network.dao.impl;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.social.network.dao.FriendDao;
import com.social.network.model.Friend;
import com.social.network.model.Group;
import com.social.network.model.User;
import com.social.network.utils.Constants;

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
    public Friend findByFriendAndOwner(User invitee, User inviter) {
        try {
            return (Friend) getCurrentSession().getNamedQuery(Constants.FIND_BY_FRIEND_AND_OWNER).setEntity("invitee", invitee)
                    .setEntity("inviter", inviter).uniqueResult();
        } catch (NonUniqueResultException e) {
            logger.debug("findByOwner NonUniqueResultException : {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.debug("findByOwner Exception : {}", e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findFriendNotInGroup(Group group, User user) {
        try {
            SQLQuery query = getCurrentSession().createSQLQuery("SELECT f.friend FROM friend f where f.user = :user");
            query.addEntity(User.class);
            return query.setLong("chatid", group.getChat().getChatId()).setLong("userId", user.getUserId()).list();
        } catch (Exception e) {
            logger.debug("findFriendNotInGroup Exception : {}", e.getMessage());
            return null;
        }
    }
}
