package View;

import Controller.BusinessAccountMenuController;
import Model.BusinessAccountModel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BusinessAccountMenuView {

	public Stage stage;
	private BusinessAccountMenuController cont;
	
	public BusinessAccountMenuView(Stage stage) {
		this.stage=stage;
	}
	
	public Boolean setController(BusinessAccountMenuController controller){
		this.cont=controller;
		return true;
	}
	
	public void updateView(BusinessAccountModel model){
		Text txt = new Text("Business: "+model.getBusinessName()
						+", was Logged in.");
		StackPane pane = new StackPane();
		pane.getChildren().add(txt);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
	}

}
