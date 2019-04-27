package com.dp.creational.factoryMethod;

// Creator
public abstract class Logisitcs
{
    public void planDelivery()
    {
        Transport t = createTransport();
        t.deliver();
    }

    // factory method - returns product interface to reap
    // the benefits of polymorphism
    public abstract Transport createTransport();
}
