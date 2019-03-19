import java.util.*;

//***************CURRENTLY MATCHING OUTPUT EXAMPLE, BUT NEED TO ACCOUNT FOR CYCLE SKIPPING********************//
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
			expanded.add(fringe.poll());
			fringe.addAll(myTree.getStart().getChildren());
			//begin the real stuff
			while(!fringe.isEmpty() && expanded.size()<1000) {
				Tree.Node temp = fringe.poll();
						//SOLUTION WAS FOUND
						if(temp.getDigits()==goal) {
						expanded.add(temp);
						LinkedList<Tree.Node> sol = new LinkedList<Tree.Node>();
						//TRACE BACK TO START
						while(temp.getParent()!=null) {
							sol.add(temp);
							temp=temp.getParent();
						}
						sol.add(temp);
						//PRINT OUT PATH
						for(int i=sol.size()-1;i>=0;i--) {
							System.out.print(sol.get(i).getDigits());
							if(i>0) {
								System.out.print(",");
							}else {
								System.out.print("\n");
							}
						}
						System.out.flush();
						//PRINT OUT EXPANDED ORDER
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
						//SOLUTION WAS NOT FOUND
						temp.generateChildren(forbidden);
						expanded.add(temp);
						temp.setVisited(true);
						fringe.addAll(temp.getChildren());
					}
			}
			System.out.println("No Solution Found.");
			return;
		}
		
	}
}
