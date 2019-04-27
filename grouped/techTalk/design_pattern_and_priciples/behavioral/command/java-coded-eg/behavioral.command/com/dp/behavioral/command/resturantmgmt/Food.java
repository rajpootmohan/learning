package com.dp.behavioral.command.resturantmgmt;

// might need factory method pattern to give different types of 
// food instances
public class Food
{
    private String name;

    private short quantity;

    // hardcoded for now but needs a better way to manage
    private int price = 2;

    public Food(String name, short quantity)
    {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public short getQuantity()
    {
        return quantity;
    }

    public void setQuantity(short quantity)
    {
        this.quantity = quantity;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }
}
