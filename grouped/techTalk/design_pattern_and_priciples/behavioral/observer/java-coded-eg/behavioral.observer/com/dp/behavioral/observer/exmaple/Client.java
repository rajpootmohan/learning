package com.dp.behavioral.observer.exmaple;

import com.dp.behavioral.observer.editor.Editor;
import com.dp.behavioral.observer.listeners.EmailNotificationListener;
import com.dp.behavioral.observer.listeners.LogOpenListener;

//Application can configure publishers and subscribers even
//in run time.
public class Client
{
    public static void main(String[] args)
    {
        Editor editor = new Editor();
        editor.events.subscribe("open", new LogOpenListener("/path/to/log/file.txt"));
        editor.events.subscribe("save", new EmailNotificationListener("admin@example.com"));

        try {
            editor.openFile("test.txt");
            editor.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
