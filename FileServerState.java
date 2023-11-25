package com;

public class FileServerState{
	
	private String myThreadName;
	private double mySharedVariable;
	private boolean accessing=false; // true a thread has a lock, false otherwise
	private int threadsWaiting=0; // number of waiting writers
	private int apples = 1000;
	private int oranges = 1000;

// Constructor	
	
	FileServerState() {
		
	}
//Attempt to aquire a lock

	public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting;
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0
		      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true;
		    System.out.println(me.getName()+" got a lock!"); 
		  }

		  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      System.out.println(me.getName()+" released a lock!");
		  }
	
	
    /* The processInput method */

	public synchronized String processInput(String myThreadName, String theInput) {
    		System.out.println(myThreadName + " received "+ theInput);
    		String theOutput = null;
    		String[] split = theInput.split(" ");
    		// Check what the client said
    		/** Check Stock */
    		if (split.length == 2 && split[0].equals("Check") && split[1].equals("Stock")) {
    			//Correct request
    			if (myThreadName.equals("WarehouseServerThreadCustomerA") || myThreadName.equals("WarehouseServerThreadCustomerB") || myThreadName.equals("WarehouseServerThreadSupplier")) {
    				theOutput = ("There are " + apples + " apples left and there are " + oranges + " oranges left");
    				}
       			else {System.out.println("Write Check Stock");}
    		}
    		/** Customer Buy Apples */
    		else if (split.length == 3) {
    			if(split[0].equals("Buy") && split[1].equals("Apples") && split[2].matches("[0-9]+")) {
    				if (myThreadName.equals("WarehouseServerThreadCustomerA") || myThreadName.equals("WarehouseServerThreadCustomerB")){
        				int number = Integer.parseInt(split[2]);
        				if(number < apples) {
        					apples -= Integer.valueOf(number);
                			theOutput = ("You bought " + number + " apples and there are " + apples + " left");
        				}
        				else {
        					theOutput = ("There is not enough apples for you to buy, We only have " + apples + " left");
        				}
    				}
    			}
    			/** Customer Buy Oranges */
    			else if(split[0].equals("Buy") && split[1].equals("Oranges") && split[2].matches("[0-9]+")) {
    				if (myThreadName.equals("WarehouseServerThreadCustomerA") || myThreadName.equals("WarehouseServerThreadCustomerB")){
        				int number = Integer.parseInt(split[2]);
        				if(number < oranges) {
        					oranges -= Integer.valueOf(number);
                			theOutput = ("You bought " + number + " oranges and there are " + oranges + " left");
        				}
        				else {
        					theOutput = ("There is not enough oranges for you to buy, We only have " + oranges + " left");
        				}
    				}
    			}
    			/** Supplier Add Apples */
	    		else if(split[0].equals("Add") && split[1].equals("Apples") && split[2].matches("[0-9]+")) {
	    				if (myThreadName.equals("WarehouseServerThreadSupplier")){
	        				int number = Integer.parseInt(split[2]);
	        				apples += Integer.valueOf(number);
	        				theOutput = ("You added " + number + " apples and there are " + apples + " left");    				
	        			}
	    				else {
	    					theOutput = ("You should type a number after Add Apples");
	    				}
	    			}
    			/** Supplier Add Oranges */
	    		else if(split[0].equals("Add") && split[1].equals("Oranges") && split[2].matches("[0-9]+")) {
	    				if (myThreadName.equals("WarehouseServerThreadSupplier")){
	        				int number = Integer.parseInt(split[2]);
	        				oranges += Integer.valueOf(number);
	                		theOutput = ("You added " + number + " oranges and there are " + oranges + " left");
	            		}
	    				else {
	        					theOutput = ("You should type a number after Add Oranges");
	        				}
	    				}
    				}
    		else { //incorrect request
    			theOutput = myThreadName + " received incorrect request - only understand \"Check Stock, Buy Apples or Buy Oranges or Add Oranges or Add Apples with a number\"";
    			}	
    		//Return the output message to the Server
        	return theOutput;
	}
}
    		
    				
    				

	
    	
    	


