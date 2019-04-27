package com.dp.behavioral.strategy.eg2;

public class PercentagePricingStrategy implements IPricingStrategy
{

    @Override
    public double calculate(IOrder order)
    {
        int discountPercentage = 10;
        double actualPrice = order.getPrice();
        double rebate = actualPrice * discountPercentage / 100;
        return actualPrice - rebate;
    }

}
