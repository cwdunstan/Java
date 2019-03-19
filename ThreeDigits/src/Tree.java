import java.util.ArrayList;
import java.util.List;

public class Tree {
    private Node start;
    public Tree(int startDigit) {
    	
        start = new Node();
        start.digit = startDigit;
        start.children = new ArrayList<Node>();
        start.digChanged=4;
    }

    public static class Node {
    	private boolean visited = false;
        private int digit;
        private Node parent;
        private List<Node> children;
        private int digChanged;
       
        public void setDigit(int digits) {
        	this.digit=digits;
        }
        public int getDigits() {
        	return digit;
        }
        public void setParent(Node pNode) {
        	this.parent=pNode;
        }
        public Node getParent() {
        	return this.parent;
        }
        public Node addChild(Node cNode) {
        	children.add(cNode);
        	cNode.setParent(this);
        	return cNode;
        }
        public List<Node> getChildren(){
        	return children;
        }
        
        public void setVisited(Boolean b) {
        	this.visited=b;
        }
        public boolean isVisited() {
        	return this.visited;
        }
        public void setChanged(int n) {
        	this.digChanged = n;
        }
        public int getChanged() {
        	return this.digChanged;
        }
        
        
        
        //GENERATE CHILDREN METHOD
        public void generateChildren(List<Integer> forbidden) {
        	//initialize children list
        	this.children = new ArrayList<Node>();
        	//convert digit to string of chars
        	String number = String.valueOf(this.getDigits());
        	char[] digits = number.toCharArray();
        	
        	//iterate through highest order down
        	for(int i=0;i<digits.length;i++) {
        		//make sure we're not changing the same number twice
        		if(this.getChanged()!=i) {
        			int single = Character.getNumericValue(digits[i]);
        			//number works
        			if(single>0) {
        				//generate low value
        				boolean tempcheck = true;
        				digits[i]=(char)((single-1)+'0');
        				int tempdown = Integer.parseInt(new String(digits));
        				digits[i]=(char)((single)+'0');
        				//make sure not forbidden
        				for(int j=0;j<forbidden.size();j++) {
        					if(tempdown==forbidden.get(j)) {
        						tempcheck=false;
        					}
        				}
        				//not forbidden values
        				if(tempcheck) {
        					Node tempNode = new Node();
        					tempNode.setDigit(tempdown);
        					tempNode.setChanged(i);
        					this.addChild(tempNode);
        				}
  
        				
        			}
        			if(single<9) {
        				boolean tempcheck = true;
        				//generate high value
        				digits[i]=(char)((single+1)+'0');
        				int tempup = Integer.parseInt(new String(digits));
        				digits[i]=(char)((single)+'0');
        				//make sure not forbidden
        				for(int j=0;j<forbidden.size();j++) {
        					if(tempup==forbidden.get(j)) {
        						tempcheck=false;
        					}
        				}
        				//not forbidden values
        				if(tempcheck) {
        					Node tempNode = new Node();
        					tempNode.setDigit(tempup);
        					tempNode.setChanged(i);
        					this.addChild(tempNode);
        				}

        			}

        		}
        	}
        	 	
        }
        
        
        
        
        
        
        
        
        
        
    }
    
    public Node getStart() {
    	return start;
    }
    
    public void printChildren(Node n) {
  	  for(int j=0;j<n.getChildren().size();j++){
		  System.out.println(n.getChildren().get(j).getDigits());
	  }
    }
    

    
}
