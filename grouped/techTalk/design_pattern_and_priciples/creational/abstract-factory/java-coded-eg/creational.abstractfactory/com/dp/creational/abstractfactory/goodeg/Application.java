package com.dp.creational.abstractfactory.goodeg;

//https://refactoring.guru/design-patterns/abstract-factory
//Factory users do not care which concrete factory they use since they work
//with factories and products through abstract interfaces.
public class Application
{
    private GUIFactory factory;

    private Button button;

    private Checkbox checkbox;

    public Application(GUIFactory factory)
    {
        this.factory = factory;
    }

    public void createUI()
    {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
        paint();
    }

    private void paint()
    {
        button.paint();
        checkbox.paint();
    }

    public GUIFactory getFactory()
    {
        return factory;
    }

    public Button getButton()
    {
        return button;
    }

    public Checkbox getCheckbox()
    {
        return checkbox;
    }

    public void setFactory(GUIFactory factory)
    {
        this.factory = factory;
    }
}
