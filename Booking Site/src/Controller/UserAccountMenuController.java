package Controller;

import Model.UserAccountModel;
import View.UserAccountMenuView;

public class UserAccountMenuController {
	private UserAccountMenuView view;
	private UserAccountModel model;
	
	/**Constructor, sets associated model and view.
	 * @param model Model to associate.
	 * @param view View to associate.
	 */
	public UserAccountMenuController(UserAccountModel model, 
			UserAccountMenuView view){
		this.model=model;
		this.view=view;
		
	}
	
	/**Calls associated view to update window.
	 */
	public void updateView(){
		view.updateView(model);
	}
}
