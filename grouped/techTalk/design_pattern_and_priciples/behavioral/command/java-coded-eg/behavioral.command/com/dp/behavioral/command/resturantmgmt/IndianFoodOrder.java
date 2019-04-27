package com.dp.behavioral.command.resturantmgmt;

//ConcreteCommand
public class IndianFoodOrder implements IFoodOrder
{
    private Food food;

    // you may also need to maintain a list of available chefs and have
    // a way to assign the order to one of free chef.
    private Chef chef;

    public IndianFoodOrder(Food food, Chef chef)
    {
        this.food = food;
        this.chef = chef;
    }

    @Override
    public Food prepareFood()
    {
        System.out.println("IndianFoodOrder(ConcreteCommand) : Delegating prepare indian food to chef(Receiver)");
        return chef.prepareIndianFood();
    }

}
