package com.dp.creational.prototype.solution;

public class ProductA1 implements ProductA
{
    private String name;

    public ProductA1(String name)
    {
        this.name = name;
    }

    public ProductA1(ProductA productA)
    {
        this(productA.getName());
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public ProductA clone()
    {
        return new ProductA1(this);
    }
}
