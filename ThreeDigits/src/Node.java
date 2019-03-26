import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Node> children = new ArrayList<Node>();
    private Node parent = null;
    private String digit;
    private int lastchange;
    private int depth=0;
    private int man=0;
    boolean visited;

    public Node(String start) {
        this.digit = start;
        this.visited=false;
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
    
    public void setMan(int man) {
        this.man = man;
    }
    
    public int getMan() {
        return this.man;
    }

    public int getChange() {
    	return this.lastchange;
    }
    
    public void addChild(String digit, int change) {
        Node child = new Node(digit);
        child.setParent(this);
        child.lastchange=change;
        this.children.add(child);
        child.depth=this.depth+1;
    }
    
    public int getDepth(){
    	return this.depth;
    }

    public String getDigit() {
        return this.digit;
    }

    public boolean isStart() {
        return (this.parent == null);
    }

    public boolean hasChildren() {
        return (this.children.size() == 0);
    }
    
    public void manhat(String b){
    	char[] tempA = this.getDigit().toCharArray();
    	char[] tempB = b.toCharArray();
    	int tempres =0;
    	for(int i=0;i<3;i++){
    		int tempAI = Integer.parseInt(String.valueOf(tempA[i]));
    		int tempBI = Integer.parseInt(String.valueOf(tempB[i]));
    		tempres += Math.abs(tempAI-tempBI);
    	}
    	this.man=tempres;
    	
    }
    
    public void generatekids(String[] forbid) {
    	//convert digit to single chars
    	char[] digits = this.digit.toCharArray();
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
    				if(forbid!=null) {
    					for(String n : forbid) {
    						if(Integer.parseInt(n)==toAdd) {
    							tempadd=false;
    						}	
    					}
    				}
    				if(tempadd) {
    					String tempcha = String.valueOf(toAdd);
    					if(tempcha.length()<3) {
    						while(tempcha.length()<3) {
    							tempcha ='0'+tempcha;
    						}
    					}
    					this.addChild(String.valueOf(tempcha),i);
    				}
    			}
    			//then adding 1
    			if(temp<9) {
    				boolean tempadd = true;
    				digits[i]=(char)((temp+1)+'0');
    				int toAdd=Integer.parseInt(String.valueOf(digits));
    				digits[i]=(char)((temp)+'0');
    				if(forbid!=null) {
    					for(String n : forbid) {
    						if(Integer.parseInt(n)==toAdd) {
    							tempadd=false;
    						}	
    					}
    				}
    				if(tempadd) {
    					String tempcha = String.valueOf(toAdd);
    					if(tempcha.length()<3) {
    						while(tempcha.length()<3) {
    							tempcha ='0'+tempcha;
    						}
    					}
    					this.addChild(String.valueOf(tempcha),i);
    				}
    			}
    			
    		}
    	}
    	
    	
    }
}