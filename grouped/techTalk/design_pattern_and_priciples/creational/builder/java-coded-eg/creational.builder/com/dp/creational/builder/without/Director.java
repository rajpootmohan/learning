package com.dp.creational.builder.without;

// client
public class Director
{
    public ComplexProduct construct()
    {
        System.out.println("Director constructing the complex object");
        ComplexProduct cp = new ComplexProduct();
        cp.assembleProduct(buildPartA());
        cp.assembleProduct(buildPartB());
        return cp;
    }

    private Product buildPartA()
    {
        return new ProductA1();
    }

    private Product buildPartB()
    {
        return new ProductB1();
    }

}
