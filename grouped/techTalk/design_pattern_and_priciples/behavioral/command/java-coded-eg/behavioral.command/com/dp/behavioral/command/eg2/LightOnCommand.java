package com.dp.behavioral.command.eg2;

//Concrete Command
public class LightOnCommand implements Command<Void, Boolean>
{
    private Light receiver;

    public LightOnCommand(Light light)
    {
        this.receiver = light;
    }

    @Override
    public Boolean execute(Void param)
    {
        return receiver.switchOn();
    }

}
