package com.dkit.gd2.johnloane.comboserviceclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ComboServiceClient
{
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);

        final int serverPort = 50005;
        final int clientPort = 50006;
        String breakingCharacters = "&&";
        final int MAX_LEN = 150;

        DatagramSocket clientSocket = null;

        try
        {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            clientSocket = new DatagramSocket(clientPort);

            boolean continueRunning = true;

            while(continueRunning)
            {
                displayMenu();
                Protocol choice = getChoice(keyboard);
                String message = null;
                boolean sendMessage = true;

                switch(choice)
                {
                    case END:
                    {
                        continueRunning = false;
                        sendMessage = false;
                        break;
                    }
                    case ECHO:
                    {
                        System.out.println("Please enter the message to be echoed");
                        message = "echo"+breakingCharacters+keyboard.nextLine();
                        break;
                    }
                    case DAYTIME:
                    {
                        message = "daytime";
                        break;
                    }
                    case STATS:
                    {
                        message = "stats";
                        break;
                    }
                    default:
                    {
                        System.out.println("Please ask Niall or Niall to implement this option. It is currently part of the protocol");
                        sendMessage = false;
                    }
                }
                if(sendMessage)
                {
                    byte buffer[] = message.getBytes();
                    DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                    clientSocket.send(requestPacket);
                    System.out.println("Message sent");

                    //Wait for and process the response....
                    //This is your homework....
                }
            }
        }
    }
}
