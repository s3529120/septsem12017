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

public class UserRegistrationView {
	public Stage stage;
	private UserRegistrationController cont;

	public UserRegistrationView(Stage stage){
		this.stage=stage;
	}

	public Boolean setController(UserRegistrationController cont){
		this.cont=cont;
		return true;
	}

	public void updateView(){

		
		Text logtxt = new Text("Already have an account?");
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				LoginController cont = new LoginController(new LoginView(stage));
				cont.updateView();
			}
		});
		VBox logbox = new VBox(logtxt,loginbtn);
		
		Text regtxt = new Text("Register with us.");
		
		//Label unamelbl = new Label("Username: ");
		TextField usertxtfield = new TextField();
		usertxtfield.setPromptText("Username");
		HBox unamehbox = new HBox(usertxtfield);

		//Label pnamelbl = new Label("Your name: ");
		TextField pnametxtfield = new TextField();
		pnametxtfield.setPromptText("Full Name");
		HBox pnamehbox = new HBox(pnametxtfield);

		//Label addlbl = new Label("Your Address: ");
		TextField addtxtfield = new TextField();
		addtxtfield.setPromptText("Address");
		HBox addhbox = new HBox(addtxtfield);

		//Label numlbl = new Label("Your Contact Number: ");
		TextField numtxtfield = new TextField();
		numtxtfield.setPromptText("Contact Number");
		HBox numhbox = new HBox(numtxtfield);

		//Label maillbl = new Label("Your Email address: ");
		TextField mailtxtfield = new TextField();
		mailtxtfield.setPromptText("Email Address");
		HBox mailhbox = new HBox(mailtxtfield);

		//Label pwordlbl = new Label("Password: ");
		PasswordField pwordfield = new PasswordField();
		pwordfield.setPromptText("Password");
		HBox pwordhbox = new HBox(pwordfield);
		
		PasswordField pwordfieldcon = new PasswordField();
		pwordfieldcon.setPromptText("Confirm Password");
		HBox pwordhboxcon = new HBox(pwordfieldcon);
		
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){

				//checking to make sure all fields are filled
			   if(cont.checkValues(usertxtfield,
			                    pnametxtfield,pwordfield,
			                    numtxtfield,addtxtfield,mailtxtfield)){
					cont.register(usertxtfield.getText(),
							pnametxtfield.getText(),pwordfield.getText(),
							numtxtfield.getText(),addtxtfield.getText(),mailtxtfield.getText());
			   }else{
			      unamehbox.getChildren().add(new Label("Invalid entry made, " +
			            "please review input."));
			   }
			}
		});
		HBox btnbox = new HBox(registerbtn);
		
		VBox vbox = new VBox(regtxt,unamehbox,pnamehbox,addhbox,numhbox,
		                     mailhbox,pwordhbox, pwordhboxcon,btnbox);
		
		HBox regmenubox = new HBox(vbox,logbox);
		
		regmenubox.getStyleClass().add("loginpageBox");
		logbox.getStyleClass().add("vbox");
		vbox.getStyleClass().add("regbox");
		unamehbox.setId("form");
		pnamehbox.setId("form");
		addhbox.setId("form");
		numhbox.setId("form");
		mailhbox.setId("form");
		pwordhbox.setId("form");
		registerbtn.setId("largebtn");
		loginbtn.setId("largebtn");
		regtxt.setId("heading");
		logtxt.setId("heading");
		
		StackPane pane = new StackPane(regmenubox);

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);

	}
}
