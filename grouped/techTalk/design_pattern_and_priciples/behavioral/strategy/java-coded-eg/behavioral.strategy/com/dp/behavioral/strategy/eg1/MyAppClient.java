package com.dp.behavioral.strategy.eg1;

public class MyAppClient
{
    public static void main(String[] args)
    {
        Context ctx = new Context(new Strategy1());
        System.out.println(ctx.operation());
        ctx.setStrategy(new Strategy2());
        System.out.println(ctx.operation());
    }
}
