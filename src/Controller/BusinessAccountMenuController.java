package Controller;

import Model.BusinessAccountModel;
import View.BusinessAccountMenuView;

public class BusinessAccountMenuController {
	private BusinessAccountMenuView view;
	private BusinessAccountModel model;
	
	public BusinessAccountMenuController(BusinessAccountModel model, 
			BusinessAccountMenuView view){
		this.model=model;
		this.view=view;
		
	}
	
	public void updateView(){
		view.updateView(model);
	}
}
