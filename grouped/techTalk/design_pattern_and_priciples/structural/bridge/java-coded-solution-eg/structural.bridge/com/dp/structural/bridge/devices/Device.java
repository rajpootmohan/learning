package com.dp.structural.bridge.devices;

//All devices follow the common interface. It makes them
//compatible with all types of remotes.
public interface Device
{
    public boolean isEnabled();

    public void enable();

    public void disable();

    public int getVolume();

    public void setVolume(int percent);

    public int getChannel();

    public void setChannel(int channel);

    public void printStatus();
}
