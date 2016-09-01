package com.social.network.domain.dao.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.model.Chat;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
@Repository
public class ChatDaoImpl extends GenericDaoHibernate<Chat, Long> implements ChatDao {

    private static final Logger logger = LoggerFactory.getLogger(ChatDao.class);
    @Autowired
    private SessionFactory sessionFactory;

    public ChatDaoImpl() {
        super(Chat.class);
    }
}
