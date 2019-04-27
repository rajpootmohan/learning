package com.dp.behavioral.strategy.problem;

public class Context
{

    public String operation(String choice)
    {
        switch (choice) {
            case "1":
                return "Context: Algorithm excuted : Result = " + 1;
            case "2":
                return "Context: Algorithm excuted : Result = " + 2;
            default:
                return "Context: Incorrect choice : Result = null";
        }
    }
}
