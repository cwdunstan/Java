import java.util.*;

public class BFS {

	public BFS(Tree myTree, List<Integer> forbidden, int goal) {
		
		//initialize Data structures
		LinkedList<Tree.Node> fringe = new LinkedList<Tree.Node>();
		LinkedList<Tree.Node> expanded = new LinkedList<Tree.Node>();
		
		//add start to fringe
		fringe.add(myTree.getStart());
		//check if it matches goal
		if(myTree.getStart().getDigits()==goal) {
			expanded.add(fringe.poll());
			myTree.getStart().setVisited(true);
			System.out.println("first ones it.");
			return;
		}
		else {
			//spawn first group of children & add to fringe
			myTree.getStart().generateChildren(forbidden);
			fringe.addAll(myTree.getStart().getChildren());
			//begin the real stuff
			while(!fringe.isEmpty() && expanded.size()<1000) {
				Tree.Node temp = fringe.poll();

						if(temp.getDigits()==goal) {
						expanded.add(temp);
						LinkedList<Tree.Node> sol = new LinkedList<Tree.Node>();
						while(temp.getParent()!=null) {
							sol.add(temp);
							temp=temp.getParent();
						}
						sol.add(temp);
						for(int i=sol.size()-1;i>=0;i--) {
							System.out.print(sol.get(i).getDigits());
							if(i>0) {
								System.out.print(",");
							}else {
								System.out.print("\n");
							}
						}
						System.out.flush();
						for(int j=0;j<expanded.size();j++) {
							System.out.print(expanded.get(j).getDigits());
							if(j<expanded.size()-1) {
								System.out.print(",");
							}else {
								System.out.print("\n");
							}
						}
						System.out.flush();
						return;
					}else {
						temp.setVisited(true);
						temp.generateChildren(forbidden);
						boolean shouldadd = true;
						for(int i = 0;i<expanded.size();i++) {
							if(expanded.get(i).getDigits()==temp.getDigits()) {
								List<Tree.Node> tempkids = expanded.get(i).getChildren();
								boolean equal = true;
								for(int j=0;j<tempkids.size();j++) {
									if(!(tempkids.get(j).getDigits() == temp.getChildren().get(j).getDigits())) {
									equal=false;
								}
							}
							if(equal) {
								shouldadd=false;
							}
						
						}
					}
						if(shouldadd) {
					fringe.addAll(temp.getChildren());
				}
						expanded.add(temp);
			}
			}
			System.out.println("No Solution Found.");
			return;
		}
		
	}
}
