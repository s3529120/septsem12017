package accounts;

public class SuperAccountModel extends AccountModel {
	/**
	 * The model for the super user account
	 * @param username Should be only one, 'sup001'
	 * @param name Should be 'Admin'
	 * @param contactNo Can be empty
	 * @param address Can be empty
	 * @param email of the user. Can be empty
	 */
	public SuperAccountModel(
			String username, String name,
			String contactNo,String address,String email){
		super(username,name,contactNo, address, email);
	}
}
