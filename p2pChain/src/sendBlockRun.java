import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class sendBlockRun implements Runnable {
	private String ip;
	private int port;
	private Blockchain chain;
	private String[] tokens;
	
	public sendBlockRun(String ip, int port, Blockchain chain){
		this.ip=ip;
		this.port=port;
		this.chain=chain;
		this.tokens=tokens;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			Socket toClient = new Socket();
			toClient.connect(new InetSocketAddress(ip, port), 2000);
	        BufferedReader clientReader = new BufferedReader(
	                new InputStreamReader(toClient.getInputStream()));
    	    PrintWriter ow = new PrintWriter(toClient.getOutputStream(), true);
    	    
		    String inputLine=null;
            while((inputLine = clientReader.readLine())!=null) {
            if(inputLine.matches("cu")) {
            	System.out.println("server got cu");
            }
            }
		    clientReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    
		}


}
