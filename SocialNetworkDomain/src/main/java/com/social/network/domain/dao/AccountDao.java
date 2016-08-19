package com.social.network.domain.dao;

import com.social.network.domain.model.Account;


public interface AccountDao extends GenericDao<Account, Long>{
	public Account findByEmail(String email);
}
