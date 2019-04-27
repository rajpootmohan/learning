package com.dp.creational.prototype.solution;

public class MyApp
{
    public static void main(String[] args)
    {
        // Creating a Client object
        // and configuring it with a PrototypeFactory object.
        Client client = new Client(new PrototypeFactory(new ProductA1("ProductA1"), new ProductB1("ProductB1")));
        System.out.println(client.operation());
    }
}
