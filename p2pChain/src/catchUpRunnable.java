import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

 
public class catchUpRunnable implements Runnable {
	
    private String ip;
    private String message;
    private int port;
	private Blockchain blockchain;
	
	public catchUpRunnable(String ip,int port, String message, Blockchain bchain) throws IOException{
        this.ip = ip;
        this.message = message;
        this.port=port;
        this.blockchain=bchain;
	}
	@Override
	public void run() {
        try {
            // create socket with a timeout of 2 seconds
            Socket toServer = new Socket();
            toServer.connect(new InetSocketAddress(ip, port), 2000);
            
            //buffed reader / object output -> read in "CU", 
            ObjectOutputStream oos = new ObjectOutputStream(toServer.getOutputStream());
            BufferedReader inputReader = new BufferedReader( new InputStreamReader(toServer.getInputStream()));
            
            
            
            
        } catch (IOException  e) {
        	System.out.println(e.getStackTrace());
        }
	}
}