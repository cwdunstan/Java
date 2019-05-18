import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class sendBlockRunnable implements Runnable {

    private String ip;
    private int port;
	private Blockchain blockchain;
	private Socket client;
	
	public sendBlockRunnable(Socket ClientSocket, Blockchain blockchain){
        this.ip = ip;
        this.port=port;
        this.blockchain=blockchain;
        this.client= ClientSocket;
	}
	
    @Override
    public void run() {
        try {
        	System.out.println("I want to send something");
            // create socket with a timeout of 2 seconds
            String remoteIP = (((InetSocketAddress) client.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
            Socket toClient = new Socket(remoteIP,client.getPort());
            ObjectOutputStream outStream = new ObjectOutputStream(toClient.getOutputStream());
            // send the block forward
            Thread thread = new Thread(new catchUpRunnable(remoteIP,toClient.getPort(), "cu",blockchain));
            thread.start();
            
            
            outStream.writeObject(blockchain);
            outStream.flush();
            System.out.println("I sent "+blockchain.getLength());
            // close objectStream and socket
            outStream.close();
            toClient.close();

        } catch (IOException e) {
        }
    }
}
