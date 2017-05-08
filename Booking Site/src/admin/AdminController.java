package admin;

import admin.AdminView;
/**
 * Update the view of the admin account landing page, and handle business functions
 * 
 *
 */
public class AdminController {

	private AdminView view;

	//Returns associated view
	public AdminView getView(){
		return this.view;
	}

	//Sets associated view
	public Boolean setView(AdminView view) {
		this.view=view;
		return true;
	}

	//Calls associated view to update window
	public void updateView(){
		view.updateView();
	}
	
	// TODO: Add methods for adding and removing a business.
	
	/**
	 * Add a new business login into the database
	 * @param busname New business name to login with
	 * @param Password New Bus Pass
	 * @param Name New human readable name
	 * @param ContactNo new phone
	 * @param Type Always 'Business'
	 * @param Address of business
	 * @param Email of business
	 * @return True if business added.
	 */
	public boolean addBusiness(String Busname,String Password,String Name,String ContactNo,
			String Type,String Address, String Email){
		
		
		return false;
	}
	
	/**
	 * Delete a business and all associated employees/rostering if any exist
	 * @param Busname of the business to be reoved from database
	 * @return True if successful
	 */
	public boolean delBusiness(String Busname){
		
		return false;
	}
}
