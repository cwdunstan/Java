import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class IDS2 {

	public static boolean found = false;
	public static void IDS2(Node n, String goal,String[] forbid) {
		
		LinkedList<Node> expanded = new LinkedList<Node>();

		
		//check goal
		if(n.getDigit().equals(goal)) {
			System.out.println(n.getDigit());
			System.out.println(n.getDigit());
			found=true;
			return;
		}
		int depth=0;
		while(!found && expanded.size()<1000) {
			LinkedList<Node> expandedtemp = new LinkedList<Node>();
			dfs(n,goal,forbid,expandedtemp,depth);
			for(int i=0;i<expandedtemp.size();i++) {
				if(expanded.size()<1000) {
				expanded.add(expandedtemp.get(i));
				}
			}
			depth++;
		}
		if(found) {
			Stack<Node> tempStack = new Stack<Node>();
			Node temp = expanded.getLast();
			while(!temp.isStart()){
				tempStack.push(temp);
				temp = temp.getParent();
			}
			tempStack.push(temp);
			while(!tempStack.isEmpty()){
				Node temp2 = tempStack.pop();
				System.out.print(temp2.getDigit()+",");
			}
			System.out.print("\n");
			System.out.flush();
		}else {
			System.out.println("No solution found.");
		}
		for(int i=0;i<expanded.size();i++) {
			System.out.print(expanded.get(i).getDigit());
			if(i<expanded.size()-1) {
				System.out.print(",");
			}
		}
		System.out.println();
		
	}
	
	public static  void dfs(Node n,String goal, String[] forbid,LinkedList<Node> expanded, int depth)
	{
		if(!found){
		n.generatekids(forbid);
		if(n.getDigit().equals(goal)){
			expanded.add(n);
			found=true;
			return;
		}
		if(shouldadd(n,expanded)){
			expanded.add(n);
			List<Node> children= n.getChildren();
			n.visited=true;
			for (int i = 0; i < children.size(); i++) {
				Node t =children.get(i);
				if(t!=null && !t.visited && expanded.size()<1000 & t.getDepth()<=depth)
				{
					dfs(t, goal,forbid,expanded, depth);
				}
			}
		}
		n.visited=true;
		}
	}
	
	public static boolean shouldadd(Node n, LinkedList<Node> expanded) {
		for(Node temp : expanded) {
			if(temp.getDigit().equals((n.getDigit()))) {
				if(temp.getChildren().size()==n.getChildren().size()) {
					ArrayList<String> temp1 = new ArrayList<String>();
					ArrayList<String> temp2 = new ArrayList<String>();
					for(int i=0;i<temp.getChildren().size();i++) {
							temp1.add(n.getChildren().get(i).getDigit());
							temp2.add(temp.getChildren().get(i).getDigit());
						}
					if(temp1.equals(temp2)) {
						return false;
					}
					}
			
				}
			}
		return true;
		
	}

}
