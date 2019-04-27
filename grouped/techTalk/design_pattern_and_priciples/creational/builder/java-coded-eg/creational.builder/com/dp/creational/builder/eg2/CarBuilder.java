package com.dp.creational.builder.eg2;

//Concrete builders provide different implementation of the construction steps.
//For eg CarBuilder and CarManualBuilder.
//Unlike with other creational patterns, with Builder you can construct
//unrelated products, which do not follow the common interface.
public class CarBuilder implements IBuilder
{
    private Car car;

    // Put a new Car instance into the "car" field
    @Override
    public void reset()
    {
        car = new Car();
    }

    // Set the number of seats in car
    @Override
    public void setSeats(int count)
    {
        car.setSeatCount(count);
    }

    // Install a given engine
    @Override
    public void setEngine(String engine)
    {
        car.setEngine(engine);
    }

    // Install a trip computer
    @Override
    public void setTripComputer(String computer)
    {
        car.setTripComputer(computer);
    }

    // Install a global positioning system
    @Override
    public void setGPS(String gps)
    {
        car.setGps(gps);
    }

    // Return the current car object
    public Car getResult()
    {
        return car;
    }
}
