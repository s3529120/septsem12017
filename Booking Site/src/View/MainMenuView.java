package View;

import Controller.DefaultController;
import Controller.UserRegistrationController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuView{
	private Stage stage;
	private DefaultController cont;
	
	public MainMenuView(Stage stage){
		this.stage=stage;
	}
	
	public void updateView(){
		
		Text txt = new Text("Welcome to booking site\n What would you like to do?");
		
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				cont.loginEvent();
			}
		});
		
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				UserRegistrationView view = new UserRegistrationView(stage);
				UserRegistrationController cont = new UserRegistrationController(view);
				cont.updateView();
			}
		});
		
		VBox vbox = new VBox();
		vbox.getChildren().add(txt);
		vbox.getChildren().add(loginbtn);
		vbox.getChildren().add(registerbtn);
		
		StackPane frame = new StackPane(vbox);
		Scene scene = new Scene(frame);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public Boolean setController(DefaultController cont){
		this.cont=cont;
		return true;
	}
}
