package com.dp.behavioral.iterator.social_networks;

import com.dp.behavioral.iterator.iterators.ProfileIterator;

public interface SocialNetwork
{
    public ProfileIterator createFriendsIterator(String profileEmail);

    public ProfileIterator createCoworkersIterator(String profileEmail);
}
