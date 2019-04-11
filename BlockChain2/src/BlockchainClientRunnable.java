import java.net.Socket;
import java.util.Scanner;
import java.io.*;


public class BlockchainClientRunnable implements Runnable {

    private String reply;
    private int serverNumber;
    private String serverName;
    private int portNumber;
    private String message;

    public BlockchainClientRunnable(int serverNumber, String serverName, int portNumber, String message) {
        this.reply = "Server" + serverNumber + ": " + serverName + " " + portNumber +"\n"; // header string
        this.serverNumber = serverNumber;
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.message = message;
        
    }

    public void run() {
        // implement your code here
    	
    	try {
    		//print start of thread
    		//CREATE A CONNECTION
    		Socket mySocket = new Socket(this.serverName,this.portNumber);
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        	PrintWriter outWriter = new PrintWriter(mySocket.getOutputStream(), true);
        	
        	//FORWARD MESSAGE VIA SOCKET TO SERVER
        	outWriter.println(message);
        	//STORE REPLY FROM SERVER IN REPLY
        	 String input = null;
        	 String temp = null;
        	while((input = inputReader.readLine())!=null) {
        		reply += (input+"\n");
        		if(input.equals("")){
        			System.out.println(reply);
        			outWriter.println("cc");
        			return;
        		}
        	}

        	//CLOSE CONNECTION BY SENDING CC
    	}
	    catch(IOException e){

	    }
    }
    
    public String getReply() {
        return reply;
    }

    // implement any helper method here if you need any
}