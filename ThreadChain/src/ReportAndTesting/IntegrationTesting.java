import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.io.*;
import java.util.Scanner;
public class IntegrationTesting {

	
/***********************************************SERVER TESTS**********************************************/
	//adds 1 valid and 1 invalid transaction, checks correct output returned
	@Test
	public void addTransactionTest1() throws Exception {

	    byte[] data = "tx|abcd1234|1\ntx|ethdfd11444|2\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();

	    BlockchainServer bcs = new BlockchainServer();
	    bcs.setBlockchain(new Blockchain());
	    bcs.serverHandler(clientInputStream, clientOutputStream);

	    byte[] bytes = clientOutputStream.toByteArray();
	    assertTrue("Accepted\n\nRejected\n\n".equals(new String(bytes)));
	}
	
	
	//adds 4 valid transactions, 2 invalid, checks correct output returned
	@Test
	public void addTransactionTest2() throws Exception {

	    byte[] data = "tx|abcd1234|1\ntx|etddddd11444|2\ntx|dope8008|3\ntx|superlame3013|4\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();

	    BlockchainServer bcs = new BlockchainServer();
	    bcs.setBlockchain(new Blockchain());
	    bcs.serverHandler(clientInputStream, clientOutputStream);

	    byte[] bytes = clientOutputStream.toByteArray();
	    assertTrue("Accepted\n\nRejected\n\nAccepted\n\nRejected\n\n".equals(new String(bytes)));
	}
	
	//adds 2 valid transactions, calls pb, checks correct output returned
	@Test
	public void printTransactionTest1() throws Exception {

	    byte[] data = "tx|abcd1234|1\ntx|halo3430|2\npb".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();

	    BlockchainServer bcs = new BlockchainServer();
	    bcs.setBlockchain(new Blockchain());
	    bcs.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();

	    assertTrue(("Accepted\n\nAccepted\n\nPool:\n"
	    		+ "---------------------------------------------------------------------------------\n"
	    		+ "|abcd1234|                                                                     1|\n"
	    		+ "|halo3430|                                                                     2|\n"
	    		+ "---------------------------------------------------------------------------------\n\n").equals(new String(bytes)));
	}
	
	//adds 4 transactions (3 valid), calls pb, checks correct output returned
	@Test
	public void printTransactionTest2() throws Exception {

	    byte[] data = "tx|beni4209|1\ntx|sthe3430|2\ntx|king9111|1\ntx|helloKitty|711\npb".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();

	    BlockchainServer bcs = new BlockchainServer();
	    bcs.setBlockchain(new Blockchain());
	    bcs.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();

	    assertTrue(("Accepted\n\nAccepted\n\nAccepted\n\nRejected\n\nPool:\n"
	    		+ "---------------------------------------------------------------------------------\n"
	    		+ "|beni4209|                                                                     1|\n"
	    		+ "|sthe3430|                                                                     2|\n"
	    		+ "|king9111|                                                                     1|\n"
	    		+ "---------------------------------------------------------------------------------\n\n").equals(new String(bytes)));
	}

	//input an invalid string, checks correct output returned (unknown command)
	@Test
	public void unknownCommand() throws Exception {

	    byte[] data = "i really hope this works\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();

	    BlockchainServer bcs = new BlockchainServer();
	    bcs.setBlockchain(new Blockchain());
	    bcs.serverHandler(clientInputStream, clientOutputStream);
	    
	    byte[] bytes = clientOutputStream.toByteArray();
	    assertTrue("Unknown Command\n\n".equals(new String(bytes)));
	}




/***********************************************CLIENT TESTS**********************************************/
	//Initialises a client server from  test file 1, then calls List 
	@Test
	public void listTest1() throws Exception {
		
		ServerInfoList pl = new ServerInfoList();
	    pl.initialiseFromFile("TestInputs/file1.txt");
	    byte[] data = "ls\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();
	    BlockchainClient bcc = new BlockchainClient();
	    bcc.setServerInfoList(pl);
	    bcc.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();

	    assertTrue("Server0: localhost 8334\n\r\n".equals(new String(bytes)));
	}
	
