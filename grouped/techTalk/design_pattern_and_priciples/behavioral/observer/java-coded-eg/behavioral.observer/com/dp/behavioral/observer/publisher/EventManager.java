package com.dp.behavioral.observer.publisher;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dp.behavioral.observer.listeners.EventListener;

//Base publisher class. It should include the subscription
//management code and notification methods.
public class EventManager
{
    private Map<String, Set<EventListener>> listeners = new HashMap<>();

    public EventManager(String... operations)
    {
        for (String operation : operations) {
            this.listeners.put(operation, new HashSet<>());
        }
    }

    public void subscribe(String eventType, EventListener listener)
    {
        Set<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener)
    {
        Set<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(String eventType, File file)
    {
        Set<EventListener> users = listeners.get(eventType);
        for (EventListener el : users) {
            el.update(eventType, file);
        }
    }

}
