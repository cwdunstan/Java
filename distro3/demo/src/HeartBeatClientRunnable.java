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
            Socket toServer = new Socket();
            toServer.connect(new InetSocketAddress(ip, 8333), 2000);
            PrintWriter printWriter = new PrintWriter(toServer.getOutputStream(), true);

            // send the message forward
            printWriter.println(message);
            printWriter.flush();

            // close printWriter and socket
            printWriter.close();
            toServer.close();
        } catch (IOException e) {
        }
    }
}
