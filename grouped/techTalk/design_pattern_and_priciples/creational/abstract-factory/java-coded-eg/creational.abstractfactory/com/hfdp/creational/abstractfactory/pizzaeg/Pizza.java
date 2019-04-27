package com.hfdp.creational.abstractfactory.pizzaeg;

public class Pizza
{
    private PizzaIngredientFactory pizzaIngredientFactory;

    public Pizza(PizzaIngredientFactory pizzaIngredientFactory)
    {
        this.pizzaIngredientFactory = pizzaIngredientFactory;
    }

    public void prepare()
    {
        pizzaIngredientFactory.createDough();
        // ...
    }

}
