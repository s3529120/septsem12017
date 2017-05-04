package accounts;

public class UserAccountModel extends AccountModel{
	
	//Attributes
	
	//Constructor
	public UserAccountModel(String username, String name,
	                        String contactNo,String address,String email){
		super(username,name,contactNo, address, email);
	}
	
	//Accessors
	public String getUsername(){
		return super.getUsername();
	}
	public String getName(){
		return super.getName();
	}
	//Mutators
	public Boolean setUsername(String name){
		super.setUsername(name);
		return true;
	}
	public Boolean setName(String name){
		return super.setName(name);
	}
	//Methods
}
