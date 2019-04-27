package com.dp.behavioral.command.resturantmgmt;

//Client
public class Customer
{
    public static void main(String[] args)
    {
        // Call a Waiter
        Waiter waiter = new Waiter();
        // Order Indian Food and pass the order to a Chef
        waiter.placeFoodOrder(new IndianFoodOrder("idly", new Chef()));
        // Order Chinese Food and pass the order to a Chef
        waiter.placeFoodOrder(new ChineseFoodOrder("noodles", new Chef()));
        // Get the ordered foods and enjoy.
        for (Food food : waiter.serve()) {
            System.out.println("Food Name: " + food.getName() + "\nQuantity: " + food.getQuantity() + " plates\nPrice: "
                + food.getPrice() + " rupees\n");
        }
    }
}
