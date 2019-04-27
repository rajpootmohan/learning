package com.dp.behavioral.strategy.eg2;

public class MyAppClient
{
    public static void main(String[] args)
    {
        IProduct product1A = new SalesProduct(1, 100, "Product1A", "01");
        IProduct product1B = new SalesProduct(2, 200, "Product1B", "01");

        IOrderLine orderLine = new SalesOrderLine(product1A, 1);

        IPricingStrategy pricingStrategy = new PercentagePricingStrategy();
        IOrder order = new SalesOrder(pricingStrategy);
        order.addOrderLines(orderLine);

        orderLine = new SalesOrderLine(product1B, 1);
        order.addOrderLines(orderLine);

        System.out.println("Order actual price : " + order.getPrice());
        System.out.println("Using the default percentage strategy (10%) : " + order.getNetPrice());
        System.out.println("Changing to threshold strategy (10%; above 200: 20%):"
            + order.getNetPrice(new ThresholdPricingStrategy()));
    }
}
