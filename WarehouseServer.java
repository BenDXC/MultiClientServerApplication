package com;

import java.net.*;
import java.io.*;

public class WarehouseServer {
  public static void main(String[] args) throws IOException {

	ServerSocket WarehouseServerSocket = null;
    boolean listening = true;
    String WarehouseServerName = "Warehouse";
    int WarehouseServerNumber = 4545;


    //Create the shared object in the global scope...
    
    FileServerState Server_StateObject = new FileServerState();
        
    // Make the server socket

    try {
      WarehouseServerSocket = new ServerSocket(WarehouseServerNumber);
    } catch (IOException e) {
      System.err.println("Could not start " + WarehouseServerName + " specified port.");
      System.exit(-1);
    }
    System.out.println(WarehouseServerName + " started");

    
    while (listening){
      new ServerThread(WarehouseServerSocket.accept(), "WarehouseServerThreadCustomerA", Server_StateObject).start();
      new ServerThread(WarehouseServerSocket.accept(), "WarehouseServerThreadCustomerB", Server_StateObject).start();
      new ServerThread(WarehouseServerSocket.accept(), "WarehouseServerThreadSupplier", Server_StateObject).start();
      System.out.println("New " + WarehouseServerName + " thread started.");
    }
    WarehouseServerSocket.close();
  	}
}
