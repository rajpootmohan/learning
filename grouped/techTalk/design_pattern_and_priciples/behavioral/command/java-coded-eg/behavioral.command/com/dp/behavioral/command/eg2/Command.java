package com.dp.behavioral.command.eg2;

//Command
public interface Command<ParamType, ReturnType>
{
    ReturnType execute(ParamType param);
}
