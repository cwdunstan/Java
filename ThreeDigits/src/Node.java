import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Node> children = new ArrayList<Node>();
    private Node parent = null;
    private int digit;
    private int lastchange;

    public Node(int digit) {
        this.digit = digit;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public void setChange(int change) {
        this.lastchange = change;
    }
    
    public Node getParent() {
        return this.parent;
    }

    public int getChange() {
    	return this.lastchange;
    }
    
    public void addChild(int digit, int change) {
        Node child = new Node(digit);
        child.setParent(this);
        child.lastchange=change;
        this.children.add(child);
    }


    public int getDigit() {
        return this.digit;
    }

    public boolean isStart() {
        return (this.parent == null);
    }

    public boolean hasChildren() {
        return (this.children.size() == 0);
    }
    
    public void generatekids(ArrayList<Integer> forbid) {
    	//convert digit to single chars
    	String number = String.valueOf(this.digit);
    	char[] digits = number.toCharArray();
    	//Start iterating
    	for(int i=0;i<digits.length;i++) {
    		//make sure not changing the same digit twice
    		if(this.getChange()!=i) {
    			int temp=Integer.parseInt(String.valueOf(digits[i]));
    			//start by subtracting 1
    			if(temp>0) {
    				boolean tempadd = true;
    				digits[i]=(char)((temp-1)+'0');
    				int toAdd=Integer.parseInt(String.valueOf(digits));
    				digits[i]=(char)((temp)+'0');
    				for(int n : forbid) {
    					if(n==toAdd) {
    						tempadd=false;
    					}
    				}
    				if(tempadd) {
    					this.addChild(toAdd,i);
    				}
    			}
    			//then adding 1
    			if(temp<9) {
    				boolean tempadd = true;
    				digits[i]=(char)((temp+1)+'0');
    				int toAdd=Integer.parseInt(String.valueOf(digits));
    				digits[i]=(char)((temp)+'0');
    				for(int n : forbid) {
    					if(n==toAdd) {
    						tempadd=false;
    					}
    				}
    				if(tempadd) {
    					this.addChild(toAdd,i);
    				}
    			}
    			
    		}
    	}
    	
    	
    }
}