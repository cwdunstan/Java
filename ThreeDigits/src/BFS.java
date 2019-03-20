import java.util.*;

public class BFS {
	
	public static void BFS(Node n, String goal,String[] forbid) {
		
		LinkedList<Node> fringe = new LinkedList<Node>();
		LinkedList<Node> expanded = new LinkedList<Node>();
		
		//is first node a goal?
		if(Integer.parseInt(n.getDigit())==Integer.parseInt(goal)) {
			return;
		}
		//it wasnt a goal
		else{
			//add to expanded, generate kids and add them to fringe
			
			n.generatekids(forbid);
			expanded.add(n);
			fringe.addAll(n.getChildren());
			//iterate until fringe is empty
			while(!fringe.isEmpty() && expanded.size()<1000) {
				Node tempn = fringe.poll();
				//found the goal node
				if(Integer.parseInt(tempn.getDigit())==Integer.parseInt(goal)) {
					expanded.add(tempn);
					printPaths(tempn,expanded);
					return;
				}else {
					tempn.generatekids(forbid);
					if(shouldadd(tempn,expanded)) {
						expanded.add(tempn);
						fringe.addAll(tempn.getChildren());

					}	
				}
			}
			System.out.println("No solution found.");
			return;
			
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
	
	public static void printPaths(Node winner, LinkedList<Node> expanded) {
		//PRINT WINNING PATH//
		LinkedList<String> tempstore = new LinkedList<String>();
		while(!winner.isStart()) {
			tempstore.add(winner.getDigit());
			winner=winner.getParent();
		}
		tempstore.add(winner.getDigit());
		for(int i=tempstore.size()-1;i>=0;i--) {
			System.out.print(tempstore.get(i));
			if(i>0) {
				System.out.print(",");
			}else {
				System.out.print("\n");
			}
		}
		System.out.flush();
		
		
		//PRINT FULL EXPANDED//
		for(int i=0;i<expanded.size();i++) {
			System.out.print(expanded.get(i).getDigit());
			if(i<expanded.size()-1) {
				System.out.print(",");
			}else {
				System.out.print("\n");
			}
		}
		System.out.flush();
	}
}
