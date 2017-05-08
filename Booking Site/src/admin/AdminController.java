package admin;

import booking.BookingsView;
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
	
	//TODO: Add methods for adding and removing a business.
}
