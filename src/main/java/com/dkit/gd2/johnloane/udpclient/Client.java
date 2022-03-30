package com.dkit.gd2.johnloane.udpclient;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client
{
    public static void main(String[] args)
    {
        DatagramSocket clientSocket = null;

        try
        {
            //Set up the server address
            InetAddress serverAddress = InetAddress.getByName("localhost");
            //Create a port for the client to send and receive on
            int clientPort = 50006;
            int serverPort = 50005;

            //Message to be sent
            String message = "Hello from John";

            clientSocket = DatagramSocket(clientPort);

            //Get the message to be sent a byte array
            byte buffer[] = message.getBytes();

            //Build a datagram with the message
            //Requires:
            //  buffer: Data to be sent as a byte array
            //  buffer.length: Length of the data to be sent
            //  serverAddress: IP address of the server
            //  serverPort: Port the server is running on


            //Send the datagram

            //Receive response from the server



        }
    }
}
