package com.dp.creational.abstractfactory.goodeg;

//https://refactoring.guru/design-patterns/abstract-factory
//Although concrete factories create the concrete products, they still return
//them with the abstract type. This fact makes factories interchangeable.
public class WinFactory implements GUIFactory
{

    @Override
    public Button createButton()
    {
        return new WinButton();
    }

    @Override
    public Checkbox createCheckbox()
    {
        return new WinCheckbox();
    }

}
