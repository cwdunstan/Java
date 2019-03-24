
public class Greedy {

	public static void Greedy(Node n, String goal,String[] forbid) {
		
		n.generatekids(forbid);
		for(Node m : n.getChildren()){
			System.out.println(m.getDigit()+": "+n.manhat(m));
		}
		
	}
}
