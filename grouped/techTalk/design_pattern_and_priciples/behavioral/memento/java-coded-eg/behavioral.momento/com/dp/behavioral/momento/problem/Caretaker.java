package com.dp.behavioral.momento.problem;

public class Caretaker
{
    public static void main(String[] args)
    {
        Originator originator = new Originator();
        // Cannot access in any way
        // Requirement is to have access as reference to the state
        // without any modification rights to the state so that
        // client can restore the state later
        String storeState = originator.state;
    }
}
