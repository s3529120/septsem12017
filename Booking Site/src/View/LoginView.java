package View;

import Controller.LoginController;
import Controller.UserRegistrationController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView {
	private LoginController cont;
	public Stage stage;
	
	public LoginView(Stage stage){
		this.stage=stage;
	}
	
	public Boolean setController(LoginController controller){
		this.cont=controller;
		return true;
	}
	
	public void updateView(){
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
		
		Text welcometxt = new Text("Welcome back!");
	   
		Label usrnamelbl = new Label("Username:");
		TextField usernamefield = new TextField();
		HBox usernameBox = new HBox();
		usernameBox.getChildren().add(usrnamelbl);
		usernameBox.getChildren().add(usernamefield);
		
		Label pwordlbl = new Label("Password:");
		PasswordField pwordfield = new PasswordField();
		HBox pwordBox = new HBox();
		pwordBox.getChildren().add(pwordlbl);
		pwordBox.getChildren().add(pwordfield);
		
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
			   if(cont.isNotEmpty(usernamefield.getText(), pwordfield.getText())){
			      cont.login(usernamefield.getText(), 
			                 pwordfield.getText());
			   }else{
			      Label errlbl = new Label("Invalid entry made, please review input");
			      usernameBox.getChildren().add(errlbl);
			   }
			}
		});
		
		VBox vbox = new VBox(welcometxt,usernameBox,pwordBox,loginbtn);
		
		HBox loginpageBox = new HBox(regbox,vbox);
		
		StackPane pane = new StackPane(loginpageBox);
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
	}
	
}
