package com;

import java.io.*;
import java.net.*;

public class Supplier {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket Supplier = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int WarehouseSocketNumber = 4545;
        String WarehouseServerName = "localhost";
        String WarehouseClientID = "Supplier";

        try {
            Supplier = new Socket(WarehouseServerName, WarehouseSocketNumber);
            out = new PrintWriter(Supplier.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(Supplier.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ WarehouseSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + WarehouseClientID + " client and IO connections");
        
        // This is modified as it's the client that speaks first

        while (true) {
            
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(WarehouseClientID + " sending " + fromUser + " to WarehouseServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(WarehouseClientID + " received " + fromServer + " from WarehouseServer");
        }
         
    }
}

