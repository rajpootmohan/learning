package com.dp.structural.bridge.client;

import com.dp.structural.bridge.devices.Device;
import com.dp.structural.bridge.devices.Radio;
import com.dp.structural.bridge.devices.Tv;
import com.dp.structural.bridge.remotes.AdvancedRemote;
import com.dp.structural.bridge.remotes.BasicRemote;

public class Client
{
    public static void main(String[] args)
    {
        testDevice(new Tv());
        testDevice(new Radio());
    }

    public static void testDevice(Device device)
    {
        System.out.println("Tests with basic remote.");
        BasicRemote basicRemote = new BasicRemote(device);
        basicRemote.power();
        device.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(device);
        advancedRemote.power();
        advancedRemote.mute();
        device.printStatus();
    }
}
