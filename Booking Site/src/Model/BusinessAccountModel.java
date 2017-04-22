package Model;

public class BusinessAccountModel extends AccountModel{
	
	//Attributes
	
	//Constructor
	public BusinessAccountModel(String username, String businessName,
			String contactNo, String address,String email) {
		super(username,businessName,contactNo,address,email);
	}
	
	//Accessors
	public String getUsername(){
		return super.getUsername();
	}
	public String getBusinessName(){
		return super.getName();
	}
	public String getContactNo(){
		return super.getContactNo();
	}
	public String getAddress(){
		return super.getAddress();
	}
	public String getEmail(){
	   return super.getEmail();
	}
	//Mutators
	public Boolean setUsername(String name){
		super.setUsername(name);
		return true;
	}
	public Boolean setBusinessName(String name){
		super.setName(name);
		return true;
	}
	public Boolean setContactNo(String num){
		super.setContactNo(num);
		return true;
	}
	public Boolean setAddress(String address){
		super.setAddress(address);
		return true;
	}
	public Boolean setEmail(String email){
	   super.setEmail(email);
	   return true;
	}
	//Methods

}
