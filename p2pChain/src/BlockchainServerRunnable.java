import java.io.*;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

public class BlockchainServerRunnable implements Runnable{

    private Socket clientSocket;
    private Blockchain blockchain;
    private HashMap<ServerInfo, Date> serverStatus;

    public BlockchainServerRunnable(Socket clientSocket, Blockchain blockchain, HashMap<ServerInfo, Date> serverStatus) {
        this.clientSocket = clientSocket;
        this.blockchain = blockchain;
        this.serverStatus = serverStatus;
    }

    public void run() {
        try {
            serverHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    public void serverHandler(InputStream clientInputStream, OutputStream clientOutputStream) {

        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(clientInputStream));
        PrintWriter outWriter = new PrintWriter(clientOutputStream, true);
        
        String localIP = (((InetSocketAddress) clientSocket.getLocalSocketAddress()).getAddress()).toString().replace("/", "");
        String remoteIP = (((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
        
        
        try {
            while (true) {
                String inputLine = inputReader.readLine();
                if (inputLine == null) {
                    break;
                }

                String[] tokens = inputLine.split("\\|");
                switch (tokens[0]) {
                    case "tx":
                        if (blockchain.addTransaction(inputLine))
                            outWriter.print("Accepted\n\n");
                        else
                            outWriter.print("Rejected\n\n");
                        outWriter.flush();
                        break;
                    case "pb":
                        outWriter.print(blockchain.toString() + "\n");
                        outWriter.flush();
                        break;
                    case "hb":
                        outWriter.print(blockchain.toString() + "\n");
                        outWriter.flush();
                        break;
                    case "cc":
                        return;
                    default:
                        outWriter.print("Error\n\n");
                        outWriter.flush();
                }
            }
        } catch (IOException e) {
        }
    }
}
