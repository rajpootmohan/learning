package com.dp.behavioral.strategy.eg2;

public interface IOrder
{
    double getPrice();

    double getNetPrice();

    double getNetPrice(IPricingStrategy pricingStrategy);

    void addOrderLines(IOrderLine orderLine);
}
