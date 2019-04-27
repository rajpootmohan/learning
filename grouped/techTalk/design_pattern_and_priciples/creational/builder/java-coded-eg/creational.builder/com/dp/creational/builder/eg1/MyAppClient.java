package com.dp.creational.builder.eg1;

public class MyAppClient
{

    public static void main(String[] args)
    {
        Builder1 builder = new Builder1();
        Director director = new Director(builder);
        director.construct();
        System.out.println(builder.getResult().getParts());
    }
}
