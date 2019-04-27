package com.dp.creational.abstractfactory.eg1;

public class Factory2 implements AbstractFactory
{

    @Override
    public ProductA createProductA()
    {
        System.out.println("Factory2: Creating a ProductA2 object.");
        return new ProductA2();
    }

    @Override
    public ProductB createProductB()
    {
        System.out.println("Factory2: Creating a ProductB2 object.");
        return new ProductB2();
    }

}
