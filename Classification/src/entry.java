import java.util.ArrayList;

public class entry {
	private ArrayList<Double> values;
	private String myClass;
	private double distance;
	
	public entry(ArrayList<Double> invalues){
		values = invalues;
		myClass="";
	}
	
	public ArrayList<Double> getValues() {
		return values;
	}
	public void setValues(ArrayList<Double> values) {
		this.values = values;
	}
	
	
	public String getMyClass() {
		return this.myClass;
	}
	public void setMyClass(String myClass) {
		this.myClass = myClass;
	}
	
	public double calcDistance(entry Target) {
		double tosquare =0;
		for(int i=0;i<values.size();i++){
			tosquare+=Math.pow((values.get(i)-Target.getValues().get(i)),2);
		}
		return Math.sqrt(tosquare);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
