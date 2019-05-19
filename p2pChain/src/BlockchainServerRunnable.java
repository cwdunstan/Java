import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
 
public class BlockchainServerRunnable implements Runnable{
 
    private Socket clientSocket;
    private Blockchain blockchain;
    private Map<ServerInfo, Date> serverStatus;
 
    public BlockchainServerRunnable(Socket clientSocket, Blockchain blockchain, Map<ServerInfo, Date> serverStatus2) {
        this.clientSocket = clientSocket;
        this.blockchain = blockchain;
        this.serverStatus = serverStatus2;
    }
 
    public void run() {
        try {
            serverHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    public void serverHandler(InputStream clientInputStream, OutputStream clientOutputStream) throws InterruptedException, ClassNotFoundException {
 
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(clientInputStream));
        PrintWriter outWriter = new PrintWriter(clientOutputStream, true);

        
        String localIP = (((InetSocketAddress) clientSocket.getLocalSocketAddress()).getAddress()).toString().replace("/", "");
        String remoteIP = (((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
 
        try {
            while (true) {
                String inputLine = inputReader.readLine();
                if (inputLine == null) {
                    break;
                }
                System.out.println(inputLine);

                String[] tokens = inputLine.split("\\|");
                switch (tokens[0]) {
                    case "tx":
                        if (blockchain.addTransaction(inputLine))
                            outWriter.print("Accepted\n\n");
                        else
                            outWriter.print("Rejected\n\n");
                        outWriter.flush();
                        break;
                    case "pb":
                        outWriter.print(blockchain.toString()+"\n");
                        outWriter.flush();
                        break;
                    //HEART BEAT
                    case "hb":
                            // create temp serverInfo
                    		ServerInfo temps = new ServerInfo(remoteIP, Integer.parseInt(tokens[1]));
                    		//if this is a new HB, broadcast SI
                    		if(!serverStatus.containsKey(temps)){
                              ArrayList<Thread> threadArrayList = new ArrayList<>();
                              for (ServerInfo serv : serverStatus.keySet()) {
                            	  if(serv.getPort()!=Integer.parseInt(tokens[1])){
                                  Thread thread = new Thread(new HeartBeatClientRunnable(serv.getHost(),serv.getPort(), "si|"+clientSocket.getLocalPort()+"|"+serv.getHost()+"|"+temps.getPort()));
                                  threadArrayList.add(thread);
                                  thread.start();
                            	  }
                              }
                              for (Thread thread : threadArrayList) {
                                  thread.join();
                              }
                    		}
                    		//update values
                    		serverStatus.put(temps, new Date());
                        break;
                   //SERVER INFO
                    case "si":
                    	ServerInfo temp = new ServerInfo(tokens[2], Integer.parseInt(tokens[3]));
                		if(!serverStatus.containsKey(temp)){
                            ArrayList<Thread> threadArrayList = new ArrayList<>();
                            for (ServerInfo serv : serverStatus.keySet()) {
                            	if(serv.getPort()!=Integer.parseInt(tokens[1])){
                                Thread thread = new Thread(new HeartBeatClientRunnable(serv.getHost(),serv.getPort(), "si|"+clientSocket.getLocalPort()+"|"+serv.getHost()+"|"+temp.getPort()));
                                threadArrayList.add(thread);
                                thread.start();
                            	}
                            }
                            for (Thread thread : threadArrayList) {
                                thread.join();
                            }
                  		}
                  		//update values
                  		serverStatus.put(temp, new Date());        				
                        break;
                    
                        
                        
                    //LAST BLOCK
                    case "lb":
                        //if my length is less than theirs
                        if(Integer.parseInt(tokens[2])> blockchain.getLength()){
                    		//create a socket
                    		System.out.println("Client Conencting to IP: "+remoteIP+" PORT: "+tokens[1]);
                        	Thread readBlock = new Thread(new readBlockRun(remoteIP,Integer.parseInt(tokens[1]),blockchain,Integer.parseInt(tokens[2])));
                            readBlock.start();
                        }       	
                        
                    	break;
                    case "cu":
                    	//establish socket from Q to P, buffer reader & object output
                		//toServer.connect(new InetSocketAddress(remoteIP, clientSocket.getLocalPort()), 2000);
                		System.out.println("SERVER Conencting to IP: "+remoteIP+" PORT: "+clientSocket.getLocalPort());
                     	


                	        
                    	break;
                    case "cc":
                        return;
                    default:
                        outWriter.print("Error\n\n");
                        outWriter.flush();
                }
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static String getIP(String host){
		try {
			InetAddress inetAddr = InetAddress.getByName(host);
	        byte[] rawadd = inetAddr.getAddress();
	        String ip = "";
	        for (int i=0;i < rawadd.length; i++) {
	            if(i>0){
	                ip+=".";
	            }
	            ip += rawadd[i] & 0xFF;
	        }
	        return ip;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    //returns 0 if equal,  1 if hash1 > hash 2, 2 if hash 1 < than 2
    public int compareHash(String hash1, String hash2){
    	byte[] h1 =  hash1.getBytes();
    	byte[] h2 =  hash2.getBytes();
    	if(h1.length==h2.length){
    		for(int i=0;i<h1.length;i++){
    			if(h1[i]>h2[i]){
    				return 1;
    			}else if(h2[i]>h1[i]){
    				return 2;
    			}
    		}
    		return 0;
    	}
    	return 0;
    }
}


//Blockchain tempchain = new Blockchain();
//Block temphead = new Block();                       
//temphead = blockchain.getHead();
//tempchain.setHead(temphead);
//tempchain.setLength(tempchain.getLength()+1);
////iterate through
//Block t = blockchain.getHead();
//Block g = tempchain.getHead();
//
//while(t.getPreviousBlock()!=null){
//	if(Base64.getEncoder().encodeToString(t.getPreviousHash()).equals(tokens[1])){
//		break;
//	}else{
//		Block f = new Block();
//		f = t.getPreviousBlock();
//		g.setPreviousBlock(f);
//		g=g.getPreviousBlock();
//		t=t.getPreviousBlock();
//		tempchain.setLength(tempchain.getLength()+1);
//	}
//}