import java.util.LinkedList;

public class DFS {

	public static void DFS(Node n, String goal,String[] forbid) {
		
		LinkedList<Node> expanded = new LinkedList<Node>();

		
		if(Integer.parseInt(n.getDigit())==Integer.parseInt(goal)) {
			return;
		}
		//it wasnt a goal
		else{
			//add to expanded, generate kids and add them to fringe	
			n.generatekids(forbid);
			expanded.add(n);
		}
		
		
	}
	
}
