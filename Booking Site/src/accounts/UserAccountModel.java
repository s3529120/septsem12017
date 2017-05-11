package accounts;

public class UserAccountModel extends AccountModel{
	
	//Constructor
	public UserAccountModel(String username, String name,
	                        String contactNo,String address,String email){
		super(username,name,contactNo, address, email);
	}
	public Boolean setAddress(String add){
	   return super.setAddress(add);
	}
}
