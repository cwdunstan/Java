import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class BlockTest extends Transaction {

	//@Test
	//public void testGetTransactions() {
	//	fail("Not yet implemented");
	//}

	@Test
	public void testCalculateHash() {
		Block myBlock = new Block();
		
		//create transcation array
		ArrayList transactions = new ArrayList();
		//add test transactions
		Transaction mine = new Transaction();
		mine.setSender("test0001");
		mine.setContent("good message");
		transactions.add(mine);
		
		Transaction mine2 = new Transaction();
		mine2.setSender("test0002");
		mine2.setContent("better message aaaaaaaaaaaaaaaaaaa");
		transactions.add(mine2);
		
		//set previous block
		Block Previous = new Block();
		byte[] filler = new byte[]{};
		Previous.setPreviousHash(filler);
		myBlock.setPreviousBlock(Previous);
		
		myBlock.setTransactions(transactions);
		myBlock.calculateHash();
	}

}
