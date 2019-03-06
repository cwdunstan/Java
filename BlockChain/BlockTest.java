import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class BlockTest extends Transaction {

	/*@Test
	public void testNewBlock() {
		Block newBlock = new Block();
		newBlock.setPreviousBlock(null);
		newBlock.setPreviousHash(new byte[0]);
		System.out.println(newBlock);
	}*/

	@Test
	public void testCalculateHash() {
		/// ADD A BUNCH OF TRANSACTIONS AND TEST BLOCKS WORK HEY
		Blockchain newChain = new Blockchain();
		System.out.println(newChain.getLength());
		newChain.addTransaction("tx|test0000|1");
		newChain.addTransaction("tx|test0000|2");
		newChain.addTransaction("tx|test0000|3");
		newChain.addTransaction("tx|test0000|4");
		System.out.println(newChain.getLength());
		newChain.addTransaction("tx|test0000|5");
		newChain.addTransaction("tx|test0000|6");
		newChain.addTransaction("tx|test0000|7");
		System.out.println(newChain.getLength());
		newChain.addTransaction("tx|test0000|8");
		
	
		System.out.println(newChain.getLength());

		System.out.println(newChain+"\n");
		

		
	}
	
	/*@Test
	public void testvalid() {
		/// ADD A BUNCH OF TRANSACTIONS AND TEST BLOCKS WORK HEY
		Blockchain newChain = new Blockchain();
		
		newChain.transTest("tx|test0001|messageeeemessageeeemessageeeemessageeeemessageeeemessageeeemessageeee");
		//newChain.addTransaction("tx|test0002|message");
		//newChain.addTransaction("tx|test0003|message");
		
		System.out.println();
		
	}*/

}
