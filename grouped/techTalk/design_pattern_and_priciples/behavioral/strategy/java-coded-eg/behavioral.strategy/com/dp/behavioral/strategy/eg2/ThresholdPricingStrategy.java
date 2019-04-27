package com.dp.behavioral.strategy.eg2;

public class ThresholdPricingStrategy implements IPricingStrategy
{

    @Override
    public double calculate(IOrder order)
    {
        int threshold = 200;
        double actualPrice = order.getPrice();
        if (actualPrice < threshold) {
            return actualPrice - (actualPrice * 10 / 100);
        } else {
            return actualPrice - (actualPrice * 20 / 100);
        }
    }

}
