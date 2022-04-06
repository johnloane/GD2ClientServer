package com.dkit.gd2.johnloane.comboserviceclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.dkit.gd2.johnloane.comboservicecore.ComboServiceDetails.*;

public class ComboServiceClient
{
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);

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
                    //Create space for the message
                    byte[] responseBuffer = new byte[MAX_LEN];
                    DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                    //Wait to receive the response
                    clientSocket.receive(responsePacket);
                    //Get the data out of the packet
                    String data = new String(responsePacket.getData(), responsePacket.getOffset(), responsePacket.getLength());
                    System.out.println("Response: " + data.trim() + ".");
                }
                System.out.println();
            }
            System.out.println("Thank you for using the Combo Service App");
            Thread.sleep(3000);
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

    private static Protocol getChoice(Scanner keyboard)
    {
        Protocol choice = Protocol.END;
        boolean validChoice = false;
        while(!validChoice)
        {
            try
            {
                int input = keyboard.nextInt();
                choice = Protocol.values()[input];
                validChoice = true;
            }
            catch(InputMismatchException ime)
            {
                System.out.println("Please enter a valid menu option");
                System.out.println();
                displayMenu();
            }
            finally
            {
                keyboard.nextLine();
            }
        }
        return choice;
    }

    private static void displayMenu()
    {
        System.out.println("Please choose one of the following options: ");
        System.out.println("1) To echo a message");
        System.out.println("2) To get current date and time");
        System.out.println("3) View the number of requests that the server has serviced");
        System.out.println("0) To quit the program");
    }
}
