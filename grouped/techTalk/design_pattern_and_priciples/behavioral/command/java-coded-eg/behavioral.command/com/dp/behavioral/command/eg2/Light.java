package com.dp.behavioral.command.eg2;

//Receiver
public class Light
{
    private boolean on = false;
    
    public boolean switchOn() {
        on = true;
        return true;
    }

    public boolean switchOff() {
        on = false;
        return true;
    }

}
