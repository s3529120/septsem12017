package Controller;

import Model.BusinessAccountModel;
import View.BusinessAccountMenuView;

public class BusinessAccountMenuController {
	private BusinessAccountMenuView view;
	private BusinessAccountModel model;
	
	/**Constructor sets model and view values.
	 * @param model Model to associate with.
	 * @param view View to associate with.
	 */
	public BusinessAccountMenuController(BusinessAccountModel model, 
			BusinessAccountMenuView view){
		this.model=model;
		System.out.println(model.getName());
		this.view=view;
		
	}
	
	/**Call view to update window.
	 */
	public void updateView(){
		view.updateView(model);
	}
	
	/**Returns associated model.
	 * 
	 */
	public BusinessAccountModel getModel(){
	   return this.model;
	}
}
