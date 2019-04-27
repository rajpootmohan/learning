// https://refactoring.guru/design-patterns/builder
package com.dp.creational.builder.eg2;

//Director gets the concrete builder object from the client (application code).
//That is because application knows better which builder it has to use to get a
//specific product
public class ClientApplication
{
    private Director director = new Director();

    public void makeCar()
    {
        CarBuilder carBuilder = new CarBuilder();
        director.constructSportsCar(carBuilder);
        // The final product is often retrieved from a builder object, since
        // Director is not aware and not dependent on concrete builders
        // and products
        Car car = carBuilder.getResult();

        CarManualBuilder carManualBuilder = new CarManualBuilder();
        director.constructSportsCar(carManualBuilder);
        // The final product is often retrieved from a builder object, since
        // Director is not aware and not dependent on concrete builders
        // and products
        Manual manual = carManualBuilder.getResult();

    }
}
