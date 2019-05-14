import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PeriodicHeartBeatRunnable implements Runnable {

    private Map<ServerInfo, Date> serverStatus;
    private int sequenceNumber;
    private int localPort;

    public PeriodicHeartBeatRunnable(int localPort,Map<ServerInfo, Date> serverStatus2) {
        this.serverStatus = serverStatus2;
        this.localPort=localPort;
        this.sequenceNumber = 0;
    }

    @Override
    public void run() {
        while(true) {
            // broadcast HeartBeat message to all peers
            ArrayList<Thread> threadArrayList = new ArrayList<>();
            for (ServerInfo serverInfo : serverStatus.keySet()) {
				try {
					InetAddress inetAddr = InetAddress.getByName(serverInfo.getHost());
		            byte[] rawadd = inetAddr.getAddress();
		            String ip = "";
		            for (int i=0;i < rawadd.length; i++) {
		                if(i>0){
		                    ip+=".";
		                }
		                ip += rawadd[i] & 0xFF;
		            }
	                Thread thread = new Thread(new HeartBeatClientRunnable(ip,serverInfo.getPort(), "hb|" +localPort+"|"+ sequenceNumber));
	                threadArrayList.add(thread);
	                thread.start();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


            }

            for (Thread thread : threadArrayList) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                }
            }

            // increment the sequenceNumber
            sequenceNumber += 1;

            // sleep for two seconds
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }
    }
}
