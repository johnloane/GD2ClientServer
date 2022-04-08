package com.dkit.gd2.johnloane.cmobostreamserviceserver;

import com.dkit.gd2.johnloane.combostreamservicecore.ComboServiceDetails;
import com.dkit.gd2.johnloane.combostreamservicecore.Protocol;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ComboStreamServer
{
    public static void main(String[] args)
    {
        try
        {
            //Step 1: Set up a connection sockets that listens for connections
            ServerSocket listeningSocket = new ServerSocket(ComboServiceDetails.SERVER_PORT);

            boolean continueRunning = true;
            int countRequests = 0;

            while(continueRunning)
            {
                //Step 2: wait for an incoming connection and build the communcations link - dataSocket for comms
                System.out.println("Waiting for connections...");
                Socket dataSocket = listeningSocket.accept();

                //Create and start a new thread per client
                ThreadPerClient runnable = new ThreadPerClient(dataSocket);
                Thread clientThread = new Thread(runnable);
                clientThread.start();
            }
            listeningSocket.close();
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
