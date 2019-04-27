package com.dp.creational.factoryMethod;

// client
public class LogisticsApp
{
    private Logisitcs logistics;

    public void configure(String shipmentType)
    {
        if ("Road".equals(shipmentType)) {
            logistics = new RoadLogistics();
        } else if ("Sea".equals(shipmentType)) {
            logistics = new SeaLogistics();
        } else {
            throw new IllegalArgumentException("Invalid shipmentType passed");
        }
    }

    public static void main(String[] args)
    {
        LogisticsApp client = new LogisticsApp();
        client.configure("Road");
        client.logistics.planDelivery();
    }

}
