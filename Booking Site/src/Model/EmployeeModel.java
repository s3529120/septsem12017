package Model;

public class EmployeeModel {
	//Attributes
	private String name;
	
	//Constructor
	public EmployeeModel(String name){
		this.name=name;
	}
	
	//Accessors
	public String getName(){
		return name;
	}
	
	//Mutators
	public Boolean setName(String name){
		this.name=name;
		return true;
	}
}
