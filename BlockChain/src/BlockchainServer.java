import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

 
public class BlockchainServer {
    private Blockchain blockchain;
 
    public BlockchainServer() { blockchain = new Blockchain(); }
 
    // getters and setters
    public void setBlockchain(Blockchain blockchain) { this.blockchain = blockchain; }
    public Blockchain getBlockchain() { return blockchain; }
 
    public static void main(String[] args){
        if (args.length != 1) {
            return;
        }
        int portNumber = Integer.parseInt(args[0]);
        BlockchainServer bcs = new BlockchainServer();
        
        // TODO: implement your code here.
        
        //Declare and Initialize server
        ServerSocket myServerSocket = null;
        try {
        	myServerSocket = new ServerSocket(portNumber);
        	Socket mySocket = null;
         	while(true) {
        		mySocket = myServerSocket.accept();
        		bcs.serverHandler(mySocket.getInputStream(),mySocket.getOutputStream());
        		mySocket.close();
			}
        }
	    catch (IOException e) {
	    	return;
	    } 
        
    }
 
    public void serverHandler(InputStream clientInputStream, OutputStream clientOutputStream) {
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(clientInputStream));
        PrintWriter outWriter = new PrintWriter(clientOutputStream, true);
 
        // TODO: implement your code here.
			    while(true) {
			   String input = null;
		        	try {
						while((input = inputReader.readLine())!=null) {
							//adding transaction
							if(input.startsWith("tx")) {
								int res = blockchain.addTransaction(input);
								if(res == 1 || res == 2) {
									outWriter.print("Accepted\n\n");
									outWriter.flush();
								}else {
									outWriter.print("Rejected\n\n");
									outWriter.flush();
								}
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
}
    
 
    // implement helper functions here if you need any.