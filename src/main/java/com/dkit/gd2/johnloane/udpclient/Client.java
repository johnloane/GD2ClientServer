package com.dkit.gd2.johnloane.udpclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client
{
    public static void main(String[] args)
    {
        DatagramSocket clientSocket = null;
        final int MAX_LEN = 240;

        try
        {
            //Set up the server address
            InetAddress serverAddress = InetAddress.getByName("localhost");
            //Create a port for the client to send and receive on
            int clientPort = 50006;
            int serverPort = 50005;

            //Message to be sent
            String message = "Hello from John";

            clientSocket = new DatagramSocket(clientPort);

            //Get the message to be sent a byte array
            byte buffer[] = message.getBytes();

            //Build a datagram with the message
            //Requires:
            //  buffer: Data to be sent as a byte array
            //  buffer.length: Length of the data to be sent
            //  serverAddress: IP address of the server
            //  serverPort: Port the server is running on
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);

            //Send the datagram
            clientSocket.send(datagram);

            System.out.println("Message sent");

            //Receive response from the server
            //Create DatagramPacket to receive response
            byte[] response = new byte[MAX_LEN];
            DatagramPacket responsePacket = new DatagramPacket(response, response.length);

            clientSocket.receive(responsePacket);

            //Get the content of the response
            String responseMessage = new String(responsePacket.getData(), responsePacket.getOffset(), responsePacket.getLength());
            System.out.println(responseMessage);

            Thread.sleep(5000);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        catch(InterruptedException ie)
        {
            System.out.println(ie.getMessage());
        }
        finally
        {
            if(clientSocket != null)
            {
                clientSocket.close();
            }
        }
    }
}
