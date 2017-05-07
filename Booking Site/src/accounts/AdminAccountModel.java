package accounts;

public class AdminAccountModel extends AccountModel {
	/**
	 * The model for the super user account
	 * @param username Should be only one, 'sup001'
	 * @param name Should be 'Admin'
	 * @param contactNo Can be empty
	 * @param address Can be empty
	 * @param email of the user. Can be empty
	 */
	public AdminAccountModel(
			String username, String name,
			String contactNo,String address,String email){
		super(username,name,contactNo, address, email);
	}
}
