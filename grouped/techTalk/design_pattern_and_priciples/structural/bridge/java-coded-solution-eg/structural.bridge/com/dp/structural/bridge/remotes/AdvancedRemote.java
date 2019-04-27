package com.dp.structural.bridge.remotes;

import com.dp.structural.bridge.devices.Device;

public class AdvancedRemote extends BasicRemote
{
    public AdvancedRemote(Device device)
    {
        super.device = device;
    }

    public void mute()
    {
        System.out.println("Remote: mute");
        device.setVolume(0);
    }
}
