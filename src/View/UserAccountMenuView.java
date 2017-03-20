package View;

import Controller.UserAccountMenuController;
import Model.UserAccountModel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserAccountMenuView {

	public Stage stage;
	private UserAccountMenuController cont;
	
	public UserAccountMenuView(Stage stage) {
		this.stage=stage;
	}
	
	public Boolean setController(UserAccountMenuController controller){
		this.cont=controller;
		return true;
	}
	
	public void updateView(UserAccountModel model){
		Text txt = new Text("User: "+model.getName()+", was Logged in or created.");
		StackPane pane = new StackPane();
		pane.getChildren().add(txt);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
	}

}
