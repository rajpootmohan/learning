package com.dp.behavioral.momento.solution;

import java.util.ArrayList;
import java.util.List;

public class Caretaker
{
    // Running the Caretaker class as application.
    public static void main(String[] args)
    {
        Originator originator = new Originator();
        Originator.Memento memento; // Memento is inner class of Originator
        // List of memento objects.
        List<Originator.Memento> mementos = new ArrayList<>();

        originator.setState("A");
        // Saving state.
        memento = originator.createMemento();
        mementos.add(memento); // adding to list
        System.out.println("(1) Saving current state ...... : " + originator.getState());
        originator.setState("B");
        // Saving state.
        memento = originator.createMemento();
        mementos.add(memento); // adding to list
        System.out.println("(2) Saving current state ...... : " + originator.getState());
        // Restoring to previous state.
        memento = mementos.get(0); // getting previous (first) memento from the list
        originator.restore(memento);
        System.out.println("(3) Restoring to previous state : " + originator.getState());
    }
}
