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
		Socket toClient = new Socket();
        System.out.println("lb:"+ip+" port: "+port);
        try {
			toClient.connect(new InetSocketAddress(ip, port), 2000);
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
    	    PrintWriter ow = new PrintWriter(toClient.getOutputStream(), true);
    	    System.out.println("Im sending a CU");
    	    ow.print("cu");
            ow.flush();
            ow.close();
            toClient.close();

	        	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}

}
