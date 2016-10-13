package com.social.network.message.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.social.network.message.domain.model.Chat;

/**
 * Created by Yadykin Andrii Oct 13, 2016
 *
 */

@Repository
public interface ChatRepository extends MongoRepository<Chat, Long>{

}

