package View;

import Controller.DefaultController;
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
		Stage primaryStage = new Stage();
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		
		Button returnbtn = new Button("Return");
		returnbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				DefaultController cont = new DefaultController(primaryStage, new MainMenuView(primaryStage));
				cont.updateView();
			}
		});
		
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
		
		Text errortxt = new Text("Incorrect username and password combination");
		HBox errorbox = new HBox();
		
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
			   if(cont.isNotEmpty(usernamefield.getText(), pwordfield.getText())){
			      if (!cont.login(usernamefield.getText(), 
			                 pwordfield.getText())) {
			    	  if (!errorbox.getChildren().contains(errortxt)) {
							errorbox.getChildren().add(errortxt);
					   }
			    	  usernameBox.setId("form");
			    	  pwordBox.setId("form");
			      }
			   }else{
				   if(usernamefield.getText().trim().equals("")) {
					   usernameBox.setId("incorrectForm");
				   } else {
					   usernameBox.setId("form");
				   }
				   if(pwordfield.getText().trim().equals("")) {
					   pwordBox.setId("incorrectForm");
				   } else {
					   pwordBox.setId("form");
				   }
				   if (errorbox.getChildren().contains(errortxt)) {
						errorbox.getChildren().remove(errortxt);
				   }
			   }
			}
		});
		
		VBox vbox = new VBox(returnbtn,welcometxt,usernameBox,pwordBox,loginbtn,errorbox);
		
		HBox loginpageBox = new HBox(regbox,vbox);
		
		loginpageBox.getStyleClass().add("loginpageBox");
		vbox.getStyleClass().add("vbox");
		returnbtn.setId("loginbtn");
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
