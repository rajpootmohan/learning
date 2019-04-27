package com.dp.behavioral.strategy.eg2;

public interface IOrderLine
{
    IProduct getProduct();

    int getQuantity();

    double getPrice();
}
