import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PeriodicHeartBeatRunnable implements Runnable {

    private HashMap<String, Date> serverStatus;
    private int sequenceNumber;

    public PeriodicHeartBeatRunnable(HashMap<String, Date> serverStatus) {
        this.serverStatus = serverStatus;
        this.sequenceNumber = 0;
    }

    @Override
    public void run() {
        while(true) {
            // broadcast HeartBeat message to all peers
            ArrayList<Thread> threadArrayList = new ArrayList<>();
            for (String ip : serverStatus.keySet()) {
                Thread thread = new Thread(new HeartBeatClientRunnable(ip, "HeartBeat " + sequenceNumber));
                threadArrayList.add(thread);
                thread.start();
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
