import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;

public class readBlockRun implements Runnable {

	private int port;
	private String ip;
	private Blockchain chain;
	private int index;
	private String theirHash;
	
	public readBlockRun(String ip, int port, Blockchain chain, int index, String theirHash){
		this.ip=ip;
		this.port=port;
		this.chain=chain;
		this.index = index;
		this.theirHash=theirHash;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket toServer = new Socket();
		String encodedhash=null;
        try {
			toServer.connect(new InetSocketAddress(ip, port), 2000);
    	    PrintWriter ow = new PrintWriter(toServer.getOutputStream(), true);
    	  
    	    boolean head=false;
    	    
    	    //if my chain is empty, give me your head
    	    if(chain.getLength()==0){
    	    	ow.print("cu\n");
    	    	ow.flush();
    	    	head = true;
    	    }
    	    // my head doesn't match theirs, give me your head
    	    else if(!Base64.getEncoder().encodeToString(chain.getHead().calculateHash()).equals(theirHash)){
    	    	ow.print("cu\n");
    	    	ow.flush();
    	    	head = true;
    	    }
    	    // we have the same head, but not the same length, give me the next from head down.
    	    else{
    	    	Block track = chain.getHead();
    	    	while(track.getPreviousBlock()!=null){
    	    		track=track.getPreviousBlock();
    	    	}
    	    	ow.print("cu|"+Base64.getEncoder().encodeToString(track.getPreviousHash())+"\n");
    	    	ow.flush();
    	    }
    	    
    	    
    	    //read in a block
            ObjectInputStream ois = new ObjectInputStream(toServer.getInputStream());
            Block temp = (Block) ois.readObject();
            
            //receiving the head of the other chain
            if(head){
            	//if my chain is empty, add the head
            	if(chain.getLength()==0){
            		chain.setHead(temp);
            		chain.setLength(chain.getLength()+1);
            	}
            	//my chain is not empty, but heads differ
            	else{
            		//if adding this head fills the chain
            		if(chain.getLength()+1 == index){
            			//if current head can be connected
            			if(Base64.getEncoder().encodeToString(chain.getHead().calculateHash()).equals(Base64.getEncoder().encodeToString(temp.getPreviousHash()))){
                			temp.setPreviousBlock(chain.getHead());
                			chain.setHead(temp);
                			chain.setLength(chain.getLength()+1);
            			}
            			//current head cant be connected, remove previous head
            			else{
            				if(chain.getHead().getPreviousBlock()!=null){
            					chain.getPool().addAll(chain.getHead().getTransactions());
            					chain.setHead(chain.getHead().getPreviousBlock());
            				}else{
            					chain.setHead(null);
            				}
            				chain.setHead(temp);
            			}
            		}
            		//adding this head doesn't fill the chain
            		else{
            			chain.setHead(temp);
            			chain.setLength(chain.getLength()+1);
            		}
            	}
            }
            
            //not receiving the head of the other chain
            else{
            	Block track = chain.getHead();
            	while(!Base64.getEncoder().encodeToString(track.getPreviousHash()).equals(Base64.getEncoder().encodeToString(temp.calculateHash()))){
            		track=track.getPreviousBlock();
            	}
            	track.setPreviousBlock(temp);
            	chain.setLength(chain.getLength()+1);
            	
            }
            
         
	        	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


      }
}


