import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
 
public class BlockchainServer {
 
    public static void main(String[] args) {
 
        if (args.length != 1) {
            return;
        }
 
        int portNumber = Integer.parseInt(args[0]);
        Blockchain blockchain = new Blockchain();
        
     
        //INITIALIZEW SERVER SOCKET
        ServerSocket myServerSocket = null;
        try{
        	myServerSocket = new ServerSocket(portNumber);
        }    
       catch(IOException e){
        	System.out.println("couldn't create socket.");
        	System.exit(-1);
        }
        
        //SET UP OTHER STUFF
        PeriodicCommitRunnable pcr = new PeriodicCommitRunnable(blockchain);
        Thread pct = new Thread(pcr);
        pct.start();
        
        while(pcr.getRunning()){
        	try{
        		Socket mySocket = myServerSocket.accept();
        		BlockchainServerRunnable bcr = new BlockchainServerRunnable(mySocket, blockchain);
        		Thread myBcr = new Thread(bcr);
        		myBcr.start();
        	} 
        	catch(IOException e){
        		System.out.println("error1");
        	}
        	
        	try{
        		myServerSocket.close();
        		pcr.setRunning(false);
        		System.out.println("server stopped.");
        		return;
        	} catch(Exception er){
        		System.out.println("error closing server");
        		System.exit(-1);
        	}
        }      
    }
}
    // implement any helper method here if you need any
