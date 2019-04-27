package com.dp.creational.abstractfactory.eg1;

public class FactoryMock implements AbstractFactory
{

    @Override
    public ProductA createProductA()
    {
        return new ProductAMock();
    }

    @Override
    public ProductB createProductB()
    {
        return new ProductBMock();
    }

}
