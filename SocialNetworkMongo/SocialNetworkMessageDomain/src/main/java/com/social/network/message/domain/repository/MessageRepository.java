package com.social.network.message.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.social.network.message.domain.model.MongoMessage;

/**
 * Created by Yadykin Andrii Oct 12, 2016
 *
 */

@Repository
public interface MessageRepository extends MongoRepository<MongoMessage, Long> {

}
