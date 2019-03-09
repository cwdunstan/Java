import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockchainServer {
    private Blockchain blockchain;

    public BlockchainServer() { blockchain = new Blockchain(); }

    // getters and setters
    public void setBlockchain(Blockchain blockchain) { this.blockchain = blockchain; }
    public Blockchain getBlockchain() { return blockchain; }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
       
        int portNumber = Integer.parseInt(args[0]);
        BlockchainServer bcs = new BlockchainServer();

        // TODO: implement your code here.
        try {
        	//create socket listening to user provided port
			ServerSocket newServerSocket = new ServerSocket(portNumber);
			
			//accept connection
			Socket mySocket = newServerSocket.accept();
			//pass input & output to serverHandler
            System.out.println("Connection Accepted.");
			bcs.serverHandler(mySocket.getInputStream(),mySocket.getOutputStream());

        } 
        catch (IOException e) {
			e.printStackTrace();
		}
        
        
    }

    public void serverHandler(InputStream clientInputStream, OutputStream clientOutputStream) {

        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(clientInputStream));
        PrintWriter outWriter = new PrintWriter(clientOutputStream, true);

        // TODO: implement your code here.
        String userIn = null;
        while(true){
        	try{
        		userIn = inputReader.readLine();
        		if(userIn.equalsIgnoreCase("exit")){
        			break;
        			}
        		else{
        			if(userIn.matches("tx\\|")){
        				
        				}
        			else if(userIn.matches("tx\\|")){

        				}
        			}
        		}
        	catch(IOException e){

        	}
        }
    }

    // implement helper functions here if you need any.
}