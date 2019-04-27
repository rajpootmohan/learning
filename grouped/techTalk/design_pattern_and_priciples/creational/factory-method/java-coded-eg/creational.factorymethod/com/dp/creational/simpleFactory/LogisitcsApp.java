package com.dp.creational.simpleFactory;

public class LogisitcsApp
{
    // client
    public static void main(String[] args)
    {
        ITransportSimpleFactory transportSimpleFactory = new TransportSimpleFactoryImpl();
        Transport transport = transportSimpleFactory.createTransport(TransportType.TRUCK);
        transport.deliver();
    }

    // Benefit
    // If name of concrete class (product) changes or new product is added or removed, client is not impacted by it.
    // This gives ease of flexibility and maintainability.
    // If tomorrow, transport construction process changes, then client code needs to change. Only, factory class has to change
}
