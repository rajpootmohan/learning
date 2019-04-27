package com.dp.creational.prototype.solution;

public class PrototypeFactory
{
    private ProductA productA;

    private ProductB productB;

    public PrototypeFactory(ProductA pa, ProductB pb)
    {
        this.productA = pa;
        this.productB = pb;
    }

    public ProductA createProductA()
    {
        System.out.println("PrototypeFactory: Cloning a ProductA object.");
        return productA.clone();
    }

    public ProductB createProductB()
    {
        System.out.println("PrototypeFactory: Cloning a ProductB object.");
        return productB.clone();
    }
}
