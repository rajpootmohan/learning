package com.dp.creational.builder.eg1;

public class Director
{
    private Builder builder;

    public Director(Builder builder)
    {
        this.builder = builder;
    }

    public void setBuilder(Builder builder)
    {
        this.builder = builder;
    }

    public void construct()
    {
        System.out.println("Director: Delegating constructing " + "a complex object to a builder object.");
        builder.buildPartA();
        builder.buildPartB();
    }
}
