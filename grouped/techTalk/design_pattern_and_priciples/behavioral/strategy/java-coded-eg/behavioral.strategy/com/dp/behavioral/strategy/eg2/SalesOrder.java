package com.dp.behavioral.strategy.eg2;

import java.util.ArrayList;
import java.util.List;

public class SalesOrder implements IOrder
{
    private List<IOrderLine> orderLines = new ArrayList<>();

    private IPricingStrategy pricingStrategy;

    public SalesOrder(IPricingStrategy pricingStrategy)
    {
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public double getPrice()
    {
        double amount = 0;
        for (IOrderLine orderLine : orderLines) {
            amount += orderLine.getPrice();
        }
        return amount;
    }

    @Override
    public double getNetPrice()
    {
        return pricingStrategy.calculate(this);
    }

    @Override
    public double getNetPrice(IPricingStrategy pricingStrategy)
    {
        return pricingStrategy.calculate(this);
    }

    @Override
    public void addOrderLines(IOrderLine orderLine)
    {
        orderLines.add(orderLine);
    }

}
