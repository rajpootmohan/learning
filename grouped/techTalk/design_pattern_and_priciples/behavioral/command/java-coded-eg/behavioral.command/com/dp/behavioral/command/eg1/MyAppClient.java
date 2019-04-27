package com.dp.behavioral.command.eg1;

public class MyAppClient
{
    public static void main(String[] args)
    {
        Receiver1 receiver1 = new Receiver1();
        Command command = new Command1(receiver1);
        Invoker invoker = new Invoker(command);
        invoker.operation();
    }
}
