package com.dp.creational.factoryMethod;

// Concrete Creator
public class SeaLogistics extends Logisitcs
{
    // return concrete product
    @Override
    public Transport createTransport()
    {
        return new Ship();
    }
}
