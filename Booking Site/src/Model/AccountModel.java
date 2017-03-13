package Model;


public class AccountModel {
	//Attributes
	private String username;
	
	//Constructor
	public AccountModel(String username){
		this.username=username;
	}
	
	//Accessors
	public String getUsername(){
		return username;
	}
	//Mutators
	public Boolean setUsername(String name){
		this.username=name;
		return true;
	}
	//Methods
	
}
