import java.util.ArrayList;

public class entry {
	private ArrayList<Double> values;
	private String myClass;
	
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
}
