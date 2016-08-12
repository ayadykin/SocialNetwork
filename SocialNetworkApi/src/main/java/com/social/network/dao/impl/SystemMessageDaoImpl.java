package com.social.network.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.social.network.dao.SystemMessageDao;
import com.social.network.model.SystemMessage;

/**
 * Created by Yadykin Andrii Jul 26, 2016
 *
 */
@Repository
public class SystemMessageDaoImpl extends GenericDaoHibernate<SystemMessage, Long> implements SystemMessageDao {

    private static final Logger logger = LoggerFactory.getLogger(SystemMessageDao.class);

    public SystemMessageDaoImpl() {
        super(SystemMessage.class);
    }

}
