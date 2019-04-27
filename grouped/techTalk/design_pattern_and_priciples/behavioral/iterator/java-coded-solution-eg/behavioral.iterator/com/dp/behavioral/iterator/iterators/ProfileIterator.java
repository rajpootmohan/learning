package com.dp.behavioral.iterator.iterators;

import com.dp.behavioral.iterator.profile.Profile;

public interface ProfileIterator
{
    boolean hasNext();

    Profile getNext();

    void reset();
}
