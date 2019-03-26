import java.util.ArrayList;
import java.util.LinkedList;

public class A{

	public static void A(Node n, String goal,String[] forbid) {
		
		LinkedList<Node> fringe = new LinkedList<Node>();
		LinkedList<Node> expanded = new LinkedList<Node>();
		
		//check if n is goal
		if(n.getDigit().equals(goal)) {
			System.out.println(n.getDigit());
			System.out.println(n.getDigit());
			return;
		}
		
		//generate first lot of kids, add to fringe.
		n.generatekids(forbid);
		expanded.add(n);
		for(Node m : n.getChildren()){
			m.manhat(goal);
			m.setMan(m.getMan()+m.getDepth());
			fringe.add(m);
		}
		
		while(!fringe.isEmpty() && expanded.size()<1000) {
			Node temp = null;
			int best = 0;
			for(int i=0;i<fringe.size();i++) {
				if(i==0) {
					temp=fringe.get(i);
					best=temp.getMan();
				}
				if(fringe.get(i).getMan() <= best) {
					temp=fringe.get(i);
					best=temp.getMan();
				}
			}
			//if next choice is a goal
			if(temp.getDigit().equals(goal)) {
				expanded.add(temp);
				LinkedList<String> tempstore = new LinkedList<String>();
				while(!temp.isStart()) {
					tempstore.add(temp.getDigit());
					temp=temp.getParent();
				}
				tempstore.add(temp.getDigit());
				for(int i=tempstore.size()-1;i>=0;i--) {
					System.out.print(tempstore.get(i));
					if(i>0) {
						System.out.print(",");
					}else {
						System.out.print("\n");
					}
				}
				System.out.flush();
				printExp(expanded);
				return;
			}
			temp.generatekids(forbid);
			if(shouldadd(temp,expanded)) {
				expanded.add(temp);
				fringe.remove(temp);
			for(Node m : temp.getChildren()){
				m.manhat(goal);
				m.setMan(m.getMan()+m.getDepth());
				fringe.add(m);
			}
			}
		}
		System.out.println("No solution found.");
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
