import java.util.*;
import java.io.*;

public class ServerInfoList {

    ArrayList<ServerInfo> serverInfos;
    int size;

    public ServerInfoList() {
        serverInfos = new ArrayList<>();
    }

    public void initialiseFromFile(String filename) {
        // implement your code here
    	if(filename==null){
    		return;
    	}
    	
            //fr reads text file
            FileReader fr;
			try {
				fr = new FileReader(filename);
				BufferedReader br = new BufferedReader(fr);
            
            //populate arraylist store with all txt values
            String line = null;
            ArrayList<String> store = new ArrayList<>();
            while((line = br.readLine()) != null) {
            	line = line.replaceAll("\\s","");
            	if(!line.equals("")){
            		store.add(line);
            	}
            }   
        
            //close file.
            br.close();  
            
            int servNums =0;
            //find last server.num
            for( String s : store){
            	String[] tokens = s.split("\\=");
            	if(tokens.length==2){
            		if(tokens[0].matches("servers.num") && tokens.length>1){
                		servNums=Integer.parseInt(tokens[1]);
                	}
            	}
            }
            //check that servnum was found, set size
            if(servNums==0){
            	this.serverInfos.clear();
            	return;
            }
            this.size=servNums;
      
            
            //TIME TO ITERATE AND ADD THE SERVERS
            //server2.host=localhost -> SECOND VALUE CANNOT BE EMPTY *****DONE******
            //server2.port=8334 -> 1023> and <65536
            //if not in pairs, or doesn't match, add null 
            //IF SERVER INDEX >= SIZE, IGNORE *****DONE*****
            int tempserv=0;
            boolean canadd = false;
            String tempHost = "";
            for(String s : store){
            	String[] tokens = s.split("\\=");
            	//check starting statements
            	//if this is a server
            	if(tokens[0].matches("server[\\d].host")){
            		
            		//get the server number
            		tempserv = Character.getNumericValue(tokens[0].charAt(6));
            		//ignore if value is out of our list
            		if(tempserv>=this.size){
            			break;
            		}
            		//add null if second value is empty
            		if(tokens.length<2){
            			try {
            				this.serverInfos.get(tempserv);
            				
            			} catch (IndexOutOfBoundsException e) {
            				this.serverInfos.add(tempserv,null);
                			
            			}
            			
            		}else{
            		//passed the tests
            		tempHost=tokens[1];
            		canadd=true;
            		}
            	}
            	
            	//CHECK PORT
            	if(tokens[0].matches("server[\\d].port")){
            		//check to see if they match
            		int checkserv = Character.getNumericValue(tokens[0].charAt(6));
            		//ignore if out of scope
            		if(checkserv>=this.size){
            			canadd=false;
            			
            		}
            		//check if port value is provided
            		if(tokens.length<2 || checkserv!=tempserv || !canadd){
            			try {
            				canadd=false;
            				this.serverInfos.get(tempserv);
            
            			} catch (IndexOutOfBoundsException e) {
            				this.serverInfos.add(tempserv,null);
            				
          
            			}
            		}else{
            			//check if pair is valid
            			try{
            				int portnum = Integer.parseInt(tokens[1]);
            				if(portnum<1023 || portnum >65536){
            					throw new NumberFormatException("port invalid");
            				} else{
            					ServerInfo temp = new ServerInfo(tempHost,portnum);
            					this.serverInfos.add(temp);
            					canadd=false;
            					
            				}
            			} catch(NumberFormatException e){
            				System.out.println(e);
                			try {
                				canadd=false;
                				this.serverInfos.get(tempserv);
                			
                			} catch (IndexOutOfBoundsException f) {
                				this.serverInfos.add(tempserv,null);
                    			
                			}
            			}
            			
            		
            	}
            }            		        		
           }
            for(int i=0;i<this.size;i++){
    			try {
    				this.serverInfos.get(i);
    	
    			} catch (IndexOutOfBoundsException e) {
    				this.serverInfos.add(i,null);			
    			}
    			
            }
                            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
        return this.size;
    }
    // implement any helper method here if you need any
}