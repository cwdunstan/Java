import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
       	Socket mySocket;
		try {
			mySocket = new Socket(serverName,portNumber);
			bcc.clientHandler(mySocket.getInputStream(),mySocket.getOutputStream());
	       	mySocket.close();
		} 
		catch (IOException e) {
			return;
		}
		
    } 
    

    public void clientHandler(InputStream serverInputStream, OutputStream serverOutputStream) throws IOException {
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(serverInputStream));
        PrintWriter outWriter = new PrintWriter(serverOutputStream, true);
        Scanner sc = new Scanner(System.in);
        String received = null;
        while (sc.hasNextLine()) {
        	String command = sc.nextLine();
        	if(!command.matches("cc")) {
        		outWriter.println(command);
        		if((received = inputReader.readLine())!=null) {
        			System.out.println(received);
        			while(inputReader.ready()) {
        				System.out.println(inputReader.readLine());
        				}
        		}
        	}
        	else {
        		outWriter.println(command);
        		outWriter.flush();
        		inputReader.close();
        		outWriter.close();
        		break;
        	}
        }
        outWriter.flush();
		inputReader.close();
		outWriter.close();
        return;
    }
}

    // implement helper functions here if you need any.
