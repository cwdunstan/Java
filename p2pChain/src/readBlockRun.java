import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Base64;

public class readBlockRun implements Runnable {

	private int port;
	private String ip;
	private Blockchain chain;
	private int index;
	
	public readBlockRun(String ip, int port, Blockchain chain, int index){
		this.ip=ip;
		this.port=port;
		this.chain=chain;
		this.index = index;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket toServer = new Socket();
        System.out.println("readblock started");
        try {
			toServer.connect(new InetSocketAddress(ip, port), 2000);
    	    PrintWriter ow = new PrintWriter(toServer.getOutputStream(), true);
    	    System.out.println("Im sending a CU");
    	    ow.print("cu\n");
            ow.flush();
    	    ObjectInputStream ois = new ObjectInputStream(toServer.getInputStream());
    	    Block temp = null;
    	    while(temp==null) {
    	    	temp = (Block) ois.readObject();
    	    }
    	    System.out.println(temp.toString());
    	    
//            ObjectInputStream ois = new ObjectInputStream(toClient.getInputStream());
//            Block temp = (Block) ois.readObject();
//            System.out.println(temp.getCurrentHash());
//            ow.close();

	        	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}

}
