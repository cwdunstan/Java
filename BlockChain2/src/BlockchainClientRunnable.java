import java.net.Socket;
import java.util.Scanner;
import java.io.*;


public class BlockchainClientRunnable implements Runnable {

    private String reply;
;

    public BlockchainClientRunnable(int serverNumber, String serverName, int portNumber, String message) {
        this.reply = "Server" + serverNumber + ": " + serverName + " " + portNumber +"\n"; // header string

        
    }

    public void run() {
        // implement your code here
    	
    	System.out.print(reply);
    	/*try {
    		Socket mySocket = new Socket(this.serverName,this.portNumber);
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        	PrintWriter outWriter = new PrintWriter(mySocket.getOutputStream(), true);
        	
        	Scanner sc = new Scanner(System.in);
            String received = null;
            while (sc.hasNextLine()) {
            String command = sc.nextLine();
            	if(!command.matches("cc")) {
            		outWriter.println(command);
            	}

    	}
    	}
	    catch(IOException e){
	            	
	    }*/
    }
    
    public String getReply() {
        return reply;
    }

    // implement any helper method here if you need any
}