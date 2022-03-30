package com.dkit.gd2.johnloane.client;

//An application that connects to the server and receives a message using stream sockets (TCP)

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        //Take in information from the user
        Scanner keyboard = new Scanner(System.in);

        //Take in the IP address
        System.out.println("Please enter the IP address of the server");
        String hostName = keyboard.nextLine();

        //Take in the port number
        System.out.println("Please enter the port of the server");
        int portNumber = keyboard.nextInt();
        keyboard.close();

        try
        {
            //Convert the IP address entered to an IpAddress
            InetAddress serverIP = InetAddress.getByName(hostName);

            //Try to create a data socket connected to the server at the IP and port entered. This will fail if the server is not running
            Socket clientSocket = new Socket(serverIP, portNumber);
            System.out.println("Connection request granted");

            //Get an inputstream to read from the data socket
            InputStream inStream = clientSocket.getInputStream();
            //Create a Scanner to data from the input stream
            Scanner clientSocketInput = new Scanner(new InputStreamReader(inStream));
            System.out.println("Waiting to read....");

            //Wait for message from server and display it when it arrives
            String message = clientSocketInput.nextLine();
            System.out.println("Message received:");
            System.out.println("\t" + message);
            clientSocketInput.close();

            //close the socket
            clientSocket.close();
            System.out.println("Data socket closed");
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}
