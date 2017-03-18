package View;

import Controller.DefaultController;
import Controller.UserRegistrationController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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
		
		Text txt = new Text("Booking Site.");
		
		Text logtxt = new Text("Already have an account?");
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				cont.loginEvent();
			}
		});
		VBox logbox = new VBox(logtxt,loginbtn);
		
		Text regtxt = new Text("Register with us.");
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				UserRegistrationView view = new UserRegistrationView(stage);
				UserRegistrationController cont = new UserRegistrationController(view);
				cont.updateView();
			}
		});
		VBox regbox = new VBox(regtxt,registerbtn);
		
		HBox hbox = new HBox();
		hbox.getChildren().add(regbox);
		hbox.getChildren().add(logbox);
		
		VBox mainMenuBox = new VBox(txt,hbox);
		
		StackPane frame = new StackPane(mainMenuBox);
		Scene scene = new Scene(frame);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public Boolean setController(DefaultController cont){
		this.cont=cont;
		return true;
	}
}
