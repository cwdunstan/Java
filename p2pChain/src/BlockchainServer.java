import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BlockchainServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            return;
        }

        int localPort = 0;
        int remotePort = 0;
        String remoteHost = null;

        try {
            localPort = Integer.parseInt(args[0]);
            remoteHost = getIP(args[1]);
            remotePort = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            return;
        }

        Blockchain blockchain = new Blockchain();

        Map<ServerInfo, Date> serverStatus = Collections.synchronizedMap(new HashMap<>());
        serverStatus.put(new ServerInfo(remoteHost, remotePort), new Date());

        PeriodicCommitRunnable pcr = new PeriodicCommitRunnable(blockchain);
        Thread pct = new Thread(pcr);
        pct.start();
        
        PeriodicHeartBeatRunnable phb = new PeriodicHeartBeatRunnable(localPort,serverStatus);
        Thread phbt = new Thread(phb);
        phbt.start();
        
        PeriodicStatusRunnable psr = new PeriodicStatusRunnable(serverStatus);
        Thread psrt = new Thread(psr);
        psrt.start();
        
        PeriodicBlockSend pbs = new PeriodicBlockSend(localPort,serverStatus,blockchain);
        Thread pbst = new Thread(pbs);
        pbst.start();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(localPort);

        	
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new BlockchainServerRunnable(clientSocket, blockchain, serverStatus)).start();
            }
        } catch (IllegalArgumentException e) {
        } catch (IOException e) {
        } finally {
            try {
                pcr.setRunning(false);
                pct.join();
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
            } catch (InterruptedException e) {
            }
        }
    }
    
    public static String getIP(String host){
		try {
			InetAddress inetAddr = InetAddress.getByName(host);
	        byte[] rawadd = inetAddr.getAddress();
	        String ip = "";
	        for (int i=0;i < rawadd.length; i++) {
	            if(i>0){
	                ip+=".";
	            }
	            ip += rawadd[i] & 0xFF;
	        }
	        return ip;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

}
