import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;

public class BlockchainClient {

    public static void main(String[] args) {

        if (args.length != 1) {
            return;
        }
        String configFileName = args[0];

        ServerInfoList pl = new ServerInfoList();
        pl.initialiseFromFile(configFileName);
       
        Scanner sc = new Scanner(System.in);
        while (true) {
            String message = sc.nextLine();
            // implement your code here
            if(message.matches("ls")){
                System.out.print(pl.toString());
            } else if(message.startsWith("ad")){
            	//add new server
        		
        	} else if(message.startsWith("rm")){
        		//remove server index
        		
        	} else if(message.startsWith("up")){
        		//update the server indwxed, given its valid
        		
        	} else if(message.startsWith("tx")){
        		//BROADCAST THE TRANsATION TO EACH MESSAGE AND PRINT REPLYS
        		
        	} else if(message.startsWith("cl")){
        		for(int i=0;i<pl.getSize();i++){
        			if(pl.getServerInfos().get(i)==null){
        				pl.removeServerInfo(i);
        			}
        		}
        		//remove all null values
        		
        	} else if(message.startsWith("pb")){
        		//BROADCAST THE PB TO EACH THREAD AND PRINT REPLYS (IF NO SERVER INDEX)
        		//UNICAST TO SPECIFIC SERVER
        		//MULTICAST TO SPECIFIED SRVER IF MORE THANONE AT GIVEN INDEX
        		broadcast(pl,"pb");
        		
        	} else if(message.startsWith("sd")){
        		//SHUTDOWN SERVER 
        		
        	}
        }
    }

    public void unicast (int serverNumber, ServerInfo p, String message) {
        // implement your code here
    	//for pl.gets(servernumber)
    }

    public static void broadcast (ServerInfoList pl, String message) {
        // implement your code here
    	//for each server in pl
        // start threads for each server
        ArrayList<ServerInfo> serverIn = pl.getServerInfos();
        for(int i=0;i<pl.getSize();i++){
        		if(serverIn.get(i)!=null){
        			BlockchainClientRunnable bcr = new BlockchainClientRunnable(i,serverIn.get(i).getHost(),serverIn.get(i).getPort(),message);
   		 			Thread bcrt = new Thread(bcr);
   		 			bcrt.start();
        		}
        	}
    }

    public void multicast (ServerInfoList serverInfoList, ArrayList<Integer> serverIndices, String message) {
        // implement your code here
    	//for each server in indices and list
    }

    // implement any helper method here if you need any
    public boolean isValid (ServerInfo in){
    
    	return true;
    }
}