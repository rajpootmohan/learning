package com.dp.creational.prototype.solution;

public class Client
{
    private ProductA productA;

    private ProductB productB;

    private PrototypeFactory ptFactory;

    public Client(PrototypeFactory ptFactory)
    {
        this.ptFactory = ptFactory;
    }

    public String operation()
    {
        System.out.println("Client: Delegating object creation to a prototype factory.");
        productA = ptFactory.createProductA();
        productB = ptFactory.createProductB();
        return "Hello World from " + productA.getName() + " and " + productB.getName() + "!";
    }
}
