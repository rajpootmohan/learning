package com.dp.creational.builder.eg2;

//Product is an object created as a result of construction. 
//Builders can produce products that do not belong to the same class 
//hierarchy or interface. It is a key difference between the Builder 
//and other creational patterns.
public class Manual
{
    private String gps;

    private String tripComputer;

    private int seatCount;

    private String engine;

    public String getGps()
    {
        return gps;
    }

    public void setGps(String gps)
    {
        this.gps = gps;
    }

    public String getTripComputer()
    {
        return tripComputer;
    }

    public void setTripComputer(String tripComputer)
    {
        this.tripComputer = tripComputer;
    }

    public int getSeatCount()
    {
        return seatCount;
    }

    public void setSeatCount(int seatCount)
    {
        this.seatCount = seatCount;
    }

    public String getEngine()
    {
        return engine;
    }

    public void setEngine(String engine)
    {
        this.engine = engine;
    }
}
