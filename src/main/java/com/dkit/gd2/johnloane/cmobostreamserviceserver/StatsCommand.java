package com.dkit.gd2.johnloane.cmobostreamserviceserver;

public class StatsCommand implements Command
{
    private int requestCount;

    public StatsCommand(int requestCount)
    {
        this.requestCount = requestCount;
    }

    @Override
    public String createResponse(String incomingMessage)
    {
        return "The number of requests the server has issued is " + requestCount;
    }
}
