import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

public class folds {
	
	public static ArrayList<entry> mytraining;
	public static int numfolds;
	
	// k = number of folds, training = training data, args = algorithm
	public static void folds(int k, ArrayList<entry> training, String args) {
		mytraining = training;
		numfolds=k;
		//training data size
		int n = training.size();
		
		//pass to fold generating function
		ArrayList<ArrayList<entry>> completeFolds = genFolds(n);
		float avg =0;
		for(int i=0;i<10;i++){
			avg+=calcAccuracy(completeFolds);
			System.out.println(i);
		}
		System.out.println(avg/10);
		
		
		
		
		//write to csv file
		try {
			PrintWriter writer = new PrintWriter("pima-folds.csv", "UTF-8");
			int i=1;
			for(ArrayList<entry> L : completeFolds) {
				writer.println("fold"+i);
				i++;
				for(entry e : L) {
					for(Double s : e.getValues()) {
						writer.print(s+",");
					}
					writer.println(e.getMyClass());
				}
				writer.println();
			}
			writer.close();

		} catch (FileNotFoundException e1) {
			return;
		} catch (UnsupportedEncodingException e1) {
			return;
		}

		
		return;
	}


	private static ArrayList<ArrayList<entry>> genFolds(int n) {
		
		//separate into yeses and no
		ArrayList<entry> yeses = new ArrayList<entry>();
		ArrayList<entry> noses = new ArrayList<entry>();
		for(entry e : mytraining){
			if(e.getMyClass().equals("yes")){
				yeses.add(e);
			}else{
				noses.add(e);
			}
		}
		//how many fit into each fold, and how many will need random dist
		int yesperfold = yeses.size()/numfolds;
		int yesleft = yeses.size()%numfolds;
		int noperfold = noses.size()/numfolds;
		int noleft = noses.size()%numfolds;
		
		//adding folds
		ArrayList<ArrayList<entry>> totalFolds = new ArrayList<>();
		Random rand = new Random();
		
		for(int i=0;i<numfolds;i++) {
			//create this fold
			ArrayList<entry> fold = new ArrayList<>();
			
			//randomly pick yeses and nos 
			for(int j=0;j<yesperfold;j++) {
				int tempindex=rand.nextInt(yeses.size());
				fold.add(yeses.get(tempindex));
				yeses.remove(tempindex);
			}
			for(int j=0;j<noperfold;j++) {
				int tempindex=rand.nextInt(noses.size());
				fold.add(noses.get(tempindex));
				noses.remove(tempindex);
			}
			totalFolds.add(fold);
		}
		
		//account for uneven folds
		int foldsize = yesperfold+noperfold;
		while(yesleft!=0) {
				int tempindex=rand.nextInt(totalFolds.size());
				if(totalFolds.get(tempindex).size()==foldsize) {
					totalFolds.get(tempindex).add(yeses.get(0));
					yeses.remove(0);
				}
				//update fold size
				int tempsize =foldsize+1;
				boolean update = true;
				for(ArrayList<entry> fod : totalFolds) {
					if(fod.size()!=tempsize) {
						update=false;
					}	
				}
				if(update) {
					foldsize=tempsize;
				}
			yesleft =yeses.size()%numfolds;
		}
		
		while(noleft!=0) {
			int tempindex=rand.nextInt(totalFolds.size());
			if(totalFolds.get(tempindex).size()==foldsize) {
				totalFolds.get(tempindex).add(noses.get(0));
				noses.remove(0);
			}
			//update fold size
			int tempsize =foldsize+1;
			boolean update = true;
			for(ArrayList<entry> fod : totalFolds) {
				if(fod.size()!=tempsize) {
					update=false;
				}	
			}
			if(update) {
				foldsize=tempsize;
			}
		noleft =noses.size()%numfolds;
	}

		return totalFolds;
	}

	private static double calcAccuracy(ArrayList<ArrayList<entry>> completeFolds){
		float total=0;
		for(int i=0;i<10;i++){
			ArrayList<entry> tempTest = completeFolds.get(i);
			ArrayList<ArrayList<entry>> testFolds = new ArrayList<ArrayList<entry>>();
			testFolds.addAll(completeFolds);
			testFolds.remove(i);
			ArrayList<entry> toTest = merge(testFolds);
			knn myCall = new knn(toTest,tempTest,1);
			ArrayList<String> result = myCall.classify();
			//check percentage
			float counter =0;
			for(int j=0;j<result.size();j++){
				if(result.get(j).equals(tempTest.get(j).getMyClass())){
					counter++;
				}
			}
			total+=(counter/result.size()*100);
			
		}		
		return total/10;
	}
	
	private static ArrayList<entry> merge(ArrayList<ArrayList<entry>> splitfolds){
		ArrayList<entry> tempTest = new ArrayList<>();
		tempTest.addAll(splitfolds.get(0));
		for(int i=1;i<9;i++){
			tempTest.addAll(splitfolds.get(i));
		}		
		return tempTest;
	}
}
