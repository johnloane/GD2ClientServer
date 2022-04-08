package com.dkit.gd2.johnloane.combostreamserviceclient;

import com.dkit.gd2.johnloane.combostreamservicecore.ComboServiceDetails;
import com.dkit.gd2.johnloane.combostreamservicecore.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
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
            while(!message.equals(Protocol.END.name()))
            {
                displayMenu();
                Protocol choice = getChoice(keyboard);
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

                    case DAYTIME:
                        message = Protocol.DAYTIME.name();

                        //Send message to server
                        output.println(message);
                        output.flush();

                        //Get the response from the server
                        response = input.nextLine();
                        System.out.println("The current date and time is: " + response);
                        break;

                    case STATS:
                        message = Protocol.STATS.name();

                        //Send message to server
                        output.println(message);
                        output.flush();

                        //Get the response from the server
                        response = input.nextLine();
                        System.out.println(response);
                        break;
                    case END:
                        message = Protocol.END.name();
                }
                if(response.equals("Unrecognised"))
                {
                    System.out.println("Please choose an option from the menu");
                }
            }
            System.out.println("Thank you for using the Combo Stream Service");
            dataSocket.close();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }

    private static String generateEcho(Scanner keyboard)
    {
        StringBuffer message = new StringBuffer(Protocol.ECHO.name());
        message.append(ComboServiceDetails.BREAKING_CHARACTERS);
        System.out.println("Please enter the message to echo: ");
        String echo = keyboard.nextLine();
        message.append(echo);
        return message.toString();
    }

    private static void displayMenu()
    {
        System.out.println("Please choose one of the following options: ");
        System.out.println("1) To echo a message");
        System.out.println("2) To get current date and time");
        System.out.println("3) View the number of requests that the server has serviced");
        System.out.println("0) To quit the program");
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
}
