import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

public class HeartBeat {

    public static void main (String[] args) {

        if (args.length != 1) {
            return;
        }

        HashMap<String, Date> serverStatus = new HashMap<String, Date>(){};
        serverStatus.put(args[0], new Date());

        // start up PeriodicPrinter

        // start up PeriodicHeartBeat

        // create server socket and accept client connection
    }
}
