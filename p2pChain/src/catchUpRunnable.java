import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

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
            PrintWriter printWriter = new PrintWriter(toServer.getOutputStream(), true);
            ObjectInputStream ois = new ObjectInputStream(toServer.getInputStream());
            // send the message forward
            printWriter.println(message);
            printWriter.flush();
            if(message.equals("cu")){
	            Block temp = (Block) ois.readObject();
	            blockchain.setHead(temp);
	            blockchain.setLength(blockchain.getLength()+1);
            }else if(message.startsWith("cu|")){
            	Blockchain temp = (Blockchain) ois.readObject();
            	System.out.println(temp.toString());
            }
            // close printWriter and socket
            ois.close();
            printWriter.close();
            toServer.close();
        } catch (IOException | ClassNotFoundException e) {
        }
	}
}
