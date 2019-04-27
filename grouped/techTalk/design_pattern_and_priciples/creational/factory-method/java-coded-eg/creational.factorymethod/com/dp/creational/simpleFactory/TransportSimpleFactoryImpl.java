package com.dp.creational.simpleFactory;

public class TransportSimpleFactoryImpl implements ITransportSimpleFactory
{
    // Although, its good but it violates Open/Closed principle. For new transportType
    // code will be modified.
    @Override
    public Transport createTransport(TransportType transportType)
    {
        switch (transportType) {
            case TRUCK:
                return new Truck();
            case SEA:
                return new Ship();
            default:
                return null;
        }
    }
}
