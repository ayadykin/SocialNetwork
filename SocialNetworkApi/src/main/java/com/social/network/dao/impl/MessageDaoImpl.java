package com.social.network.dao.impl;

import com.social.network.dao.MessageDao;
import com.social.network.model.Message;
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
