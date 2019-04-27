package com.dp.creational.prototype.solution;

public class ProductB1 implements ProductB
{
    private String name;

    public ProductB1(String name)
    {
        this.name = name;
    }

    public ProductB1(ProductB productB)
    {
        this(productB.getName());
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public ProductB clone()
    {
        return new ProductB1(this);
    }

}
