import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class sendBlockRun implements Runnable {
	
	private int port;
	private String ip;
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
		Socket toServer = new Socket();
		try {
			toServer.connect(new InetSocketAddress(ip, port), 2000);
		    ObjectOutputStream oos = new ObjectOutputStream(toServer.getOutputStream());
	 	   	oos.flush();
		    BufferedReader clientReader = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
            String inputLine = clientReader.readLine();

            System.out.println(inputLine);

		    clientReader.close();
		    toServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    
		}


}
