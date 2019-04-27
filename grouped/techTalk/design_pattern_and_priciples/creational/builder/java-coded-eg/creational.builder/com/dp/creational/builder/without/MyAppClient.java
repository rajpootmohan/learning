package com.dp.creational.builder.without;

public class MyAppClient
{
    private Director director;

    public MyAppClient(Director director)
    {
        this.director = director;
    }

    public static void main(String[] args)
    {
        MyAppClient client = new MyAppClient(new Director());
        System.out.println(client.orderNewComplexProduct().getParts());
    }

    public ComplexProduct orderNewComplexProduct()
    {
        ComplexProduct cp = director.construct();
        return cp;
    }
}
