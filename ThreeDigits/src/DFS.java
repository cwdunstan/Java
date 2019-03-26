import java.util.*;

public class DFS {
	
	public static boolean found = false;

	public static void DFS(Node n, String goal,String[] forbid) {
		
		LinkedList<Node> expanded = new LinkedList<Node>();

		
		if(n.getDigit().equals(goal)) {
			System.out.println(n.getDigit());
			System.out.println(n.getDigit());
			return;
		}
		//it wasnt a goal
		else{
			//add to expanded, generate kids and add them to fringe	

			dfs(n,goal, forbid, expanded);
		}
		if(found){
			//print path
			Stack<Node> tempStack = new Stack<Node>();
			Node temp = expanded.getLast();
			while(!temp.isStart()){
				tempStack.push(temp);
				temp = temp.getParent();
			}
			tempStack.push(temp);
			while(!tempStack.isEmpty()){
				Node temp2 = tempStack.pop();
				System.out.print(temp2.getDigit()+" ");
			}
			System.out.print("\n");
			System.out.flush();
		}
			else{
				System.out.println("No solution found.");
			}

			
			//print expanded
			for(Node b : expanded){
				System.out.print(b.getDigit()+" ");
			}
			System.out.print("\n");
			System.out.flush();
		
		
	}
	
	public static  void dfs(Node n,String goal, String[] forbid,LinkedList<Node> expanded)
	{
		if(!found){
		n.generatekids(forbid);
		if(Integer.parseInt(n.getDigit())==Integer.parseInt(goal)){
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
				if(t!=null && !t.visited && expanded.size()<1000)
				{
					dfs(t, goal,forbid,expanded);
				}
			}
		}
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
