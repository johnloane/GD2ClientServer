package com.dkit.gd2.johnloane.cmobostreamserviceserver;

import com.dkit.gd2.johnloane.combostreamservicecore.ComboServiceDetails;
import com.dkit.gd2.johnloane.combostreamservicecore.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.NoSuchElementException;
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
            int countRequests = 0;

            while (!incomingMessageType.equals(Protocol.END))
            {
                response = null;

                //take information from the client

                incomingMessage = input.nextLine();
                System.out.println("Received message " + incomingMessage);

                String[] components = incomingMessage.split(ComboServiceDetails.BREAKING_CHARACTERS);
                incomingMessageType = Protocol.valueOf(components[0]);

                countRequests++;

                CommandFactory factory = new CommandFactory(countRequests);
                //Figure out what command was sent by the client
                Command command =factory.createCommand(incomingMessageType);

                if(command != null)
                {
                    response = command.createResponse(incomingMessage);
                }

                //Send the response to the client
                output.println(response);
                output.flush();
            }
            dataSocket.close();
        }
        catch(NoSuchElementException nse)
        {
            System.out.println(nse.getMessage());
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}
