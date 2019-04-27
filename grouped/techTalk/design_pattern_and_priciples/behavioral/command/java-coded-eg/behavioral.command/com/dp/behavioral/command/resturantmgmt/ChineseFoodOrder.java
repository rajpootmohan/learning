package com.dp.behavioral.command.resturantmgmt;

//ConcreteCommand
public class ChineseFoodOrder implements IFoodOrder
{
    private Food food;

    // you may also need to maintain a list of available chefs and have
    // a way to assign the order to one of free chef.
    private Chef chef;

    public ChineseFoodOrder(Food food, Chef chef)
    {
        this.food = food;
        this.chef = chef;
    }

    @Override
    public Food prepareFood()
    {
        System.out.println("ChineseFoodOrder(ConcreteCommand) : Delegating prepare chinese food to chef(Receiver)");
        return chef.prepareChineseFood();
    }

}
