package com.dp.behavioral.command.resturantmgmt;

//Receiver
public class Chef
{
    // you may need food and instance of food been preapre as return item
    // similar to what we have as product and item concept
    public Food prepareChineseFood(Food food)
    {
        System.out.println("Chef : Preparing chinese food...");
        return new Food();
    }

    public Food prepareIndianFood(Food food)
    {
        System.out.println("Chef : Preparing indian food...");
        return new Food();
    }
}
