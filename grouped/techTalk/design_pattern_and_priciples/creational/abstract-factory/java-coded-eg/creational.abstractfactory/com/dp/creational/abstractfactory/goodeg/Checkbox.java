package com.dp.creational.abstractfactory.goodeg;

// https://refactoring.guru/design-patterns/abstract-factory
//This pattern assumes that you have several families of products, structured
//into separate class hierarchies (Button/Checkbox). All products of the same
//family must follow the common interface
public interface Checkbox
{
    void paint();
}
