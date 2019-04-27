package com.dp.creational.abstractfactory.eg1;

public class Factory1 implements AbstractFactory
{

    @Override
    public ProductA createProductA()
    {
        System.out.println("Factory1: Creating a ProductA1 object.");
        return new ProductA1();
    }

    @Override
    public ProductB createProductB()
    {
        System.out.println("Factory1: Creating a ProductB1 object.");
        return new ProductB1();
    }

}
