import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class UnitTesting {

//*************************************************TEST BLOCKCHAIN FUNCTIONALITY*********************************************//
	//add one transaction
	@Test
	public void addOneTransaction() throws Exception {
	    Blockchain bc = new Blockchain();
	    boolean returnValue = bc.addTransaction("tx|test1111|1");

	    assertTrue(1 ==  bc.getPool().size() && returnValue);
	}
	
	//add multiple transaction
	@Test
	public void addMultipleTransaction() throws Exception {
	    Blockchain bc = new Blockchain();
	    boolean returnValue = bc.addTransaction("tx|test1111|1");
	    boolean returnValue2 = bc.addTransaction("tx|test2222|1");
	    boolean returnValue3 = bc.addTransaction("tx|test3333|1");
	   
	    assertTrue(3 ==  bc.getPool().size() && returnValue && returnValue2 && returnValue3);
	}
	
	//add invalid transactions
	@Test
	public void addInvalidTransaction() throws Exception {
	    Blockchain bc = new Blockchain();
	    boolean returnValue = bc.addTransaction("tx|test|1");
	    boolean returnValue2 = bc.addTransaction("txx|test1234|1");
	    boolean returnValue3 = bc.addTransaction("tx|test12345|1");
	    assertTrue(0 ==  bc.getPool().size() && !returnValue && !returnValue2 && !returnValue3);
	}
	
	
	
	
	//***********************************************TEST SERVERINFO FUNCTIONALITY*******************************************//
	//create & retrieve one
	@Test
	public void createServerInfo() throws Exception {
	    ServerInfo si = new ServerInfo("localhost", 8333);
	 
	    assertTrue(si.getHost().equals("localhost") && si.getPort()==8333);
	}
	
	//Create invalid server infos (should return null for invalid host, and 0 for invalid port).
	@Test
	public void createInvalidServerInfo() throws Exception {
	    ServerInfo si1 = new ServerInfo("", 123456);
	    ServerInfo si2 = new ServerInfo("localhost", 900);
	    
	    assertTrue(si1.getHost()==null && si1.getPort()==0 && si2.getHost().equals("localhost") && si2.getPort()==0);
	}
	
	
	
	
	
//*********************************************TEST SERVERINFOLIST FUNCTIONALITY*********************************************//
	//Initialize from file1, a simple config file, check all values match provided
		/*	servers.num=1
			server0.host=localhost
			server0.port=8334	*/
	@Test
	public void configSimple() throws Exception {
	    ServerInfoList newList = new ServerInfoList();
	    newList.initialiseFromFile("TestInputs/file1.txt");
	    assertTrue(newList.getServerInfos().size()==1 
	    		&& newList.getServerInfos().get(0).getHost().equals("localhost") && newList.getServerInfos().get(0).getPort()==8334);
	}
	
	//Initialize from file2, a simple config file with multiple servers and spacing, check all values match provided
	/*	servers.num=3

		server0.host=localhost
		server0.port=8334

		server1.host=globalhost
		server1.port=8333

		server2.host=127.22.0.420
		server2.port=8335	*/
	@Test
	public void configMultipleSimple() throws Exception {
	    ServerInfoList newList = new ServerInfoList();
	    newList.initialiseFromFile("TestInputs/file2.txt");
	    assertTrue(newList.getServerInfos().size()==3 
	    		&& newList.getServerInfos().get(0).getHost().equals("localhost") && newList.getServerInfos().get(0).getPort()==8334
	    		&& newList.getServerInfos().get(1).getHost().equals("globalhost") && newList.getServerInfos().get(1).getPort()==8333
	    		&& newList.getServerInfos().get(2).getHost().equals("127.22.0.420") && newList.getServerInfos().get(2).getPort()==8335);
	}
	
	//Initialize from file3, a config file with multiple servers, including inconsistent pairing, invalid ports, and too many servers.
	/*	servers.num=3

		server0.host=localhost
		
		server1.host=globalhost
		server1.port=123456
		
		server2.host=localhost
		server2.port=8335
		
		server3.host=localhost
		server3.port=8333	*/
	@Test
	public void configComplexOne() throws Exception {
	    ServerInfoList newList = new ServerInfoList();
	    newList.initialiseFromFile("TestInputs/file3.txt");
	    
	    assertTrue(newList.getServerInfos().size()==3 
	    		&& newList.getServerInfos().get(0)==null
	    		&& newList.getServerInfos().get(1)==null
	    		&& newList.getServerInfos().get(2).getHost().equals("localhost") && newList.getServerInfos().get(2).getPort()==8335);
	}
	
	//Initialize from file4, a config file with multiple servers, including inconsistent pairing, empty values, and too many servers.
	/*	servers.num=5

		server0.host=localhost
		server1.host=globalhost
		server1.port=123456
		
		
		server2.host=localhost
		server2.port=8335
		server3.host=localhost
		server3.port=8333
		
		server0.host=mega
		server5=
		server5=
		server4.host=megaman
		server4.port=6666
			*/
	@Test
	public void configComplexTwo() throws Exception {
	    ServerInfoList newList = new ServerInfoList();
	    newList.initialiseFromFile("TestInputs/file4.txt");
	    
	    assertTrue(newList.getServerInfos().size()==5 
	    		&& newList.getServerInfos().get(0)==null
	    		&& newList.getServerInfos().get(1)==null
	    		&& newList.getServerInfos().get(2).getHost().equals("localhost") && newList.getServerInfos().get(2).getPort()==8335
	    		&& newList.getServerInfos().get(3).getHost().equals("localhost") && newList.getServerInfos().get(3).getPort()==8333
	    		&& newList.getServerInfos().get(4).getHost().equals("megaman") && newList.getServerInfos().get(4).getPort()==6666);
	}
		
	//Initialize from an invalid filepath, to check correct error is thrown (file not found)
	@Test 
	public void configInvalid() {
		ServerInfoList newList = new ServerInfoList();
		try{
			newList.initialiseFromFile("TestInputs/sweatyPits.tat");
		}catch(Exception e){
			assertTrue(e.getMessage().equals("File not found."));
		}
	}
}