	//Initialises a client server from  test file 2 with multiple servers, then calls List 
	@Test
	public void listTest2() throws Exception {
		
		ServerInfoList pl = new ServerInfoList();
	    pl.initialiseFromFile("TestInputs/file2.txt");
	    byte[] data = "ls\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();
	    BlockchainClient bcc = new BlockchainClient();
	    bcc.setServerInfoList(pl);
	    bcc.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();
	    
	    assertTrue(("Server0: localhost 8334\n"
	    		+ "Server1: globalhost 8333\n"
	    		+ "Server2: 127.22.0.420 8335\n\r\n").equals(new String(bytes)));
	}
	
	//Initialises a client server from  test file 1, then updates the value
	@Test
	public void updateTest1() throws Exception {
		
		ServerInfoList pl = new ServerInfoList();
	    pl.initialiseFromFile("TestInputs/file1.txt");
	    byte[] data = "up|0|superhost|8777\nls\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();
	    BlockchainClient bcc = new BlockchainClient();
	    bcc.setServerInfoList(pl);
	    bcc.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();
	    assertTrue("Succeeded\n\r\nServer0: superhost 8777\n\r\n".equals(new String(bytes)));
	}
	
	//Initialises a client server from  test file 1, then attempt to update with invalid value
	@Test
	public void updateTest2() throws Exception {
		
		ServerInfoList pl = new ServerInfoList();
	    pl.initialiseFromFile("TestInputs/file1.txt");
	    byte[] data = "up|0|superhost|87777\nls\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();
	    BlockchainClient bcc = new BlockchainClient();
	    bcc.setServerInfoList(pl);
	    bcc.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();
	    assertTrue("Server0: localhost 8334\n\r\n".equals(new String(bytes)));
	}
	
	//Initialises a client server from  test file 1, then attempt to update with no value provided
	@Test
	public void updateTest3() throws Exception {
		
		ServerInfoList pl = new ServerInfoList();
	    pl.initialiseFromFile("TestInputs/file1.txt");
	    byte[] data = "up|0|\nls\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();
	    BlockchainClient bcc = new BlockchainClient();
	    bcc.setServerInfoList(pl);
	    bcc.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();

	    assertTrue("Server0: localhost 8334\n\r\n".equals(new String(bytes)));
	}
	
	//Initialises a client server from  test file 5, which creates server 1 and 2, then call clear
	@Test
	public void clearTest() throws Exception {
		
		ServerInfoList pl = new ServerInfoList();
	    pl.initialiseFromFile("TestInputs/file5.txt");
	    byte[] data = "cl\nls\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();
	    BlockchainClient bcc = new BlockchainClient();
	    bcc.setServerInfoList(pl);
	    bcc.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();
	    assertTrue("Succeeded\n\r\nServer0: globalhost 8333\nServer1: 127.22.0.420 8335\n\r\n".equals(new String(bytes)));
	}
	
	//Initialises a client server from  test file 5, which creates server 1 and 2, then removes server at index 1
	@Test
	public void removeTest() throws Exception {
		
		ServerInfoList pl = new ServerInfoList();
	    pl.initialiseFromFile("TestInputs/file5.txt");
	    byte[] data = "rm|1\nls\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();
	    BlockchainClient bcc = new BlockchainClient();
	    bcc.setServerInfoList(pl);
	    bcc.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();
	    assertTrue("Succeeded\n\r\nServer2: 127.22.0.420 8335\n\r\n".equals(new String(bytes)));
	}
	
	//Initialises a client server from  test file 5, which creates server 1 and 2, then removes an invalid index
	@Test
	public void removeInvalidTest() throws Exception {
		
		ServerInfoList pl = new ServerInfoList();
	    pl.initialiseFromFile("TestInputs/file5.txt");
	    byte[] data = "rm|4\nls\n".getBytes();
	    ByteArrayInputStream clientInputStream = new ByteArrayInputStream(data);
	    ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();
	    BlockchainClient bcc = new BlockchainClient();
	    bcc.setServerInfoList(pl);
	    bcc.serverHandler(clientInputStream, clientOutputStream);
	    byte[] bytes = clientOutputStream.toByteArray();
	    assertTrue("Server1: globalhost 8333\nServer2: 127.22.0.420 8335\n\r\n".equals(new String(bytes)));
	}
	
	
	
	
}
