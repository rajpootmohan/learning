package com.dp.creational.builder.eg1;

public class Builder1 implements Builder
{
    private ComplexProduct co = new ComplexProduct();

    @Override
    public void buildPartA()
    {
        System.out.println("Builder1: Creating and assembling ProductA1.");
        co.assembleProduct(new ProductA1());
    }

    @Override
    public void buildPartB()
    {
        System.out.println("Builder1: Creating and assembling ProductB1.");
        co.assembleProduct(new ProductB1());
    }

    public ComplexProduct getResult()
    {
        return co;
    }
}
