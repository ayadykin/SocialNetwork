package com.social.network.neo4j.services.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.network.neo4j.domain.Group;
import com.social.network.neo4j.domain.User;
import com.social.network.neo4j.exeptions.InviteException;
import com.social.network.neo4j.repository.GroupRepository;
import com.social.network.neo4j.repository.UserRepository;
import com.social.network.neo4j.services.Neo4jService;

/**
 * Created by Yadykin Andrii Dec 1, 2016
 *
 */

@Slf4j
@Service
public class Neo4jServiceImpl implements Neo4jService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    @Transactional
    public Set<User> getFriends(long userId) {
        User user = userRepository.findOne(userId);
        // Optional.ofNullable(user).orElseThrow(exceptionSupplier);

        return user.getFriends();
    }

    @Override
    @Transactional
    public User createUser(String name) {
        log.info(" createUser name : {}", name);
        User user = new User();
        user.setName(name);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void inviteFriend(long userId, long friendId) {
        log.info("inviteFriend userId : {}, friendId : {}", userId, friendId);
        User user = userRepository.findOne(userId);
        User invitee = userRepository.findOne(friendId);

        if (user.getInvetee().stream().filter(i -> i.equals(invitee)).findFirst().isPresent()) {
            log.error("inviteFriend -> Invitee exist");
            throw new InviteException("Invitee exist");
        }
        if (user.getFriends().stream().filter(i -> i.equals(invitee)).findFirst().isPresent()) {
            log.error("inviteFriend -> Friend exist");
            throw new InviteException("Friend exist");
        }

        user.addInvitee(invitee);
        user.addInviter(invitee);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void acceptInvitation(long userId, long friendId) {

        User user = userRepository.findOne(userId);
        User inviter = userRepository.findOne(friendId);

        inviter.getInvetee().stream().filter(i -> i.equals(user)).findFirst()
                .orElseThrow(() -> new InviteException("Invitee doesn'n exist"));
        inviter.getInveter().stream().filter(i -> i.equals(user)).findFirst()
                .orElseThrow(() -> new InviteException("Inviter doesn'n exist"));

        inviter.removeInviter(user);
        inviter.removeInvitee(user);

        inviter.addFriend(user);
        user.addFriend(inviter);

        userRepository.save(inviter);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public Group createGroup(long ownerId, String groupName, long[] userIds) {
        User owner = userRepository.findOne(ownerId);

        Group group = new Group(groupName, owner);
        group.addUser(owner);

        owner.addGroup(group);
        userRepository.save(owner);

        int size = userIds.length;
        for (int i = 0; i < size; i++) {
            User user = userRepository.findOne(userIds[i]);
            user.addGroup(group);
            userRepository.save(user);
            group.addUser(user);
        }

        return groupRepository.save(group);
    }
}
