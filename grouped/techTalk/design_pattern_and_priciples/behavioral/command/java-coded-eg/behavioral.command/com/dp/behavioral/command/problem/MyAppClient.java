package com.dp.behavioral.command.problem;

public class MyAppClient
{
    public static void main(String[] args)
    {
        Receiver1 receiver1 = new Receiver1();
        Invoker invoker = new Invoker(receiver1);
        invoker.operation();
    }
}
