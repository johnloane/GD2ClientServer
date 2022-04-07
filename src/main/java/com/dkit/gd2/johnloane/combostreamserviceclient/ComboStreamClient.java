package com.dkit.gd2.johnloane.combostreamserviceclient;

import com.dkit.gd2.johnloane.combostreamservicecore.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static com.dkit.gd2.johnloane.combostreamservicecore.ComboServiceDetails.SERVER_PORT;

public class ComboStreamClient
{
    public static void main(String[] args)
    {
        try
        {
            //Step 1: Establish a connection with the server
            Socket dataSocket = new Socket("localhost", SERVER_PORT);

            //Step 2: Build input and output streams linked to the socket
            OutputStream out = dataSocket.getOutputStream();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(out));

            InputStream in = dataSocket.getInputStream();
            //An example of the Decorator design pattern
            Scanner input = new Scanner(new InputStreamReader(in));

            //Step 3: Get the input from the user
            Scanner keyboard = new Scanner(System.in);
            Protocol messageType = Protocol.NONE;
            String message = "";

            //Step 4: Depending on input build a message, send it to the server and wait for a response
            while(!message.name().equals(Protocol.END.name()))
            {
                displayMenu();
                Protocol choice = Protocol.values()[getNumber(keyboard)];
                String response = "";

                switch(choice)
                {
                    case ECHO:
                        message = generateEcho(keyboard);

                        //Send the message
                        output.println(message);
                        output.flush();

                        //Get the response
                        response = input.nextLine();
                        System.out.println("Response " + response);
                        break;
                }


            }
        }
    }
}
