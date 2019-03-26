import java.util.ArrayList;
import java.util.LinkedList;

public class Hill {

	public static void Hill(Node n, String goal,String[] forbid) {
		boolean stop = false;
		Node fringe = n;
		fringe.manhat(goal);
		LinkedList<Node> expanded = new LinkedList<Node>();
		
		//check if n is goal
		if(n.getDigit().equals(goal)) {
			System.out.println(n.getDigit());
			System.out.println(n.getDigit());
			return;
		}
		
		//generate first lot of kids, add to fringe.

		while(!stop && expanded.size()<1000) {
			n.generatekids(forbid);
			expanded.add(n);

			for(Node m : n.getChildren()){
				m.manhat(goal);
				if(m.getMan()<=fringe.getMan()) {
					fringe=m;
				}
			}
			if(n.getDigit().equals(goal)) {
				printExp(expanded);
				printExp(expanded);
				return;
			}
			if(fringe.equals(n)) {
				System.out.println("No solution found.");
				stop=true;
			}
			n=fringe;
		}

		printExp(expanded);
		return;
		//this is where while loop will start

		
	}
	
	public static void printExp(LinkedList<Node> expanded) {
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
