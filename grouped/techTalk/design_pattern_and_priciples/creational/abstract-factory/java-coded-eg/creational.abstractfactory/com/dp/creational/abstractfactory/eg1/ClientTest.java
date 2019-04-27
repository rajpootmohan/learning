package com.dp.creational.abstractfactory.eg1;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class ClientTest
{
    Client client = new Client(new FactoryMock());

    @Test
    public void testOperation()
    {
        assertEquals("Hello World from ProductAMock and ProductBMock", client.operation());
    }
}
