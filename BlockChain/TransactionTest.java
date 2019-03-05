import static org.junit.Assert.*;

import org.junit.Test;

public class TransactionTest extends Transaction {

	Transaction mine = new Transaction();
	Transaction mine2 = new Transaction();
	
	//Test That Appropriate Senders are added, others rejected.
	@Test
	public void testSetSender() {
		
		mine.setSender("test0001");
		assertEquals(mine.getSender(),"test0001");
		mine2.setSender("test002");
		assertEquals(mine2.getSender(),null);
	}
	//Test That Appropriate Contents are added, others rejected.
	@Test
	public void testSetContent() {
		String testContent="This is a valid test message from me.";
		mine.setContent(testContent);
		assertEquals(mine.getContent(),testContent);
		mine2.setContent("this is an invalid | test message.");
		assertEquals(mine2.getContent(),null);
	}

	//@Test
	//public void testGetSender() {
	//	fail("Not yet implemented");
	//}

	//@Test
	//public void testGetContent() {
	//	fail("Not yet implemented");
	//}

	@Test
	public void testToString() {
		mine.setSender("test0001");
		String testContent="This is a valid test message from me.";
		mine.setContent(testContent);
		System.out.println(mine.toString());
	}

}
