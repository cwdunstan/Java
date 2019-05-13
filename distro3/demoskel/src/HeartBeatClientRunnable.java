import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HeartBeatClientRunnable implements Runnable{

    private String ip;
    private String message;

    public HeartBeatClientRunnable(String ip, String message) {
        this.ip = ip;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            // create socket with a timeout of 2 seconds

            // send the message forward

            // close printWriter and socket
        } catch (IOException e) {
        }
    }
}
