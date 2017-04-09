package Controller;

import View.LoginView;
import View.MainMenuView;
import javafx.stage.Stage;

public class DefaultController {
	private MainMenuView view;
	public Stage stage;
	
	/**Constructor sets view and stage.
	 * @param stage Stage to associate with.
	 * @param view View to associate with.
	 */
	public DefaultController(Stage stage, MainMenuView view){
		this.view=view;
		this.stage=stage;
		view.setController(this);
	}
	
	/**Calls view to update window
	 */
	public void updateView(){
		view.updateView();
	}
	
	/**Creates login view and controller.
	 */
	public void loginEvent(){
		LoginController cont = new LoginController(new LoginView(stage));
		cont.updateView();
	}
}
