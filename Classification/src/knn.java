import java.util.ArrayList;

public class knn {
	
	private ArrayList<entry> train;
	private ArrayList<entry> test;
	private int k;
	private ArrayList<String> result;

	
	public knn(ArrayList<entry> training, ArrayList<entry> testing, int kin){
		this.train = training;
		this.test = testing;
		this.k=kin;
		this.result = new ArrayList<>();
		
		
	}

	public ArrayList<String> classify(){
		for(entry e : test){
			ArrayList<entry> min = new ArrayList<>();
			for(entry t : train){
				//calcluate distance
				double distance = t.calcDistance(e);
				t.setDistance(distance);
				//if min store is full, iterate and replace greatest value
				if(min.size()>=k){
					double greatest=0;
					int track =0;
					for(int j=0;j<k;j++){
						if(min.get(j).getDistance()>greatest){
							greatest = min.get(j).getDistance();
							track = j;
						}
					}
					if(distance<greatest){
						min.set(track, t);
					}
				}else{
					min.add(t);
				}
			}
			result.add(majority(min));
		}
		return result;
	}
	public void printResult() {
		for(String s : result){
			System.out.println(s);
		}
		return;
	}
	
	public String majority(ArrayList<entry> input){
		int yes =0;
		int no =0;
		for(entry e : input){
			if(e.getMyClass().matches("yes")){
				yes++;
			}
			if(e.getMyClass().matches("no")){
				no++;
			}
		}
		if(yes>=no){
			return "yes";
		}
		else{
			return "no";
		}
	}
	

}
