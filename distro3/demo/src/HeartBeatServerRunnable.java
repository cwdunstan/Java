import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class HeartBeatServerRunnable implements Runnable{

    private Socket toClient;
    private HashMap<String, Date> serverStatus;

    public HeartBeatServerRunnable (Socket toClient, HashMap<String, Date> serverStatus) {
        this.toClient = toClient;
        this.serverStatus = serverStatus;
    }

    @Override
    public void run() {
        try {
            String localIP = (((InetSocketAddress) toClient.getLocalSocketAddress()).getAddress()).toString().replace("/", "");
            String remoteIP = (((InetSocketAddress) toClient.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(toClient.getInputStream()));

            String line = bufferedReader.readLine();
            String[] tokens = line.split(" ");
            switch (tokens[0]) {
                case "HeartBeat":
                    serverStatus.put(remoteIP, new Date());
                    if (tokens[1].equals("0")) {
                        ArrayList<Thread> threadArrayList = new ArrayList<>();
                        for (String ip : serverStatus.keySet()) {
                            if (ip.equals(remoteIP) || ip.equals(localIP)) {
                                continue;
                            }
                            Thread thread = new Thread(new HeartBeatClientRunnable(ip, "IP " + remoteIP));
                            threadArrayList.add(thread);
                            thread.start();
                        }
                        for (Thread thread : threadArrayList) {
                            thread.join();
                        }
                    }
                    break;
                case "IP":
                    if (!serverStatus.keySet().contains(tokens[1])) {
                        serverStatus.put(tokens[1], new Date());

                        // relay
                        ArrayList<Thread> threadArrayList = new ArrayList<>();
                        for (String ip : serverStatus.keySet()) {
                            if (ip.equals(tokens[1]) || ip.equals(remoteIP) || ip.equals(localIP)) {
                                continue;
                            }
                            Thread thread = new Thread(new HeartBeatClientRunnable(ip, line));
                            threadArrayList.add(thread);
                            thread.start();
                        }
                        for (Thread thread : threadArrayList) {
                            thread.join();
                        }
                    }
                    break;
            }
            bufferedReader.close();
            toClient.close();
        } catch (Exception e) {
        }
    }
}
