package com.social.network.dao;

import com.social.network.dao.impl.GenericDaoHibernate;
import com.social.network.model.Account;


public interface AccountDao extends GenericDao<Account, Long>{
	public Account findByEmail(String email);
}
