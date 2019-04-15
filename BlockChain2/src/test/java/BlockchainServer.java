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
        
        
        PeriodicCommitRunnable pcr = new PeriodicCommitRunnable(blockchain);
        Thread pct = new Thread(pcr);
        ServerSocket myServerSocket = null;
        try{
        	pct.start();
        	myServerSocket = new ServerSocket(portNumber);
        	Socket mySocket = null;
        	BlockchainServerRunnable bcs = null;
        	while(pcr.getRunning()){
        		mySocket = myServerSocket.accept();
        		BlockchainServerRunnable bsr = new BlockchainServerRunnable(mySocket,blockchain);
        		 Thread bsrt = new Thread(bsr);
        		 bsrt.start();
        	}
        	pcr.setRunning(false);
        	pct.join();
        }
        catch(IOException e){
        	
        }
        catch(InterruptedException e){
        	
        }
     


    }

    // implement any helper method here if you need any
}
