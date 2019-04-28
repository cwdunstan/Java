import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class fold {
	
	//split training set into K folds 
	public static ArrayList<ArrayList<entry>> fold(int k, ArrayList<entry> training) {
		int n = training.size();
		
		ArrayList<entry> yeses = new ArrayList<entry>();

		ArrayList<entry> noses = new ArrayList<entry>();

		
		//separate into yes and no
		for(entry e : training){
			if(e.getMyClass().equals("yes")){
				yeses.add(e);
			}else{
				noses.add(e);
			}
		}
		
		int numyes = yeses.size()/k;
		int randyes = yeses.size()%k;
		int numno = noses.size()/k;
		int randno = noses.size()%k;
		
		//create fold structure
		ArrayList<ArrayList<entry>> folds = new ArrayList<>();
		Random rand = new Random();

		//iterate, a filling each fold
		for(int i=0;i<k;i++) {
			ArrayList<entry> toadd = new ArrayList<>();
			for(int j=0;j<numyes;j++) {
				int tempindex=rand.nextInt(yeses.size());
				toadd.add(yeses.get(tempindex));
				yeses.remove(tempindex);
			}
			for(int j=0;j<numno;j++) {
				int tempindex=rand.nextInt(noses.size());
				toadd.add(noses.get(tempindex));
				noses.remove(tempindex);
			}
			folds.add(toadd);
		}
		
		//account for uneven
		if(randyes!=0) {
			int foldsize=numyes+numno;
			while(!yeses.isEmpty()) {
				int tempindex=rand.nextInt(folds.size());
				if(folds.get(tempindex).size()==foldsize) {
					folds.get(tempindex).add(yeses.get(0));
					yeses.remove(0);
				}
			}
		}
		
		if(randno!=0) {
			int foldsize=numyes+numno;
			while(!noses.isEmpty()) {
				int tempindex=rand.nextInt(folds.size());
				if(folds.get(tempindex).size()==foldsize) {
					folds.get(tempindex).add(noses.get(0));
					noses.remove(0);
				}
			}
		}
		
		accuracy(folds);
		return folds;

				
		
	}
	
	public static double accuracy(ArrayList<ArrayList<entry>> fold) {
		
		float[] store = new float[fold.size()];
		
		for(int k=0;  k<fold.size(); k++) {

			ArrayList<ArrayList<entry>> temp = new ArrayList<>();
			temp.addAll(fold);
			//separate test fold
			ArrayList<entry> testFold = temp.remove(k);
			//store expected vals
			ArrayList<String> answers = new ArrayList<String>();

			//combine remaining folds
			ArrayList<entry> testTraining = temp.get(0);
			for(int i=1;i<temp.size();i++) {
				testTraining.addAll(temp.get(i));
			}
			
			NB myCall = new NB(testTraining,testFold);
			ArrayList<String> result = myCall.classify();
			
			int correct =0; 
			int wrong =0;
			
			for(int i=0;i<testFold.size();i++) {
				if(result.get(i).equals(testFold.get(i).getMyClass())) {
					correct++;
				}else {
					wrong++;
				}
			}
			
			store[k]=((float)(correct)/result.size());
		}
		
		float toreturn = store[0];
		for(int i=1;i<fold.size();i++) {
			toreturn = toreturn + store[i];
		}
		toreturn=(toreturn/fold.size())*100;
		System.out.println(toreturn);

		return toreturn;
		
	}
}
