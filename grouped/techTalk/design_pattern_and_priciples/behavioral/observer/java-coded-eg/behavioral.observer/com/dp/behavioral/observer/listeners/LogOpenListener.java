package com.dp.behavioral.observer.listeners;

import java.io.File;

//List of concrete listeners. They react to publisher
//updates by doing some useful work.
public class LogOpenListener implements EventListener
{
    private File log;

    public LogOpenListener(String fileName)
    {
        this.log = new File(fileName);
    }

    @Override
    public void update(String eventType, File file)
    {
        System.out.println("Save to log " + log + ": Someone has performed " + eventType
            + " operation with the following file: " + file.getName());
    }
}
