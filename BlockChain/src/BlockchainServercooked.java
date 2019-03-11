/*import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

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
        
        //Declare and Initialize server
        ServerSocket myServerSocket = null;
        try {
        	myServerSocket = new ServerSocket(portNumber);

			//Accept Connections
        	while(true) {
     
        		Socket mySocket = myServerSocket.accept();
        		bcs.serverHandler(mySocket.getInputStream(),mySocket.getOutputStream());	
        		mySocket.getOutputStream().close();
        		mySocket.close();
        		
				
			}
        }
	    catch (IOException e) {
	    	System.out.print(e);
	    }
       }

        //Catch Server error


        
 

    public void serverHandler(InputStream clientInputStream, OutputStream clientOutputStream) {
    	
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(clientInputStream));
        PrintWriter outWriter = new PrintWriter(clientOutputStream, true);
        
        class manage implements Runnable{
        	
			public void run() {
				// TODO Auto-generated method stub
				String input =null;
		        while(true) {
		        	try {
						input = inputReader.readLine();
						if(input==null) {
							break;
						}
			        	if(input.length()>1) {
							if(input.substring(0,2).matches("tx")) {
								int res = blockchain.addTransaction(input);
								if(res == 1 || res == 2) {
									outWriter.print("Accepted\n");
									outWriter.flush();
								}else {
									outWriter.print("Rejected\n");
									outWriter.flush();
								}
							}
							else if(input.matches("pb")) {
								outWriter.print(blockchain.toString()+"\n");
								outWriter.flush();
							}

							else if(input.matches("cc")) {
								outWriter.flush();
								break;
							}
							else {
								outWriter.print("Error\n\n");
								outWriter.flush();
							}
						}
						else {
							outWriter.print("Error\n\n");
							outWriter.flush();
						}
					}
			        	
				 catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        }
			}
        	
			  
        }
        // TODO: implement your code here.
        Thread t = new Thread(new manage());
	    t.start();
        
    }
    
}*/