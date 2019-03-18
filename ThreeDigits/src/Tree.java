import java.util.ArrayList;
import java.util.List;

public class Tree {
    private Node start;
    public Tree(int startDigit) {
        start = new Node();
        start.digit = startDigit;
        start.children = new ArrayList<Node>();
    }

    public static class Node {
        private int digit;
        private Node parent;
        private List<Node> children;
       
        public void setDigit(int digits) {
        	this.digit=digits;
        }
        public int getDigits() {
        	return digit;
        }
        public void setParent(Node pNode) {
        	this.parent=pNode;
        }
        public Node addChild(Node cNode) {
        	children.add(cNode);
        	cNode.setParent(this);
        	return cNode;
        }
        public List<Node> getChildren(){
        	return children;
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
