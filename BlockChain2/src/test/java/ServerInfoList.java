import java.util.*;
import java.io.*;

public class ServerInfoList {

    ArrayList<ServerInfo> serverInfos;

    public ServerInfoList() {
        serverInfos = new ArrayList<>();
    }

    public void initialiseFromFile(String filename) {
        // implement your code here
    	if(filename==null){
    		return;
    	}
    	try {
            //fr reads text file
            FileReader fr =  new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            
            //populate arraylist store with all txt values
            String line = null;
            ArrayList<String> store = new ArrayList<>();
            while((line = br.readLine()) != null) {
            	if(!line.equals("")){
            		store.add(line);
            	}
            }   
        
            //close file.
            br.close();  
            
            int serversnum=0;
            int curserv =0;
            String temphost="";
            boolean newserv = false;
            //iterate through store
            for(int i=0;i<store.size();i++){
            	String[] tokens = store.get(i).split("\\=");
            	//get number of servers
            	if(tokens.length>0){
            		
            	if(tokens[0].matches("servers.num") && tokens.length>1){
            		serversnum=Integer.parseInt(tokens[1]);
            	}
          
            	//if host is specified
            	if(tokens[0].matches("server[\\d]\\.host")){
            		if(tokens.length>1){
            		//make sure note the last line of file
	            		if(newserv){
	            			this.serverInfos.add(null);
	            			newserv=false;
	            		}
	            		curserv=Character.getNumericValue(tokens[0].charAt(6));
	            		if(curserv<serversnum){
	            			newserv=true;
	            			temphost=tokens[1];
	            		}else{
	            			newserv=false;
	            		}
            		}
            	}
            	
            	if(tokens[0].matches("server[\\d]\\.port")){
            		if(tokens.length>1){
            		//make sure note the last line of file
	            		int tempserv =Character.getNumericValue(tokens[0].charAt(6));
	            		if(tempserv == curserv && newserv){
	            			int tempPort = Integer.parseInt(tokens[1]);
	    					if(tempPort >1023 && tempPort <65536){
	    						ServerInfo temp = new ServerInfo(temphost,tempPort);
	    						this.serverInfos.add(temp);
	    					}else{
	    						this.serverInfos.add(null);
	    					}
	    					temphost="";
	    					newserv=false;
	            		}
            		}
            	}
            	
            	}	
            	
            }
        }
        catch(FileNotFoundException e) {
        	e.printStackTrace();           
        }
        catch(IOException e) {
             e.printStackTrace();
        }
    	 catch(IndexOutOfBoundsException e) {
             e.printStackTrace();
        }
    }

    public ArrayList<ServerInfo> getServerInfos() {
        return serverInfos;
    }

    public void setServerInfos(ArrayList<ServerInfo> serverInfos) {
        this.serverInfos = serverInfos;
    }

    public boolean addServerInfo(ServerInfo newServerInfo) { 
        // implement your code here
    	if(newServerInfo!=null){
    		this.serverInfos.add(newServerInfo);
    		return true;
    	}
    	return false;
    }

    public boolean updateServerInfo(int index, ServerInfo newServerInfo) { 
        // implement your code here
    	if(newServerInfo!=null && index<serverInfos.size()){
    		this.serverInfos.set(index,newServerInfo);
    		return true;
    	}
    	return false;
    }
    
    public boolean removeServerInfo(int index) { 
        // implement your code here
    	if(serverInfos==null){
    		return false;
    	}
    	if(index<serverInfos.size()){
    		this.serverInfos.set(index,null);
    		return true;
    	}
    	return false;
    }

    public boolean clearServerInfo() { 
        // implement your code here
    	if(this.serverInfos!=null){
    		this.serverInfos.clear();
    		return true;
    	}
    	return false;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < serverInfos.size(); i++) {
            if (serverInfos.get(i) != null) {
                s += "Server" + i + ": " + serverInfos.get(i).getHost() + " " + serverInfos.get(i).getPort() + "\n";
            }
        }
        return s;
    }
    
    public int getSize() {
        return serverInfos.size();
    }
    // implement any helper method here if you need any
}