import java.util.ArrayList;

public class Blockchain {
	//latest block of chain
    private Block head;
    //uncommited transactions
    private ArrayList<Transaction> pool;
    //chain length
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
        // TODO: implement you code here.
    	//test transaction validity.
    	Transaction tempTrans = transTest(txString);
    	if(tempTrans == null){
    		return 0;
    	}
    	else{
    		//pool still has room
    		if(pool.size() < poolLimit-1){
    			pool.add(tempTrans);
    			return 1;
    		}
    		else{
    			pool.add(tempTrans);
    			Block newBlock = new Block();
    			ArrayList<Transaction> poolCopy = new ArrayList<Transaction>(pool);
    			newBlock.setTransactions(poolCopy);
    			if(this.head==null){
        			newBlock.setPreviousBlock(null);
        			newBlock.setPreviousHash(new byte[32]);
    			}
    			else{
        			newBlock.setPreviousBlock(this.head);
        			newBlock.setPreviousHash(this.head.calculateHash());
    			}
    			this.head = newBlock;
    			length++;
    			pool.clear();
    			
    			return 2;
    		}
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
    //check content is 70 chars
    public boolean checkContent(String content) {
    	int charcount =0;
    	Boolean valid = true;
    	for(int i=0;i<content.length();i++){
    		char temp = content.charAt(i);
    		if(temp !=' '){
    			charcount++;
    		}
    		if(temp =='|'){
    			valid=false;
    		}
    	}
    	if(charcount<71 && valid == true){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    //check & convert valid transaction
    public Transaction transTest(String input){
    	if(input.length()>11){
    		
    		if(input.substring(0, 12).matches("tx\\|[a-z]{4}[0-9]{4}\\|") && checkContent(input.substring(12))){
    			Transaction validTrans = new Transaction();
    			validTrans.setSender(input.substring(3,11));
    			validTrans.setContent(input.substring(12));
    			return validTrans;
    		}
    	}
    	return null;
    }  
}