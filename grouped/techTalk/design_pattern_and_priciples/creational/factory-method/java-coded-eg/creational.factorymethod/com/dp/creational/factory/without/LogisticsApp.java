package com.dp.creational.factory.without;

public class LogisticsApp
{
    // coupled with truck concrete class
    // What if tomorrow new type of logistics is introduced like ship?
    // You will have to make lot of changes. Breaks Open for extension
    // & closed for modification
    private Truck truck;
    
    public LogisticsApp(Truck truck) {
        this.truck = truck;
    }
    
    // client
    public static void main(String[] args)
    {
        // What if tomorrow construction of truck class changes and let's say
        // it requires some parameters like goodsType, weight - then we will 
        // have to change everywhere.
        LogisticsApp client = new LogisticsApp(new Truck());
        client.truck.deliver();
    }
}
