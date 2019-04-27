package com.dp.creational.abstractfactory.goodeg;

public class WinButton implements Button
{

    @Override
    public void paint()
    {
        System.out.println("Drawing a windows button");
    }

}
