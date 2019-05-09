import java.io.*;
import java.util.*;

public class MyClassifier {

	
	public static void main (String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		//Validate inputs provided
		if(!validArgs(args)){
			return;
		}
		//generate entry lists from files
		ArrayList<entry> training =generate(args[0], true);
		ArrayList<entry> testing =generate(args[1], false);
		
		//check values provided
		if(training.size() == 0 || testing.size()==0){
			System.out.println("Empty or invalid file.");
			return;
		}
		//check both the same number of attributes
		if(training.get(0).getValues().size()!=testing.get(0).getValues().size()){
			System.out.println("Inconsistent attributes.");
			return;
		}
		
		//call algorithm, store result in arraylist result
		ArrayList<String> result = algoHandler(training,testing,args[2]);
		
		//calls fold validation
		folds myfold = new folds();
		myfold.folds(10, training, args[2]);
		System.out.println();
		return;
	}
	
	//validate arguments provided
	public static boolean validArgs(String[] args){
		//check length
		 if (args.length!=3){
			 System.out.println("Wrong number of arguments.");
			 return false;
		 }
			  
		 //check for valid file paths
		 for(int i=0;i<2;i++){
			File tmpDir = new File(args[i]);
			boolean exists = tmpDir.exists();
			if(!exists){
				System.out.println("Invalid filepath.");
				return false;
			}	
		}
		//check valid algorithm name
		 if(!args[2].matches("NB") && !args[2].matches("[\\d]NN")){
			 System.out.println("Invalid algorithm.");
			return false;
		 }
		 
		return true;
	}
	
	public static ArrayList<entry> generate(String filepath, boolean training){
		File file = new File(filepath);
		Scanner inputFile;
		ArrayList<entry> inputs = new ArrayList<entry>();
		try {
			inputFile = new Scanner(file);
			//populate values
			while (inputFile.hasNext()){
				String[] temp = inputFile.next().split(",");
				ArrayList<Double> invalues = new ArrayList<>();
				//need to take class value from end
				if(!training){
					for(String s : temp){
						invalues.add(Double.parseDouble(s));
					}
					entry tempEntry = new entry(invalues);
					inputs.add(tempEntry);
				}
				//no class data
				else{
					for(int i=0;i<temp.length-1;i++){
						invalues.add(Double.parseDouble(temp[i]));
					}
					entry tempEntry = new entry(invalues);
					tempEntry.setMyClass(temp[temp.length-1]);
					inputs.add(tempEntry);
				}
			   
			  }

		  }catch(FileNotFoundException e){
			  System.out.println("Invalid File.");
		  }
		return inputs;
		
	}

	public static ArrayList<String> algoHandler(ArrayList<entry> training, ArrayList<entry> testing, String arg){
		//call naive bias
		if(arg.matches("NB")){
			knn myCall = new knn(training,testing,5);
			ArrayList<String> result = myCall.classify();
			myCall.printResult();
			return result;
		}
		
		//call knn
		if(arg.matches("[\\d]NN")){
			int k=Character.getNumericValue(arg.charAt(0));
			if (k<1){
				System.out.println("Invalid number of neighbours.");
				return null;
			}
			knn myCall = new knn(training,testing,k);
			ArrayList<String> result = myCall.classify();
			myCall.printResult();
			return result;
		}
		return null;
	}



}
