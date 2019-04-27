package com.dp.behavioral.observer.editor;

import java.io.File;

import com.dp.behavioral.observer.publisher.EventManager;

//Concrete publisher, which contains real business logic
//interesting for some subscribers. We could derive this
//class from a base publisher, but that is not always
//possible in real life, since the concrete publisher might
//already have a different parent class. In this case, you
//can patch the subscription logic in with composition,
//just like we did it here.
public class Editor
{
    public EventManager events;

    private File file;

    public Editor()
    {
        this.events = new EventManager("open", "save");
    }

    // Business logic methods can notify subscribers about
    // the changes.
    public void openFile(String filePath)
    {
        this.file = new File(filePath);
        events.notify("open", file);
    }

    public void saveFile() throws Exception
    {
        if (this.file != null) {
            events.notify("save", file);
        } else {
            throw new Exception("Please open a file first.");
        }
    }
}
