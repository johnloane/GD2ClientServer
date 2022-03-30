package com.dkit.gd2.johnloane.udpserver;

// This example uses UDP to send messages from server to client

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Server
{
    public static void main(String[] args)
    {
        int serverPort = 50005;
        int clientPort = 50006;
        boolean continueRunning = true;

        final int MAX_LEN = 240;
        DatagramSocket serverSocket = null;

        try
        {
            //Create a datagram socket for receiving on
            serverSocket = new DatagramSocket(serverPort);

            System.out.println("Waiting for message on port " + serverPort);
            while(continueRunning)
            {
                byte buffer[] = new byte[MAX_LEN];
                DatagramPacket datagram = new DatagramPacket(buffer, MAX_LEN);

                serverSocket.receive(datagram);

                //Convert the buffer of byte to a string
                String message = new String(buffer);

                System.out.println("Message received : \"" + message + "\".");

                //Where did the message come from
                InetAddress sender = datagram.getAddress();
                System.out.println("Sender " + sender);

                //Build a response packet
                String responseMessage = "Message received, thank you!";

                //Get the message as a byte array
                byte[] responseArray = responseMessage.getBytes();
                DatagramPacket response = new DatagramPacket(responseArray, responseArray.length, sender, clientPort);
                serverSocket.send(response);
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
