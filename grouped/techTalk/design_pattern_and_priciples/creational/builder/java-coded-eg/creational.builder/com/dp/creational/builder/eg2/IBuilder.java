package com.dp.creational.builder.eg2;

//Builder interface defines all possible ways to build a product.
public interface IBuilder
{
    void reset();
    void setSeats(int count);
    void setEngine(String engine);
    void setTripComputer(String computer);
    void setGPS(String gps);
}
