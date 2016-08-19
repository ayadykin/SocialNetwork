package com.social.network.domain.dao.impl;

import com.social.network.domain.dao.MessageDao;
import com.social.network.domain.model.Message;

import org.springframework.stereotype.Repository;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
@Repository
public class MessageDaoImpl extends GenericDaoHibernate<Message, Long> implements MessageDao{

    public MessageDaoImpl(){
        super(Message.class);
    }
}
