package com.dp.behavioral.command.eg2;

public class MyAppClient
{

    public static void main(String[] args)
    {
        Light receiver = new Light();

        // How should I parameterize to generics here and remove warnings?
        Command switchOncommand = new LightOnCommand(receiver);
        RemoteControl invoker = new RemoteControl(switchOncommand);
        invoker.pressButton();

        // How should I parameterize to generics here and remove warnings?
        Command switchOffcommand = new LightOffCommand(receiver);
        invoker.setCommand(switchOffcommand);
        invoker.pressButton();

    }
}
