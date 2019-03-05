import java.io.*;
import java.util.Scanner;

public class BlockchainClient {
    public static void main(String[] args) {

        if (args.length != 2) {
            return;
        }
        String serverName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        BlockchainClient bcc = new BlockchainClient();

        // TODO: implement your code here.
    }

    public void clientHandler(InputStream serverInputStream, OutputStream serverOutputStream) {
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(serverInputStream));
        PrintWriter outWriter = new PrintWriter(serverOutputStream, true);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            // TODO: implement your code here
        }
    }

    // implement helper functions here if you need any.
}