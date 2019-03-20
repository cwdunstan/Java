import java.io.*;
import java.util.*;


public class ThreeDigits {

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
	   ArrayList<String> inputs = new ArrayList<String>();
	   String[] forbidden = null;
	   try {
			inputFile = new Scanner(file);
			while (inputFile.hasNext()){
			    inputs.add(inputFile.next());
		   }
		   //POPULATE FORBIDDEN LIST  
			if(inputs.size()>2) {
				forbidden = inputs.get(2).split(",");
			}
				
	   }
	   catch (FileNotFoundException e) {
		// couldn't open file
		e.printStackTrace();
	}
//*****************CREATE TREE & CALL ALGORITHM************************************//
	Node start = makeTree(inputs.get(0));
	
	switch(choice) {
	case "B":
		BFS.BFS(start,inputs.get(1),forbidden);
		break;
	
	}
	
	
	
	
}	
	
//**************************TREE CONSTRUCTOR*******************************//
	
	public static Node makeTree(String start) {
		Node startNode = new Node(start);
		startNode.setChange(4);

		//Generate forbidden list
		//Spawn Children
		return startNode;
	}
}
