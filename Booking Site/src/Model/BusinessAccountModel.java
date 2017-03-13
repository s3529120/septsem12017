package Model;

public class BusinessAccountModel extends AccountModel{
	
	//Attributes
	private String businessName;
	private String contactNo;
	private String address;
	private DayModel[] opendays;
	private BookingModel[] bookings;
	
	//Constructor
	public BusinessAccountModel(String username, String businessName,
			String contactNo, String address) {
		super(username);
		this.businessName=businessName;
		this.contactNo=contactNo;
		this.address=address;
	}
	
	//Accessors
	public String getUsername(){
		return super.getUsername();
	}
	public String getBusinessName(){
		return businessName;
	}
	public String getContactNo(){
		return contactNo;
	}
	public String getAddress(){
		return address;
	}
	//Mutators
	public Boolean setUsername(String name){
		super.setUsername(name);
		return true;
	}
	public Boolean setBusinessName(String name){
		this.businessName=name;
		return true;
	}
	public Boolean setContactNo(String num){
		this.contactNo=num;
		return true;
	}
	public Boolean setAddress(String address){
		this.address=address;
		return true;
	}
	//Methods

}
