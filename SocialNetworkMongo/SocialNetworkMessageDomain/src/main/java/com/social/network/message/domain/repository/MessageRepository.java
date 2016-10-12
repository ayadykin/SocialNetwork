package com.social.network.message.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.social.network.message.domain.model.Message;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{

}

