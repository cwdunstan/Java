import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BlockchainServerRunnable implements Runnable{

    private Socket clientSocket;
    private Blockchain blockchain;

    public BlockchainServerRunnable(Socket clientSocket, Blockchain blockchain) {
        // implement your code here
    	this.clientSocket = clientSocket;
    	this.blockchain = blockchain;
    }

    public void run() {
        // implement your code here
    	try{
    	 BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream(), true);
         System.out.println("Server thread Started");
		    while(true) {
		    	
		   String input = null;
	        	try {
					while((input = inputReader.readLine())!=null) {
						//adding transaction
						if(input.startsWith("tx")) {
							boolean res = blockchain.addTransaction(input);
							if(res) {
								outWriter.print("Accepted\n\n");
								outWriter.flush();
							}else {
								outWriter.print("Rejected\n\n");
								outWriter.flush();
							}
						}
						//Print blockchain
						else if(input.matches("ls")) {
							outWriter.print(blockchain.toString()+"\n");
							outWriter.flush();
						}
						//Print blockchain
						else if(input.matches("pb")) {
							outWriter.print(blockchain.toString()+"\n");
							outWriter.flush();
						}
						//Close connection
						else if(input.matches("cc")) {
							outWriter.flush();
							outWriter.close();
			
							break;
						}
						else {
							outWriter.print("Error\n\n");
							System.out.print(input);
							outWriter.flush();
					}
					}
				//input null
					outWriter.flush();
					outWriter.close();
					break;	
					
	        	}
	        	catch(IOException e) {
	        		outWriter.flush();
					outWriter.close();
	        		break;
	        	}
		    }
    }
    	catch(IOException e) {

    		return;
    	}
    	
    }
    // implement any helper method here if you need any
}
