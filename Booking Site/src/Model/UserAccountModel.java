package Model;

public class UserAccountModel extends AccountModel{
	
	//Attributes
	private String name;
	
	//Constructor
	public UserAccountModel(String username, String name){
		super(username);
		this.name=name;
	}
	
	//Accessors
	public String getUsername(){
		return super.getUsername();
	}
	public String getName(){
		return name;
	}
	//Mutators
	public Boolean setUsername(String name){
		super.setUsername(name);
		return true;
	}
	public Boolean setName(String name){
		this.name=name;
		return true;
	}
	//Methods
}
