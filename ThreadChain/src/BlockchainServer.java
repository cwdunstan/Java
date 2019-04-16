import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
public class BlockchainServer {
	private static Blockchain bcs;

	public static void main(String[] args) {
 
        if (args.length != 1) {
            return;
        }
        int portNumber=0;
        try{
        	 portNumber = Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
        	return;
        }
        Blockchain blockchain = new Blockchain();
        
        
        PeriodicCommitRunnable pcr = new PeriodicCommitRunnable(blockchain);
        Thread pct = new Thread(pcr);
        pct.start();
        
        ServerSocket myServerSocket = null;
        while(true){
        	try{
        		if(portNumber>1023 && portNumber<65536){
        		myServerSocket = new ServerSocket(portNumber);
        		while(pcr.getRunning()){
        			Socket mySocket = myServerSocket.accept();
        			mySocket.setSoTimeout(1000);
        			BlockchainServerRunnable bsr = new BlockchainServerRunnable(mySocket,blockchain);
        			Thread bsrt = new Thread(bsr);
        			bsrt.start();
        		}
        		}else{
        			break;
        		}
        	}
        	      
        catch(IOException e){
        	System.out.println("Port unavaliable.");
        	return;
        } 


        }
 
    }

	public void setBlockchain(Blockchain blockchain) {
		// TODO Auto-generated method stub
		bcs=blockchain;
	}

	public void serverHandler(ByteArrayInputStream clientInputStream, ByteArrayOutputStream clientOutputStream) {
		// TODO Auto-generated method stub
		
	    	 BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientInputStream));
	         PrintWriter outWriter = new PrintWriter(clientOutputStream, true);
			    while(true) {
					   String input = null;
			        	try {
							while((input = inputReader.readLine())!=null) {
								//adding transaction
								if(input.startsWith("tx")) {
									boolean res = bcs.addTransaction(input);
									if(res) {
										outWriter.print("Accepted\n\n");
										outWriter.flush();
									}else {
										outWriter.print("Rejected\n\n");
										outWriter.flush();
									}
								}
								//Print blockchain
								else if(input.matches("pb")) {
									outWriter.print(bcs.toString()+"\n");
									outWriter.flush();
								}
								//Close connection
								else if(input.matches("cc")) {
									outWriter.flush();
									outWriter.close();
									return;
								}
								else {
									outWriter.print("Unknown Command\n\n");
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
 
    // implement any helper method here if you need any
}