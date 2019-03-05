import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.ArrayList;

public class Block {
    private Block previousBlock;
    private byte[] previousHash;
    private ArrayList<Transaction> transactions;

    public Block() { transactions = new ArrayList<>(); }

    // getters and setters
    public Block getPreviousBlock() { return previousBlock; }
    public byte[] getPreviousHash() { return previousHash; }
    public ArrayList<Transaction> getTransactions() { return transactions; }
    public void setPreviousBlock(Block previousBlock) { this.previousBlock = previousBlock; }
    public void setPreviousHash(byte[] previousHash) { this.previousHash = previousHash; }
    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String toString() {
        String cutOffRule = new String(new char[81]).replace("\0", "-") + "\n";
        String prevHashString = String.format("|PreviousHash:|%65s|\n",
                Base64.getEncoder().encodeToString(previousHash));
        String hashString = String.format("|CurrentHash:|%66s|\n",
                Base64.getEncoder().encodeToString(calculateHash()));
        String transactionsString = "";
        for (Transaction tx : transactions) {
            transactionsString += tx.toString();
        }
        return "Block:\n"
                + cutOffRule
                + hashString
                + cutOffRule
                + transactionsString
                + cutOffRule
                + prevHashString
                + cutOffRule;
    }

    // to calculate the hash of current block.
    public byte[] calculateHash() {
        // TODO: implement your code here.
    	//empty byte array
    	byte[] hashbyte= new byte[]{};
    	
    	try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(baos);
		
		    	//get previous blocks hash value, then hash using dos.write()
				dos.write(getPreviousHash());
				
		    	//get each transaction in the list
		    	ArrayList<Transaction> toHash = this.getTransactions();
		    	int listSize = toHash.size();
		    	for(int i=0;i<listSize;i++){
		    		//get transaction
		    		String preHash = toHash.get(i).toString();
		    		//convert to accepted format
		    		preHash = "tx"+preHash;
		    		dos.writeUTF(preHash);
		    	}
		    	//convert to tx|sender|content string format
		    	//hash string using dos.writeUTF(string)
		    	//write to data output stream
		    	 byte[] bytes = baos.toByteArray();
				 hashbyte = digest.digest(bytes);
		    		
		} 
    	catch (NoSuchAlgorithmException e) {
    		
			e.printStackTrace();
		}
		 catch (IOException e) {
			 e.printStackTrace();
		 }
    	return hashbyte;
    }

    // implement helper functions here if you need any.
}