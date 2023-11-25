package com;
import java.net.*;
import java.io.*;
public class ServerThread extends Thread {

	
  private Socket fileSocket = null;
  private FileServerState ServerStateObject;
  private String fileServerThreadName;
   
  //Setup the thread
  	public ServerThread(Socket fileSocket, String FileServerThreadName, FileServerState StateObject) {
	
//	  super(FileServerThreadName);
	  this.fileSocket = fileSocket;
	  ServerStateObject = StateObject;
	  fileServerThreadName = FileServerThreadName;
	}

  public void run() {
    try {
      System.out.println(fileServerThreadName + "initialising.");
      PrintWriter out = new PrintWriter(fileSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(fileSocket.getInputStream()));
      String inputLine, outputLine;

      while ((inputLine = in.readLine()) != null) {
    	  // Get a lock first
    	  try { 
    		  ServerStateObject.acquireLock();  
    		  outputLine = ServerStateObject.processInput(fileServerThreadName, inputLine);
    		  out.println(outputLine);
    		  ServerStateObject.releaseLock();  
    	  } 
    	  catch(InterruptedException e) {
    		  System.err.println("Failed to get lock when reading:"+e);
    	  }
      }

       out.close();
       in.close();
       fileSocket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
