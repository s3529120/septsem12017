package accounts;

public class AdminAccountModel extends AccountModel {
	/**
	 * The model for the super user account
	 * @param username Should be only one, 'sup001'
	 * @param name Should be 'Admin'
	 * @param email of the user. Can be empty
	 */
	public AdminAccountModel(
			String username,String email){
		super(username,"Admin",email);
	}
}
