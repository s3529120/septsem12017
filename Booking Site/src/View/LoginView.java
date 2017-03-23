package View;

import Controller.LoginController;
import Controller.UserRegistrationController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	   
		TextField usernamefield = new TextField();
		HBox usernameBox = new HBox();
		usernamefield.setPromptText("Username");
		usernameBox.getChildren().add(usernamefield);
		
		PasswordField pwordfield = new PasswordField();
		HBox pwordBox = new HBox();
		pwordfield.setPromptText("Password");
		pwordBox.getChildren().add(pwordfield);
		
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
			   if(cont.isNotEmpty(usernamefield.getText(), pwordfield.getText())){
			      cont.login(usernamefield.getText(), 
			                 pwordfield.getText());
			   }else{
			      
			      //add css change here
			   }
			}
		});
		
		VBox vbox = new VBox(welcometxt,usernameBox,pwordBox,loginbtn);
		
		HBox loginpageBox = new HBox(regbox,vbox);
		
		loginpageBox.getStyleClass().add("loginpageBox");
		vbox.getStyleClass().add("vbox");
		regbox.getStyleClass().add("regbox");
		usernameBox.setId("form");
		pwordBox.setId("form");
		registerbtn.setId("registerbtn");
		regtxt.setId("heading");
		welcometxt.setId("heading");
		loginbtn.setId("loginbtn");
		
		StackPane pane = new StackPane(loginpageBox);
		
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
	}
	
}
