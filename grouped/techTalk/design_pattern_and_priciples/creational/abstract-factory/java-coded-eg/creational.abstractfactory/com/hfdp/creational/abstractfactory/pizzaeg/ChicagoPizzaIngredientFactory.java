package com.hfdp.creational.abstractfactory.pizzaeg;

public class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory
{

    @Override
    public Dough createDough()
    {
        return new ThickCrustDough();
    }

    @Override
    public Sauce createSauce()
    {
        return new PlumTomatoSauce();
    }

    @Override
    public Cheese createCheese()
    {
        return new MozzarellaCheese();
    }

    @Override
    public Clams createClam()
    {
        return new FrozenClams();
    }

}
