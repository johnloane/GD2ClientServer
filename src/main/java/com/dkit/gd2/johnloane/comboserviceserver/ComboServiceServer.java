package com.dkit.gd2.johnloane.comboserviceserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import static com.dkit.gd2.johnloane.comboservicecore.ComboServiceDetails.*;

public class ComboServiceServer
{
    public static void main(String[] args)
    {

        int countRequests = 0;
        boolean continueRunning = true;

        DatagramSocket serverSocket = null;

        try
        {
            //Create a socket to listen for and send messages
            serverSocket = new DatagramSocket(serverPort);

            System.out.println("Server is ready on port: " + serverPort);

            //Loop forever, processing requests
            while(continueRunning)
            {
                byte[] incomingMessageBuffer = new byte[MAX_LEN];
                DatagramPacket incomingPacket = new DatagramPacket(incomingMessageBuffer, incomingMessageBuffer.length);

                //Wait to receive the next incoming request
                serverSocket.receive(incomingPacket);
                //Get the data out of the packet
                String data = new String(incomingPacket.getData(), incomingPacket.getOffset(), incomingPacket.getLength());

                System.out.println(data);

                //Process the information in the packet
                //Trim off any excess whitespace
                data = data.trim();

                //Break the message based on the breaking character
                String[] components = data.split(breakingCharacters);
                countRequests++;

                String response = null;

                //Work out what command has been sent
                if(components[0].equalsIgnoreCase("echo"))
                {
                    //Strip the echo and the breaking characters off the text being sent back
                    response = data.replace("echo"+breakingCharacters, "");
                }
                else if(components[0].equalsIgnoreCase("daytime"))
                {
                    response = new Date().toString();
                }
                else if(components[0].equalsIgnoreCase("stats"))
                {
                    response = "The number of requests dealth with is " + countRequests;
                }
                else
                {
                    response = "Unrecognised request. The protocol does currently support that feature. Please ask Niall or Niall to implement it";
                }

                //Get the address of who sent it
                InetAddress requestorAddress = incomingPacket.getAddress();
                System.out.println(requestorAddress);

                byte[] responseBuffer = response.getBytes();
                //Create a packet and add response
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, requestorAddress, clientPort);
                serverSocket.send(responsePacket);
            }
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        finally
        {
            if(serverSocket != null)
            {
                serverSocket.close();
            }
        }
    }
}
