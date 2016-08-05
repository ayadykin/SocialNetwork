package com.social.network.message;

import java.util.Arrays;
import java.util.List;

import com.social.network.model.User;

/**
 * Created by Yadykin Andrii Jul 27, 2016
 *
 */

public class Subscribers {

    private List<User> subscribers;
    private User publisher;

    /**
     * Constructor for group subscribers
     * 
     * @param publisher
     * @param subscribers
     */
    public Subscribers(User publisher, List<User> subscribers) {
        this.subscribers = subscribers;
        this.publisher = publisher;
        setPublisherFirst();
    }

    /**
     * Constructor for friend subscribers
     * 
     * @param publisher
     * @param invited
     */
    public Subscribers(User publisher, User invited) {
        this.subscribers = Arrays.asList(publisher, invited);
    }

    private void setPublisherFirst() {
        int index = subscribers.indexOf(publisher);
        subscribers.remove(index);
        subscribers.add(0, publisher);
    }

    public User getPublisher() {
        return subscribers.stream().findFirst().orElseThrow(() -> new IllegalArgumentException());
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

}
