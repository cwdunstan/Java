import java.util.*;

public class IDS {
	
	public static LinkedList<Node> expanded = new LinkedList<Node>();
	public static LinkedList<Node> explored = new LinkedList<Node>();
	public static boolean found = false;
	public static int depth =0;
	
	public static void IDS(Node n, String goal,String[] forbid) {
		//base case
		expanded.add(n);
		explored.add(n);
		if(Integer.parseInt(n.getDigit())==Integer.parseInt(goal)){
			System.out.println("Start = Solution");
			return;
		}
		else{
			n.generatekids(forbid);
			depth++;
			runIDS(n, goal,forbid);


		}
	}
	
	public static void runIDS(Node n, String goal, String[] forbid){

		while(!found && explored.size()<10000) {
			runDFS(n,goal,explored);
			depth++;
			expanded.clear();
			newLayer(n,forbid,depth);
		}
			
			for(Node m : explored){
				System.out.print(m.getDigit());
				if(explored.getLast()!=m) {
				System.out.print(",");
				}
			}
			System.out.println();
		
	}
			
		
	
	
	public static  void runDFS(Node n,String goal,LinkedList<Node> explored)
	{
		if(!found){
		if(Integer.parseInt(n.getDigit())==Integer.parseInt(goal)){
			LinkedList<Node> tempres = new LinkedList<Node>();
			explored.add(n);
			found=true;
			while(!n.isStart()) {
				tempres.add(n);
				n=n.getParent();
			}
			tempres.add(n);
			for(int i=tempres.size()-1;i>=0;i--) {
				System.out.print(tempres.get(i).getDigit());
				if(i>0) {
					System.out.print(",");
				}
			}
			System.out.println();
			return;
		}
		
			explored.add(n);
			List<Node> children = n.getChildren();
			n.visited=true;
			for (int i = 0; i < children.size(); i++) {
				Node t =children.get(i);
				if(t!=null && !t.visited && explored.size()<1000)
				{
					runDFS(t, goal,explored);
				}
			
		}
		}
	}
	
	public static void newLayer(Node n, String[] forbid, int depth){
		n.visited=false;
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addAll(n.getChildren());
			while(!queue.isEmpty()){
				Node temp = queue.pop();
				temp.visited=false;
				if(shouldadd(temp,expanded)){
				expanded.add(temp);
				if(temp.getDepth()<depth){
					temp.generatekids(forbid);
					queue.addAll(temp.getChildren());
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
	
	


