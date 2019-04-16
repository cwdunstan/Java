import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;
 
public class BlockchainClient {
 
	private static ServerInfoList pl;
    public static void main(String[] args) {
 
        if (args.length != 1) {
            return;
        }
        String configFileName = args[0];
 
        pl = new ServerInfoList();
        pl.initialiseFromFile(configFileName);
       
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String message = sc.nextLine();
            // INPUT IS LIST
            if(message.matches("ls")){
                System.out.println(pl.toString());
            } 
            //ADD A NEW SERVER
            else if(message.startsWith("ad")){
            	//add new server
        		String[] infos = message.split("\\|");
        		//handles addd
        		if(!infos[0].equals("ad")){
        			System.out.println("Unknown Command\n\n");
        		}
        		//handles invalid parameters
        		if(infos.length!=3){
        			System.out.println("Failed\n");
        		}
        		else{
        			if(infos[2].length()==4){
        				int tempPort=0;
        				//make sure port is valid
        				try{
        					tempPort = Integer.parseInt(infos[2]);
        				} catch (NumberFormatException n){

        				}
        				if(tempPort!=0){		
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
        	}
        } 
        //REMOVE A SERVER  ****TESTED******
        else if(message.startsWith("rm")){
        	String[] infos = message.split("\\|");
        	//Check Valid Format
    		if(!infos[0].equals("rm")){
    			System.out.println("Unknown Command\n");
    		}
    		else if(infos.length!=2){	
        		System.out.println("Failed\n");
        	}
        	else{
        		//Check valid server index format
        		int tempVal =-1;
        		try{
					tempVal = Integer.parseInt(infos[1]);
				} catch (NumberFormatException n){

				}
        		//check if server is within pl size
				if(tempVal!=-1){
        			if(pl.getServerInfos().size()>tempVal && tempVal>=0){
        				if(pl.getServerInfos().get(tempVal)!=null){
        						if(pl.removeServerInfo(tempVal)){
        							System.out.println("Succeeded\n");
        						}else{
                					System.out.println("Failed\n");
                				}
        					}else{
            					System.out.println("Failed\n");
            				}
        				}
        				else{
        					System.out.println("Failed\n");
        				}
        			}
					else{
    					System.out.println("Failed\n");
    				}
        		}
        	
        	//UPDATE VALUE ***TESTED****
        	} else if(message.startsWith("up")){
        		String[] infos = message.split("\\|");
        		//check actually updating
        		if(!infos[0].equals("up")){
        			System.out.println("Unknown Command\n");
        		}
        		//check valid length
        		else if(infos.length!=4){
        			System.out.println("Failed\n");
        		}
        		else{
            		//Check valid server index format
            		int tempVal =-1;
            		int tempPort =0;
            		try{
    					tempVal = Integer.parseInt(infos[1]);
    					tempPort = Integer.parseInt(infos[3]);
    				} catch (NumberFormatException n){

    				}
            		//All the validity checks!
            		if(tempVal!=-1 && tempPort!=0 && tempPort>1023 && tempPort < 65536 && pl.getServerInfos().size()>tempVal && tempVal>=0){
		        			ServerInfo tempServ = new ServerInfo(infos[2],tempPort);
		        			if(pl.updateServerInfo(tempVal,tempServ)){
		        				System.out.println("Succeeded\n");
		        			}else{
		        				System.out.println("Failed\n");
		        			}	
		        	}else{
		        			System.out.println("Failed\n");
	        		}
            	}
        	
        	//ADD TRANSACTION
        	} else if(message.startsWith("tx")){    		
        		String[] infos = message.split("\\|");
        		//check actually updating
        		if(!infos[0].equals("tx")){
        			System.out.println("Unknown Command\n");
        		}
        		//check valid length
        		else if(infos.length!=3){
        			System.out.println("Failed\n");
        		}
        		//broadcast
        		else{
        			broadcast(pl,message);
        		}       		        		
        	} 
        	//CLEAR NULL VALUES
        	else if(message.matches("cl")){
        		for(int i=0;i<pl.getSize()-1;i++){
        			if(pl.getServerInfos().get(i)==null){
        				pl.getServerInfos().remove(i);
        			}
        		}
        		System.out.println("Succeeded\n");
        		//remove all null values
        		
        	} 
        	
        	//PRINT BLOCKCHAIN
        	else if(message.startsWith("pb")){
        		//BROADCAST THE PB TO EACH THREAD AND PRINT REPLYS (IF NO SERVER INDEX), UNICAST TO SPECIFIC SERVER
        		//MULTICAST TO SPECIFIED SRVER IF MORE THANONE AT GIVEN INDEX
        		
        		//Validity Check
        		String[] infos = message.split("\\|");
        		if(!infos[0].equals("pb")){
        			System.out.println("Unknown Command\n");
        		}
        		else{
        			//no index
        			if(infos.length==1){
        				broadcast(pl,"pb");
        			}
        			//Indexes provided
        			else{
        				//iterate, starting at 1  (remove pb)
        				ArrayList<Integer> store = new ArrayList<>();
        				for(int i=1;i<infos.length;i++){
                    		try{
            					store.add(Integer.parseInt(infos[i]));
            				} catch (NumberFormatException n){
            					
            				}
        				}
        				//1 server index
        				if(store.size()==1 && store.get(0)>-1 && store.get(0)<pl.getServerInfos().size()){
        					unicast(store.get(0),pl.getServerInfos().get(store.get(0)),"pb");
        				}
        				//more than 1 server index
        				else if(store.size()>1){
        					multicast(pl,store,"pb");
        				}
        				
        			}
        		}
        		        	           		
        	} 
            //SHUTDOWN SERVER 
        	else if(message.startsWith("sd")){
        		//close all servers, then returns
        		
        		
        		return;
        		
        	}
        	
        	else{
        		System.out.println("Unknown Command\n");
        	}
        
        } 
    }
    
    
 
    public static void unicast (int serverNumber, ServerInfo p, String message) {
        // start threads for each server
    	if(p!=null){
    		BlockchainClientRunnable bcr = new BlockchainClientRunnable(serverNumber,p.getHost(),p.getPort(),message);
    	
    		ExecutorService executor = Executors.newFixedThreadPool(1);
    			executor.submit(bcr);
        
    			try{
    				executor.awaitTermination(600, TimeUnit.MILLISECONDS);
    			}catch(InterruptedException e){
        	
    			}
    			System.out.print(bcr.getReply());
    	}
    }
 
    public static void broadcast (ServerInfoList pl, String message) {
        // start threads for each server
        ArrayList<ServerInfo> serverIn = pl.getServerInfos();
        ArrayList<BlockchainClientRunnable> cleverGirl = new ArrayList<>();
        int numservs=serverIn.size();
        ExecutorService executor = Executors.newFixedThreadPool(numservs);
        for(int i=0;i<numservs;i++){
        	if(serverIn.get(i)!=null){
        		BlockchainClientRunnable bcr = new BlockchainClientRunnable(i,serverIn.get(i).getHost(),serverIn.get(i).getPort(),message);
        		cleverGirl.add(bcr);
        		executor.submit(bcr);
        	}
        }
        
        try{
        	executor.awaitTermination(600, TimeUnit.MILLISECONDS);
        }catch(InterruptedException e){
        	
        }
        for(BlockchainClientRunnable b : cleverGirl){
        	System.out.print(b.getReply());
        }
               
        
    }
 
    public static void multicast (ServerInfoList serverInfoList, ArrayList<Integer> serverIndices, String message) {
        // implement your code here
    	//for each server in indices and list
    	ArrayList<ServerInfo> serverIn = serverInfoList.getServerInfos();
    	ArrayList<BlockchainClientRunnable> cleverGirl = new ArrayList<>();
    	ExecutorService executor = Executors.newFixedThreadPool(serverIndices.size());
    	for(int i : serverIndices){
    		if(serverIn.get(i)!=null){
    			BlockchainClientRunnable bcr = new BlockchainClientRunnable(i,serverIn.get(i).getHost(),serverIn.get(i).getPort(),message);
    			cleverGirl.add(bcr);
    			executor.submit(bcr);
    		}
    	}
    	
        try{
        	executor.awaitTermination(600, TimeUnit.MILLISECONDS);
        }catch(InterruptedException e){
        	
        }
        for(BlockchainClientRunnable b : cleverGirl){
        	System.out.print(b.getReply());
        }
    }



	
    public void setServerInfoList(ServerInfoList pl2) {
		// TODO Auto-generated method stub
		pl=pl2;
	}



	public void serverHandler(ByteArrayInputStream clientInputStream, ByteArrayOutputStream clientOutputStream) {
		// TODO Auto-generated method stub
        Scanner sc = new Scanner(clientInputStream);
        PrintWriter outWriter = new PrintWriter(clientOutputStream, true);
        while (sc.hasNextLine()) {
            String message = sc.nextLine();
            // INPUT IS LIST
            if(message.matches("ls")){
            	outWriter.println(pl.toString());
            } 
            //ADD A NEW SERVER
            else if(message.startsWith("ad")){
            	//add new server
        		String[] infos = message.split("\\|");
        		//handles addd
        		if(!infos[0].equals("ad")){
        			outWriter.println("Unknown Command\n\n");
        		}
        		//handles invalid parameters
        		if(infos.length!=3){
        			outWriter.println("Failed\n");
        		}
        		else{
        			if(infos[2].length()==4){
        				int tempPort=0;
        				//make sure port is valid
        				try{
        					tempPort = Integer.parseInt(infos[2]);
        				} catch (NumberFormatException n){

        				}
        				if(tempPort!=0){		
        					ServerInfo tempServ = new ServerInfo(infos[1],Integer.parseInt(infos[2]));
        					if(pl.addServerInfo(tempServ)){
        						outWriter.println("Succeeded\n");
        					}else{
        						outWriter.println("Failed\n");
        					}		
        			}else{
        				outWriter.println("Failed\n");
        			}
        		}        		
        	}
        } 
        //REMOVE A SERVER  ****TESTED******
        else if(message.startsWith("rm")){
        	String[] infos = message.split("\\|");
        	//Check Valid Format
    		if(!infos[0].equals("rm")){
    			outWriter.println("Unknown Command\n");
    		}
    		else if(infos.length!=2){	
    			outWriter.println("Failed\n");
        	}
        	else{
        		//Check valid server index format
        		int tempVal =-1;
        		try{
					tempVal = Integer.parseInt(infos[1]);
				} catch (NumberFormatException n){

				}
        		//check if server is within pl size
				if(tempVal!=-1){
        			if(pl.getServerInfos().size()>tempVal && tempVal>=0){
        				if(pl.getServerInfos().get(tempVal)!=null){
        						if(pl.removeServerInfo(tempVal)){
        							outWriter.println("Succeeded\n");
        						}else{
        							outWriter.println("Failed\n");
                				}
        					}else{
        						outWriter.println("Failed\n");
            				}
        				}
        				else{
        					//outWriter.println("Failed\n");
        				}
        			}
					else{
						outWriter.println("Failed\n");
    				}
        		}
        	
        	//UPDATE VALUE ***TESTED****
        	} else if(message.startsWith("up")){
        		String[] infos = message.split("\\|");
        		//check actually updating
        		if(!infos[0].equals("up")){
        			System.out.println("Unknown Command\n");
        		}
        		//check valid length
        		else if(infos.length!=4){
        			System.out.println("Failed\n");
        		}
        		else{
            		//Check valid server index format
            		int tempVal =-1;
            		int tempPort =0;
            		try{
    					tempVal = Integer.parseInt(infos[1]);
    					tempPort = Integer.parseInt(infos[3]);
    				} catch (NumberFormatException n){

    				}
            		//All the validity checks!
            		if(tempVal!=-1 && tempPort!=0 && tempPort>1023 && tempPort < 65536 && pl.getServerInfos().size()>tempVal && tempVal>=0){
		        			ServerInfo tempServ = new ServerInfo(infos[2],tempPort);
		        			if(pl.updateServerInfo(tempVal,tempServ)){
		        				outWriter.println("Succeeded\n");
		        			}else{
		        				outWriter.println("Failed\n");
		        			}	
		        	}else{
		        		
	        		}
            	}
        	
        	//ADD TRANSACTION
        	} else if(message.startsWith("tx")){    		
        		String[] infos = message.split("\\|");
        		//check actually updating
        		if(!infos[0].equals("tx")){
        			outWriter.println("Unknown Command\n");
        		}
        		//check valid length
        		else if(infos.length!=3){
        			
        		}
        		//broadcast
        		else{
        			broadcast(pl,message);
        		}       		        		
        	} 
        	//CLEAR NULL VALUES
        	else if(message.matches("cl")){
        		for(int i=0;i<pl.getSize()-1;i++){
        			if(pl.getServerInfos().get(i)==null){
        				pl.getServerInfos().remove(i);
        			}
        		}
        		outWriter.println("Succeeded\n");
        		//remove all null values
        		
        	} 
        	
        	//PRINT BLOCKCHAIN
        	else if(message.startsWith("pb")){
        		//BROADCAST THE PB TO EACH THREAD AND PRINT REPLYS (IF NO SERVER INDEX), UNICAST TO SPECIFIC SERVER
        		//MULTICAST TO SPECIFIED SRVER IF MORE THANONE AT GIVEN INDEX
        		
        		//Validity Check
        		String[] infos = message.split("\\|");
        		if(!infos[0].equals("pb")){
        			outWriter.println("Unknown Command\n");
        		}
        		else{
        			//no index
        			if(infos.length==1){
        				broadcast(pl,"pb");
        			}
        			//Indexes provided
        			else{
        				//iterate, starting at 1  (remove pb)
        				ArrayList<Integer> store = new ArrayList<>();
        				for(int i=1;i<infos.length;i++){
                    		try{
            					store.add(Integer.parseInt(infos[i]));
            				} catch (NumberFormatException n){
            					
            				}
        				}
        				//1 server index
        				if(store.size()==1 && store.get(0)>-1 && store.get(0)<pl.getServerInfos().size()){
        					unicast(store.get(0),pl.getServerInfos().get(store.get(0)),"pb");
        				}
        				//more than 1 server index
        				else if(store.size()>1){
        					multicast(pl,store,"pb");
        				}
        				
        			}
        		}
        		        	           		
        	} 
            //SHUTDOWN SERVER 
        	else if(message.startsWith("sd")){
        		//close all servers, then returns
        		
        		
        		return;
        		
        	}
        	
        	else{
        		System.out.println("Unknown Command\n");
        	}
        
        } 
	}
	

    // implement any helper method here if you need any
    
}