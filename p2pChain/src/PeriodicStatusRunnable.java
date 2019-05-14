import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class PeriodicStatusRunnable implements Runnable {
    
	private Map<ServerInfo, Date> serverStatus;

    public PeriodicStatusRunnable(Map<ServerInfo, Date> serverStatus) {
        this.serverStatus = serverStatus;
    }

    @Override
    public void run() {
        while(true) {
            for (ServerInfo serv : serverStatus.keySet()) {
                // if greater than 2T, remove
                if (new Date().getTime() - serverStatus.get(serv).getTime() > 4000) {
                    serverStatus.remove(serv);
                }else{
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
