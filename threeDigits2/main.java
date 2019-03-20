package threeDigits2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main (String[] args) {
		//Parameter Check
		if(args.length<2) {
			System.out.println("Incorrect input. Try again.");
			System.exit(0);
		}
		  //ALGORITHM CHOICE
		  String choice = args[0];
		  //FILE PROVIDED 
		   File file = new File(args[1]);
		   //READ FILE
		   Scanner inputFile;
		   //ArrayList<Integer> values = new ArrayList<Integer>();
		  // List<Integer> forbidden = new ArrayList<Integer>();
		   try {
			inputFile = new Scanner(file);
			   while (inputFile.hasNext()){
				    String next = inputFile.next();
				    String[] temp = next.split(",");
				    for(int i=0;i<temp.length;i++) {
				    	System.out.println(temp[i]);
				    	//values.add(Integer.valueOf(temp[i]));
				    }
				   }
		   	} 	
		   catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
