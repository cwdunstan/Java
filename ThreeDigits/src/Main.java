import java.io.*;
import java.util.*;


public class Main {
	

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
	   
	for(int i=0;i<values.size();i++) {
		System.out.println(values.get(i));
	}

	 
  
	  
	  switch(choice) {
	  case "A":
		 // A
		  break;
	  case "B":
		  //BFS
		  break;
	  case "D":
		  //DFS
		  break;
	  case "I":
		  //IDS
		  break;
	  case "G":
		  //Greedy
		  break;
	  case "H":
		  //Hill
		  break;
	  }
	  
	  
	  }
	
}
