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
        	try{
            String message = sc.nextLine();
            // implement your code here
            if(message.matches("ls")){
                System.out.print(pl.toString());
                System.out.println();
            } 
            else if(message.startsWith("ad")){
            	//add new server
        		String[] infos = message.split("\\|");
        		if(infos.length<3){
        			System.out.println("Failed\n");
        		}
        		else{
        			if(infos[2].length()==4){
        				ServerInfo tempServ = new ServerInfo(infos[1],Integer.parseInt(infos[2]));
        				if(pl.addServerInfo(tempServ)){
        					System.out.println("Succeeded\n");
        				}else{
        					System.out.println("Failed\n");
        				}
        				
        			}else{
        				System.out.println("Failed\n");
        			}
        		}        		
        		
        	} else if(message.startsWith("rm")){
 
        			String[] infos = message.split("\\|");
        			if(infos.length<2){
        				System.out.println("Failed\n");
        			}
        			else{
        				int tempVal = Integer.parseInt(infos[1]);
        				if(pl.getSize()>=tempVal && tempVal>=0){
        					if(pl.getServerInfos().get(tempVal)!=null){
        						if(pl.removeServerInfo(tempVal));
        						System.out.println("Succeeded\n");
        					}
        				}
        				else{
        					System.out.println("Failed\n");
        				}
        			}
        		//remove server index
        		
        	} else if(message.startsWith("up")){
        		//update the server indwxed, given its valid
        		String[] infos = message.split("\\|");
        		if(infos.length!=4){
        			System.out.println("Failed\n");
        		}
        		else{
        			int tempInd = Integer.parseInt(infos[1]);
	        		if(infos[3].length()==4){
	        			ServerInfo tempServ = new ServerInfo(infos[2],Integer.parseInt(infos[3]));
	        			if(pl.updateServerInfo(tempInd,tempServ)){
	        				System.out.println("Succeeded\n");
	        			}else{
	        				System.out.println("Failed\n");
	        			}	
	        		}else{
	        			System.out.println("Failed\n");
        			}
        		} 
        		
        	} else if(message.startsWith("tx")){
        		//BROADCAST THE TRANsATION TO EACH MESSAGE AND PRINT REPLYS
        		String[] infos = message.split("\\|");
        		if(infos.length!=3){
        			System.out.println("Failed\n");
        		}
        		else{
        			broadcast(pl,message);
        		}
        		        		
        	} else if(message.matches("cl")){
        		for(int i=0;i<pl.getSize();i++){
        			if(pl.getServerInfos().get(i)==null){
        				pl.getServerInfos().remove(i);
        			}
        		}
        		System.out.println("Succeeded\n");
        		//remove all null values
        		
        	} else if(message.startsWith("pb")){
        		//BROADCAST THE PB TO EACH THREAD AND PRINT REPLYS (IF NO SERVER INDEX)
        		//UNICAST TO SPECIFIC SERVER
        		//MULTICAST TO SPECIFIED SRVER IF MORE THANONE AT GIVEN INDEX
        		if(message.matches("pb")){
        			broadcast(pl,"pb");
        		}else{
        			String[] infos = message.split("\\|");
        			ArrayList<Integer> store = new ArrayList<>();
        			for(int i=1;i<infos.length;i++){
        				store.add(Integer.parseInt(infos[i]));
        			}
        			if(store.size()==1){
        				unicast(store.get(0),pl.getServerInfos().get(store.get(0)),"pb");
        			}
        			else{
        				multicast(pl,store,"pb");
        			}
        		}
        		
        	} else if(message.startsWith("sd")){
        		//SHUTDOWN SERVER 
        		return;
        		
        	}else{
        		System.out.println("Unknown Command\n");
        	}
        
        } catch(NoSuchElementException e){
        	return;
        
        } catch(IndexOutOfBoundsException e){
        	e.printStackTrace();
        	return;
        }
    }
    }
    
 
    public static void unicast (int serverNumber, ServerInfo p, String message) {
        // implement your code here
    	//for pl.gets(servernumber)
    	if(p!=null){
			BlockchainClientRunnable bcr = new BlockchainClientRunnable(serverNumber,p.getHost(),p.getPort(),message);
	 			Thread bcrt = new Thread(bcr);
	 			try{
	 				bcrt.start();
	 				bcrt.join();
	 			} catch(InterruptedException e){
	 				
	 			}
		}
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
   		 			try{
   		 				bcrt.start();
   		 				bcrt.join();
   		 			} catch(InterruptedException e){
   		 				
   		 			}
        		}
        	}
    }
 
    public static void multicast (ServerInfoList serverInfoList, ArrayList<Integer> serverIndices, String message) {
        // implement your code here
    	//for each server in indices and list
        ArrayList<ServerInfo> serverIn = serverInfoList.getServerInfos();
        for(int i :  serverIndices){
        		if(serverIn.get(i)!=null){
        			BlockchainClientRunnable bcr = new BlockchainClientRunnable(i,serverIn.get(i).getHost(),serverIn.get(i).getPort(),message);
   		 			Thread bcrt = new Thread(bcr);
   		 			try{
   		 				bcrt.start();
   		 				bcrt.join();
   		 			} catch(InterruptedException e){
   		 				
   		 			}
        	}
        }
    }
    // implement any helper method here if you need any
    public boolean isValid (String in){
    
    	return true;
    }
}