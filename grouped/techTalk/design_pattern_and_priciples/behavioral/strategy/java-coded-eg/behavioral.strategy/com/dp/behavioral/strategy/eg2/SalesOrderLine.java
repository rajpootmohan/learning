package com.dp.behavioral.strategy.eg2;

public class SalesOrderLine implements IOrderLine
{
    private IProduct product;

    private int quantity;

    public SalesOrderLine(IProduct product, int quantity)
    {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public IProduct getProduct()
    {
        return product;
    }

    @Override
    public int getQuantity()
    {
        return quantity;
    }

    @Override
    public double getPrice()
    {
        return product.getPrice() * quantity;
    }

}
