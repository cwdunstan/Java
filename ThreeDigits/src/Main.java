import java.io.*;
import java.util.*;


public class Main {

	public static ArrayList<Integer> forbid = new ArrayList<Integer>();

	public static void main (String[] args) {
	//CHECK PARAMETERS
	  if (args.length==0){
	     System.out.println("Bad Filename.");
	     System.exit(0);
	  }
//****************************READ FILE************************************//
	  //ALGORITHM CHOICE
	  String choice = args[0];
	  //VALUES PROVIDED BY USER	  
	   File file = new File(args[1]);
	   Scanner inputFile;
	   ArrayList<Integer> values = new ArrayList<Integer>();
	   try {
		inputFile = new Scanner(file);
		   while (inputFile.hasNext()){
			    String next = inputFile.next();
			    String[] temp = next.split(",");
			    for(int i=0;i<temp.length;i++) {
			    	values.add(Integer.valueOf(temp[i]));
			    }
		   }
	   	} 	
	   catch (FileNotFoundException e) {
		// couldn't open file
		e.printStackTrace();
	}
//*****************CREATE TREE & CALL ALGORITHM************************************//
	Node start = makeTree(values);
	
	switch(choice) {
	case "B":
		BFS.BFS(start,values.get(1),forbid);
		break;
	
	}
	
	
	
	
}	
	
//**************************TREE CONSTRUCTOR*******************************//
	
	public static Node makeTree( ArrayList<Integer> values) {
		Node startNode = new Node(values.get(0));
		startNode.setChange(4);

		//Generate forbidden list
		if(values.size()>2) {
			for(int i=2;i<values.size();i++) {
				forbid.add(values.get(i));
			}
		}
		//Spawn Children
		return startNode;
	}
}
