import java.util.*;

public class BFS {
	
	public static void BFS(Node n, int goal,ArrayList<Integer> forbid) {
		
		LinkedList<Node> fringe = new LinkedList<Node>();
		LinkedList<Node> expanded = new LinkedList<Node>();
		
		//is first node a goal?
		if(n.getDigit()==goal) {
			System.out.println(n.getDigit());
			System.out.println(n.getDigit());
			return;
		}
		//it wasnt a goal
		else{
			//add to expanded, generate kids and add them to fringe
			expanded.add(n);
			n.generatekids(forbid);
			fringe.addAll(n.getChildren());
			//iterate until fringe is empty
			while(!fringe.isEmpty() && expanded.size()<1000) {
				Node tempn = fringe.poll();

				//found the goal node
				if(tempn.getDigit()==goal) {
					expanded.add(tempn);
					printPaths(tempn,expanded);
					return;
				}else {
					expanded.add(tempn);
					tempn.generatekids(forbid);
					fringe.addAll(tempn.getChildren());
				}
			}
			System.out.println("No solution found.");
			return;
			
		}

		
	}
	
	public static void printPaths(Node winner, LinkedList<Node> expanded) {
		//PRINT WINNING PATH//
		LinkedList<Integer> tempstore = new LinkedList<Integer>();
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
