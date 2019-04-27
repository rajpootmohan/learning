package com.dp.creational.builder.eg2;

// Director constructs products using a Builder object.
// Note, that Builder does not require creating a director class. 
// The separate director class is handy when you have several 
// product variations that require different construction processes. 
// Director can encapsulate all that code inside a single class.
public class Director
{
    // Director defines the order of building steps. It works with a builder object
    // through the common builder interface. Therefore it may not know what product
    // is being built
    public void constructSportsCar(IBuilder builder)
    {
        builder.reset();
        builder.setSeats(2);
        builder.setEngine("SportsEngine");
        builder.setTripComputer("trip computer");
        builder.setGPS("gps");
    }
}
