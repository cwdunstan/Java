import java.io.*;
import java.util.*;


public class Main {
	
	public static int[] readFile(String file){
	     try{
	       BufferedReader fr= new BufferedReader(new FileReader(file));
	       String line;
	       int [] digits= new int[5];
	       int i=0;
	       while((line=fr.readLine())!=null) {
	    	   if(i!=2) {
	    		   digits[i]= Integer.parseInt(line);
	    	   }else {
	    		   String [] temp = line.split(",");
	    		   for(int j=2;j<5;j++) {
	    			   digits[j]=Integer.parseInt(temp[j-2]);
	    		   }
	    	   }
	    	   i++;
	       }
	       return digits;
	       
	     }
	     catch(IOException e){
	    	System.out.println(e);
	     }
		return null;
	  }

	public static void main (String[] args) {
	  if (args.length==0){
	     System.out.println("Bad Filename.");
	     System.exit(0);
	   }
	  //ALGORITHM CHOICE
	  String choice = args[0];
	  //VALUES PROVIDED BY USER	  
	   File file = new File(args[1]);
	   Scanner inputFile;
	   ArrayList<Integer> values = new ArrayList<Integer>();
	   List<Integer> forbidden = new ArrayList<Integer>();
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
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   if(values.size()>2) {
		   forbidden = values.subList(2, values.size());
	   }
	 
	  //pass start value to new tree
	  Tree myTree = new Tree(values.get(0));	  
	  
	  switch(choice) {
	  case "A":
		 // A(myTree,values[1]);
		  break;
	  case "B":
		  BFS myBFS = new BFS(myTree,forbidden,values.get(1));
		  break;
	  case "D":
		  //DFS(myTree,values[1]);
		  break;
	  case "I":
		  //IDS(myTree,values[1]);
		  break;
	  case "G":
		  //Greedy(myTree,values[1]);
		  break;
	  case "H":
		  //Hill(myTree,values[1]);
		  break;
	  }
	  
	  
	  }
	
}
