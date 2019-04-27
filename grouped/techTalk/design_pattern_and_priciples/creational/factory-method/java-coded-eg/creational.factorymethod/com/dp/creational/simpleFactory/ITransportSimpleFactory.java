package com.dp.creational.simpleFactory;

public interface ITransportSimpleFactory
{
    Transport createTransport(TransportType transportType);
}
