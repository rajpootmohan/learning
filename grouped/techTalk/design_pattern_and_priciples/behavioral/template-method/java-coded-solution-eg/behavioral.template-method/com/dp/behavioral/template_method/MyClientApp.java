package com.dp.behavioral.template_method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.dp.behavioral.template_method.networks.Facebook;
import com.dp.behavioral.template_method.networks.Network;
import com.dp.behavioral.template_method.networks.Twitter;

/**
 * Demo class. Everything comes together here.
 */
public class MyClientApp
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Network network = null;
        System.out.print("Input user name: ");
        String userName = reader.readLine();
        System.out.print("Input password: ");
        String password = reader.readLine();

        // Enter the message.
        System.out.print("Input message: ");
        String message = reader.readLine();

        System.out.println("\nChoose social network for posting message.\n" + "1 - Facebook\n" + "2 - Twitter");
        int choice = Integer.parseInt(reader.readLine());

        // Create proper network object and send the message.
        if (choice == 1) {
            network = new Facebook(userName, password);
        } else if (choice == 2) {
            network = new Twitter(userName, password);
        }
        network.post(message);
    }
}
