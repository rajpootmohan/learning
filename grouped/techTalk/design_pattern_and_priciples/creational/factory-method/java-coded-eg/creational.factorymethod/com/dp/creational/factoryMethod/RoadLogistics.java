package com.dp.creational.factoryMethod;

//Concrete Creator
public class RoadLogistics extends Logisitcs
{
    // return concrete product
    @Override
    public Transport createTransport()
    {
        return new Truck();
    }
}
