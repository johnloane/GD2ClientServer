package com.dkit.gd2.johnloane.cmobostreamserviceserver;

import com.dkit.gd2.johnloane.combostreamservicecore.ComboServiceDetails;

public class EchoCommand implements Command
{
    @Override
    public String createResponse(String incomingMessage)
    {
        //StringBuffer is synchronized while StringBuilder is not
        StringBuffer echoMessage = new StringBuffer("");
        String[] components = incomingMessage.split(ComboServiceDetails.BREAKING_CHARACTERS);
        if (components.length > 1)
        {
            echoMessage.append(components[1]);
            //What is the user included && in the message?
            //It should still be sent back to them
            //echo&&Hello&&From&&John -> Hello&&From&&John
            for (int i = 2; i < components.length; i++)
            {
                echoMessage.append(ComboServiceDetails.BREAKING_CHARACTERS);
                echoMessage.append(components[i]);
            }
        }
        return echoMessage.toString();
    }
}
