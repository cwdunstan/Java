import java.util.ArrayList;

public class NB {

	private static  ArrayList<entry> train;
	private static ArrayList<entry> test;
	private static ArrayList<String> result;
	
	public NB(ArrayList<entry> training, ArrayList<entry> testing){
		this.train = training;
		this.test = testing;
		this.result = new ArrayList<>();
		
	}
	
	public static void classify(){
		//split into yes and no
		 ArrayList<entry> yeses = new ArrayList<entry>();
		ArrayList<entry> noses = new ArrayList<entry>();
		for(entry e : train){
			if(e.getMyClass().equals("yes")){
				yeses.add(e);
			}else{
				noses.add(e);
			}
		}
		//convert to 2d Matrix (slopy i know)
		double[][] yesmatrix = translate(yeses);
		double[][] nomatrix = translate(noses);
		for(entry e:test){
			if(pdf(e,yesmatrix) >= pdf(e,nomatrix)){
				result.add("yes");
			}else{
				result.add("no");
			}
		}
		for(String s : result){
			System.out.println(s);
		}
			
		return;
		
	}
	
	private static double pdf(entry e, double[][] matrix) {
		// TODO Auto-generated method stub
		int k = e.getValues().size();
		//calculate means and SD for each entry in row, stored in double lists
		double[] meanstore = calcMean(matrix, k);
		double[] sdstore = calcSD(matrix,meanstore,k);
		
		//calcualte deinsity values
		double[] densitystore = new double[k];
		for(int i=0;i<k;i++){
			double sd = sdstore[i];
			double mean = meanstore[i];
			densitystore[i] = calcFunc(mean,sd,e.getValues().get(i));
		}
		double start = densitystore[0];
		for(int i=1;i<k;i++){
			double temp = start;
			start = temp * densitystore[i];
		}
		
		return start;
	}

	public static double[] calcMean(double[][] matrix, int k){
		//for each row
		double[] meanstore = new double[k];
		int total =0;
		for(double[] d : matrix){
			for(int i=0;i<meanstore.length;i++){
				meanstore[i]+=d[i];
			}
			total=total+1;
		}
		
		for(int i=0;i<meanstore.length;i++){
			double temp = meanstore[i];
			meanstore[i]=(temp/total);
		}
		return meanstore;
		
	}
	
	public static double[] calcSD(double[][] matrix, double[] means, int k){
		double[] sdstore = new double[k];
		//System.out.println(matrix[0][7]);
		//traverse columns
		for(int i = 0;i<k;i++){
			double toCalc =0;
			for (int j = 0; j < matrix.length; j++) {
				toCalc+=(Math.pow((matrix[j][i]-means[i]),2));
			}
			sdstore[i]=Math.sqrt(toCalc/(matrix.length-1));
		}
		return sdstore;
		
	}

	public static double calcFunc(double mean, double sd, double val){
		double results;
		if(sd!=0){
			results = (1/( sd * Math.sqrt( 2 * Math.PI ))) * (Math.pow(Math.E,-((Math.pow((val - mean ), 2)/(2 * Math.pow(sd, 2))))));
		}else{
			results = 1;
		}
		return results;
	}

	public static double[][] translate(ArrayList<entry> input){
		double [ ] [ ] trainingD = new double [input.size()] [ input.get(0).getValues().size() ];
		for(int i=0;i<input.size();i++){
			ArrayList<Double> toAdd = input.get(i).getValues();
			for(int j=0;j<toAdd.size();j++){
				trainingD[i][j]=toAdd.get(j);
			}
		}
		return trainingD;
	}
	
}
