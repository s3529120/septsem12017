package admin;

import booking.BookingsView;

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
