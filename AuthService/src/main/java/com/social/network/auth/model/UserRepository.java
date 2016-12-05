package com.social.network.auth.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yadykin Andrii Dec 5, 2016
 *
 */

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

}
