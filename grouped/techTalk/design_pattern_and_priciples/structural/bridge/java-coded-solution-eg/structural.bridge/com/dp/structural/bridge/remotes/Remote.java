package com.dp.structural.bridge.remotes;

//Remotes contain references to the device they control.
//Remotes delegate most of the work to their device objects.
public interface Remote
{
    public void power();

    public void volumeDown();

    public void volumeUp();

    public void channelDown();

    public void channelUp();
}
