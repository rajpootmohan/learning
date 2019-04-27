package com.dp.behavioral.command.eg2;

//Concrete Command
public class LightOffCommand implements Command<Void, Boolean>
{
    private Light receiver;

    public LightOffCommand(Light light)
    {
        this.receiver = light;
    }

    @Override
    public Boolean execute(Void param)
    {
        return receiver.switchOff();
    }

}
