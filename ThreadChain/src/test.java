import static org.junit.Assert.*;

import org.junit.Test;

public class test {

	@Test
	public void test() throws Exception {
		addOneTransactionTest();
	}
	@Test
	public void addOneTransactionTest() throws Exception {
	    Blockchain bc = new Blockchain();
	    boolean returnValue = bc.addTransaction("tx|test1111|1");

	    assertTrue(1 ==  bc.getPool().size() && returnValue);
	}

}
