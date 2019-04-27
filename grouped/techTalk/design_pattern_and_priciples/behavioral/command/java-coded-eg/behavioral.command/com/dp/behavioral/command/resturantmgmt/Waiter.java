package com.dp.behavioral.command.resturantmgmt;

import java.util.ArrayList;
import java.util.List;

//Invoker
public class Waiter
{
    // Think of way that customer can alter his order list while it is
    // being prepared.
    private List<IFoodOrder> customerOrder = new ArrayList<>();

    public boolean placeFoodOrder(IFoodOrder order)
    {
        customerOrder.add(order);
        return true;
    }

    public List<Food> prepareFoodOrder()
    {
        List<Food> foodList = new ArrayList<>();
        for (IFoodOrder order : customerOrder) {
            foodList.add(order.prepareFood());
        }
        return foodList;
    }

    // Think of a callback mechanism from chef saying whenever he has prepared the order
    // waiter could be alerted to pick it and serve. But, maybe, waiter is working on pull
    // mechanism and always keeps checking the order prepared and serve it.
    public void serveFoodOrder()
    {
        // TODO Auto-generated method stub

    }
}
