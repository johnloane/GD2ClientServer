package com.dkit.gd2.johnloane.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * A client-server application that accepts a connection and sends a message using stream sockets (TCP)
 *
 */
public class Server
{
    public static void main( String[] args )
    {
        //Take in some information
        Scanner keyboard = new Scanner(System.in);
        //Take in the port number that the server accept connections on
        System.out.print("Please enter the server port number: ");
        //50005
        int portNumber = keyboard.nextInt();
        //Clear the scanner's buffer
        keyboard.nextLine();

        //Take in the message that the server should send back to the clients
        System.out.println("Please enter the message to return to client");
        String message = keyboard.nextLine();
        keyboard.close();

        try
        {
            //Set up a connection point
            //Create a socket for accepting connections
            ServerSocket connectionSocket = new ServerSocket(portNumber);
            System.out.println("Now ready to accept a connection");

            //wait to accept a connection, at which time a data socket will be created
            //accept() method returns a datasocket
            Socket dataSocket = connectionSocket.accept();
            System.out.println("Connection accepted");

            //Get an output stream to write to the datasocket
            OutputStream outStream = dataSocket.getOutputStream();
            //create a PrintWriter to write to the socket
            PrintWriter socketOutput = new PrintWriter(new OutputStreamWriter(outStream));

            //Use the outputstream's write to write to the socket
            socketOutput.println(message);

            //Flush the stream - ensure that data is written the socket and there hanging around
            socketOutput.flush();
            System.out.println("Message sent");

            //Message has been sent. Close all sockets
            dataSocket.close();
            System.out.println("Data socket closed");

            connectionSocket.close();
            System.out.println("Connection socket closed");
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }
}
