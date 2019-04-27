package com.hfdp.creational.abstractfactory.pizzaeg;

public interface PizzaIngredientFactory
{
    Dough createDough();

    Sauce createSauce();

    Cheese createCheese();

    Clams createClam();
    
    // plus other methods like createVeggies(), createPepperoni() and for them
    // we can define their own product families

}
