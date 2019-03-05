import java.util.ArrayList;

public class Blockchain {
    private Block head;
    private ArrayList<Transaction> pool;
    private int length;

    private final int poolLimit = 3;

    public Blockchain() {
        pool = new ArrayList<>();
        length = 0;
    }

    // getters and setters
    public Block getHead() { return head; }
    public ArrayList<Transaction> getPool() { return pool; }
    public int getLength() { return length; }
    public void setHead(Block head) { this.head = head; }
    public void setPool(ArrayList<Transaction> pool) { this.pool = pool; }
    public void setLength(int length) { this.length = length; }

    // add a transaction
    public int addTransaction(String txString) {
    	//Check valid format
    	if(txString.matches("tx|[a-z]{4}[0-9]{4}|%70s|\n")){
    		Transaction Temp = new Transaction();
    		Temp.setSender(txString.substring(3,7));
    		Temp.setContent(txString.substring(7,73));
    		if(pool.size()<poolLimit){
    			pool.add(Temp);
    			return 1;
    		}else{
    			pool.add(Temp);
    			Block newBlock = new Block();
    			newBlock.setTransactions(pool);
    			setHead(newBlock);
    			length++;
    			pool.clear();
    			return 2;
    		}
    		
    	}else{
    		return 0;
    	}
        
    }

    public String toString() {
        String cutOffRule = new String(new char[81]).replace("\0", "-") + "\n";
        String poolString = "";
        for (Transaction tx : pool) {
            poolString += tx.toString();
        }

        String blockString = "";
        Block bl = head;
        while (bl != null) {
            blockString += bl.toString();
            bl = bl.getPreviousBlock();
        }

        return "Pool:\n"
                + cutOffRule
                + poolString
                + cutOffRule
                + blockString;
    }

    // implement helper functions here if you need any.
}