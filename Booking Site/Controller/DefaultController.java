package Controller;

import View.LoginView;
import View.MainMenuView;
import javafx.stage.Stage;

public class DefaultController {
	private MainMenuView view;
	public Stage stage;
	
	public DefaultController(Stage stage, MainMenuView view){
		this.view=view;
		this.stage=stage;
		view.setController(this);
	}
	
	public void updateView(){
		view.updateView();
	}
	
	public void loginEvent(){
		LoginController cont = new LoginController(new LoginView(stage));
		cont.updateView();
	}
}
