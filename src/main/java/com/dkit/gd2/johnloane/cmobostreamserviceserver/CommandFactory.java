package com.dkit.gd2.johnloane.cmobostreamserviceserver;

import com.dkit.gd2.johnloane.combostreamservicecore.Protocol;

public class CommandFactory
{
    private int requestCount;

    public CommandFactory(int requestCount)
    {
        this.requestCount = requestCount;
    }

    public Command createCommand(Protocol command)
    {
        Command c = null;

        if(command == Protocol.ECHO)
        {
            c = new EchoCommand();
        }
        if(command == Protocol.DAYTIME)
        {
            c = new DaytimeCommand();
        }
        if(command == Protocol.STATS)
        {
            c = new StatsCommand(requestCount);
        }
        return c;
    }
}
