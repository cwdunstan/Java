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
		// TODO
                    break;
		// TODO
            }
            bufferedReader.close();
            toClient.close();
        } catch (Exception e) {
        }
    }
}
