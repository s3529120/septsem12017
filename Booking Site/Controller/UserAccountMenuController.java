package Controller;

import Model.UserAccountModel;
import View.UserAccountMenuView;

public class UserAccountMenuController {
	private UserAccountMenuView view;
	private UserAccountModel model;
	
	public UserAccountMenuController(UserAccountModel model, 
			UserAccountMenuView view){
		this.model=model;
		this.view=view;
		
	}
	
	public void updateView(){
		view.updateView(model);
	}
}
