package com.dkit.gd2.johnloane.cmobostreamserviceserver;

import com.dkit.gd2.johnloane.combostreamservicecore.ComboServiceDetails;
import com.dkit.gd2.johnloane.combostreamservicecore.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class ThreadPerClient implements Runnable
{
    private Socket dataSocket;

    public ThreadPerClient(Socket dataSocket)
    {
        this.dataSocket = dataSocket;
    }

    @Override
    public void run()
    {
        //Step 3: Build the input and output streams linked to the datasocket
        try
        {
            OutputStream out = dataSocket.getOutputStream();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(out));

            InputStream in = dataSocket.getInputStream();
            //An example of the Decorator design pattern
            Scanner input = new Scanner(new InputStreamReader(in));

            Protocol incomingMessageType = Protocol.NONE;
            String incomingMessage;
            String response;
            boolean sendMessage = false;

            while (!incomingMessageType.equals(Protocol.END))
            {
                response = null;

                //take information from the client

                incomingMessage = input.nextLine();
                System.out.println("Received message " + incomingMessage);

                countRequests++;

                //Break the message up into components
                String[] components = incomingMessage.split(ComboServiceDetails.BREAKING_CHARACTERS);

                incomingMessageType = Protocol.valueOf(components[0]);
                if (components[0].equals(Protocol.ECHO.name()))
                {
                    //StringBuffer is synchronized while StringBuilder is not
                    StringBuffer echoMessage = new StringBuffer("");
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
                            sendMessage = true;
                        }
                    }
                    response = echoMessage.toString();
                    sendMessage = true;
                } else if (components[0].equals(Protocol.DAYTIME.name()))
                {
                    response = new Date().toString();
                    sendMessage = true;
                } else if (components[0].equals(Protocol.STATS.name()))
                {
                    response = "The number of requests is " + countRequests;
                    sendMessage = true;
                } else if (components[0].equals(Protocol.END.name()))
                {
                    //continueRunning = false;
                    sendMessage = false;
                } else
                {
                    response = "That feature is implemented yet.";
                    sendMessage = true;
                }


                if (sendMessage)
                {
                    output.println(response);
                    output.flush();
                }
            }
            dataSocket.close();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}
