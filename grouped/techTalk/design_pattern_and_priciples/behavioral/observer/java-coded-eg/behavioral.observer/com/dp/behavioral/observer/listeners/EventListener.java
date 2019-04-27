package com.dp.behavioral.observer.listeners;

import java.io.File;

//Common subscribers interface. By the way, modern
//programming languages allow to simplify this code and use
//functions as subscribers.
public interface EventListener
{
    public void update(String eventType, File file);
}
