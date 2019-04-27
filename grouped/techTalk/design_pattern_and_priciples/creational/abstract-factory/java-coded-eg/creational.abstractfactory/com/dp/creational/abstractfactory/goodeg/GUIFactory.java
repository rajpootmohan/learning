package com.dp.creational.abstractfactory.goodeg;

//https://refactoring.guru/design-patterns/abstract-factory
//Abstract factory knows about all (abstract) product types.
public interface GUIFactory
{
    Button createButton();

    Checkbox createCheckbox();
}
