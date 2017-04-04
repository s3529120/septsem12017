package Controller;

import Model.BusinessAccountModel;
import View.BusinessAccountMenuView;

public class BusinessAccountMenuController {
	private BusinessAccountMenuView view;
	private BusinessAccountModel model;
	
	//Constructor sets model and view values
	public BusinessAccountMenuController(BusinessAccountModel model, 
			BusinessAccountMenuView view){
		this.model=model;
		this.view=view;
		
	}
	
	//Call view to update window
	public void updateView(){
		view.updateView(model);
	}
}
