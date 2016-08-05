package com.social.network.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.social.network.dao.AccountDao;
import com.social.network.model.Account;
import com.social.network.utils.Constants;

/**
 * @author ayadykin
 */

@Repository
public class AccountDaoImpl extends GenericDaoHibernate<Account, Long> implements AccountDao {
    private final static Logger logger = LoggerFactory.getLogger(AccountDao.class);

    public AccountDaoImpl() {
        super(Account.class);
    }

    @Override
    public Account findByEmail(String email) {
        logger.debug("findByEmail ");
        try {
            return (Account) getCurrentSession().getNamedQuery(Constants.FIND_ACCOUNT_BY_EMAIL).setString("email", email)
                    .uniqueResult();
        } catch (NonUniqueResultException e) {
            logger.debug("findByEmail NonUniqueResultException : {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.debug("findByEmail Exception : {}", e.getMessage());
            return null;
        }
    }
}
