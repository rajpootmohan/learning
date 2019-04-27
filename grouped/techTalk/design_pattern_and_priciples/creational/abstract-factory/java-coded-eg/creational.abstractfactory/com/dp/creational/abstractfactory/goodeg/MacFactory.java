package com.dp.creational.abstractfactory.goodeg;

// https://refactoring.guru/design-patterns/abstract-factory
//Each concrete factory extends basic factory and responsible for creating
//products of a single variety.
public class MacFactory implements GUIFactory
{

    @Override
    public Button createButton()
    {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox()
    {
        return new MacCheckbox();
    }

}
