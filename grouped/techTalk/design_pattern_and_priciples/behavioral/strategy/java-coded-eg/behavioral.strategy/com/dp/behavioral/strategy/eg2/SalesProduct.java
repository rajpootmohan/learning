package com.dp.behavioral.strategy.eg2;

public class SalesProduct implements IProduct
{
    private long id;

    private double price;

    private String description;

    private String group;

    public SalesProduct(long id, double price, String description, String group)
    {
        this.id = id;
        this.price = price;
        this.description = description;
        this.group = group;
    }

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public double getPrice()
    {
        return price;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public String getGroup()
    {
        return group;
    }

    @Override
    public void operation()
    {
        System.out.println("SalesProduct: Performing an operation ...");
    }

}
