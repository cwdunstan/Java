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
			
		return;
		
	}
	
	private static double pdf(entry e, double[][] matrix) {
		// TODO Auto-generated method stub
		double[] meanstore = calcMean(matrix,e.getValues().size());
		return 0;
	}

	public static double[] calcMean(double[][] matrix, int k){
		//for each row
		double[] meanstore = new double[k];
		int total =0;
		for(double[] d : matrix){
			for(int i=0;i<meanstore.length;i++){
				meanstore[i]+=d[i];
			}
			total++;
		}
		
		for(double d : meanstore){
			d=d/total;
		}
		return meanstore;
		
	}
	
	public static double calcSD(double[][] matrix, double[] means, int total){
		return 0;
		
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
