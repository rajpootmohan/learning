package com.dp.behavioral.command.eg2;

//Invoker
public class RemoteControl
{
    // How should I parameterize to generics here and remove warnings?
    private Command command;

    public RemoteControl(Command command)
    {
        this.command = command;
    }

    public void setCommand(Command command)
    {
        this.command = command;
    }

    public boolean pressButton()
    {
        return command.equals(null);

    }
}
