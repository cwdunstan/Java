import java.io.*;


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
	  
	  int [] values = readFile(args[0]);

	  Tree myTree = new Tree(values[0]);
	 
	  for(int i=2;i<5;i++) {
		  Tree.Node tempNode = new Tree.Node();
		  tempNode.setDigit(values[i]);
		  myTree.getStart().addChild(tempNode);
	  }
	  
	  myTree.printChildren(myTree.getStart());
	  }
	
}
