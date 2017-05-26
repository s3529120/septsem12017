package accounts;


public class AccountModel {
	//Attributes
	private String username;
   private String contactNo;
   private String address;
   private String email;
   private String name;
	
	//Constructor
	public AccountModel(String username,String name, String contactNo,String address,String email){
		this.username=username;
		this.contactNo=contactNo;
		this.address=address;
		this.email=email;
		this.name=name;
	}
	//Alternate constructor
	public AccountModel(String username2, String name2, String email2) {
		this.username=username2;
		this.name=name2;
		this.email=email2;
	}

	//Accessors
	public String getUsername(){
		return username;
	}
	
	public String getContactNo(){
	   return this.contactNo;
	}
	
	public String getAddress(){
      return this.address;
   }

   public String getEmail(){
      return this.email;
   }
   public String getName(){
      return this.name;
   }
	//Mutators
	public Boolean setUsername(String name){
		this.username=name;
		return true;
	}
	public Boolean setContactNo(String num){
      this.contactNo=num;
      return true;
   }
	public Boolean setAddress(String add){
      this.address=add;
      return true;
   }
	public Boolean setEmail(String email){
      this.email=email;
      return true;
   }
	public Boolean setName(String name){
	   this.name=name;
	   return true;
	}
	//Methods
	
}
