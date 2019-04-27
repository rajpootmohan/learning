package com.dp.behavioral.strategy.problem;

public class MyAppClient
{
    public static void main(String[] args)
    {
        Context ctx = new Context();
        System.out.println(ctx.operation(("1")));
        System.out.println(ctx.operation(("2")));
    }

}
