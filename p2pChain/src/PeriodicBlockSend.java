import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PeriodicBlockSend implements Runnable{

	private Map<ServerInfo, Date> serverStatus;
	private Blockchain block;
	private int portnum;

    public PeriodicBlockSend(int portnum, Map<ServerInfo, Date> serverStatus, Blockchain blockchain) {
        this.serverStatus = serverStatus;
        this.block=blockchain;
        this.portnum=portnum;
    }
    
	@Override
	public void run() {
		while(true){
			//send latest blocks hash to 5 random peers periodically
			ArrayList<Thread> threadArrayList = new ArrayList<>();
			String encodedString = "";
			if(block.getLength()>0){
				encodedString = Base64.getEncoder().encodeToString(block.getHead().calculateHash());
			}
	        for (ServerInfo serv : serverStatus.keySet()) {
	        		Thread thread = new Thread(new HeartBeatClientRunnable(serv.getHost(),serv.getPort(),"lb|"+portnum+"|"+block.getLength()+"|"+encodedString));
	        		threadArrayList.add(thread);
	        		thread.start();  
	        }
	        for (Thread thread : threadArrayList) {
	            try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
			
			
	        // sleep for two seconds
	        try {
	            Thread.sleep(2000);
	        } catch (InterruptedException e) {
	        }
		}
	}

}
