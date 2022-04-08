package com.dkit.gd2.johnloane.cmobostreamserviceserver;

import java.util.Date;

public class DaytimeCommand implements Command
{
    @Override
    public String createResponse(String incomingMessage)
    {
        return new Date().toString();
    }
}
