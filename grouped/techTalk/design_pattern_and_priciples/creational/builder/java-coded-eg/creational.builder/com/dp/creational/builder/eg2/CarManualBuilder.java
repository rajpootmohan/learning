package com.dp.creational.builder.eg2;

// Concrete builders provide different implementation of the construction steps.
// For eg CarBuilder and CarManualBuilder.
// Unlike with other creational patterns, with Builder you can construct
// unrelated products, which do not follow the common interface.
public class CarManualBuilder implements IBuilder
{

    private Manual manual;

    // Put a new Manual instance into the "manual" field
    @Override
    public void reset()
    {
        manual = new Manual();
    }

    // Document car seats features
    @Override
    public void setSeats(int count)
    {
        manual.setSeatCount(count);
    }

    // Add an engine instruction
    @Override
    public void setEngine(String engine)
    {
        manual.setEngine(engine);
    }

    // Add a trip computer instruction
    @Override
    public void setTripComputer(String computer)
    {
        manual.setTripComputer(computer);
    }

    // Add GPS instruction
    @Override
    public void setGPS(String gps)
    {
        manual.setGps(gps);
    }

    // Return the current manual object
    public Manual getResult()
    {
        return manual;
    }
}
