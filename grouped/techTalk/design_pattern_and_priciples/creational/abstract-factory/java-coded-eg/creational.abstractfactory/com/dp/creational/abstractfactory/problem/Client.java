package com.dp.creational.abstractfactory.problem;

public class Client
{
    private ProductA productA;

    private ProductB productB;

    public Client()
    {
        productA = new ProductA1();
        productB = new ProductB1();
    }
}
