package com.dp.creational.abstractfactory.eg1;

public class MyAppClient
{
    public static void main(String[] args)
    {
        Client cl = new Client(new Factory1());
        System.out.println(cl.operation());

        cl = new Client(new Factory2());
        System.out.println(cl.operation());
    }
}
