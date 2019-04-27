package com.dp.behavioral.command.problem;

public class Invoker
{
    private Receiver1 receiver1;

    public Invoker(Receiver1 receiver1)
    {
        this.receiver1 = receiver1;
    }

    public void operation()
    {
        System.out.println("Invoker : Calling execute on the installed command...");
        receiver1.action1();
    }
}
