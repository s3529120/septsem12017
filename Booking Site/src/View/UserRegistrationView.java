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
		unamehbox.setId("form");

		//Label pnamelbl = new Label("Your name: ");
		TextField pnametxtfield = new TextField();
		pnametxtfield.setPromptText("Full Name");
		HBox pnamehbox = new HBox(pnametxtfield);
		pnamehbox.setId("form");

		//Label addlbl = new Label("Your Address: ");
		TextField addtxtfield = new TextField();
		addtxtfield.setPromptText("Address");
		HBox addhbox = new HBox(addtxtfield);
		addhbox.setId("form");

		//Label numlbl = new Label("Your Contact Number: ");
		TextField numtxtfield = new TextField();
		numtxtfield.setPromptText("Contact Number");
		HBox numhbox = new HBox(numtxtfield);
		numhbox.setId("form");

		//Label maillbl = new Label("Your Email address: ");
		TextField mailtxtfield = new TextField();
		mailtxtfield.setPromptText("Email Address");
		HBox mailhbox = new HBox(mailtxtfield);
		mailhbox.setId("form");

		//Label pwordlbl = new Label("Password: ");
		PasswordField pwordfield = new PasswordField();
		pwordfield.setPromptText("Password");
		HBox pwordhbox = new HBox(pwordfield);
		pwordhbox.setId("form");
		
		PasswordField pwordfieldcon = new PasswordField();
		pwordfieldcon.setPromptText("Confirm Password");
		HBox pwordhboxcon = new HBox(pwordfieldcon);
		pwordhboxcon.setId("form");
		
		Text emptyerrortxt = new Text("All fields must be filled");
		HBox emptyerrorbox = new HBox();
		
		Text passerrortxt = new Text("Entered passwords do not match");
		HBox passerrorbox = new HBox();
		
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){

				//checking to make sure all fields are filled
			   if(cont.checkValues(usertxtfield,
			                    pnametxtfield,pwordfield,pwordfieldcon,
			                    numtxtfield,addtxtfield,mailtxtfield)){
					cont.register(usertxtfield.getText(),
							pnametxtfield.getText(),pwordfield.getText(),
							numtxtfield.getText(),addtxtfield.getText(),mailtxtfield.getText());
			   }else{
			      //checking for empty
				   cont.validateEntires(
							usertxtfield, unamehbox, 
							pnametxtfield, pnamehbox, 
							pwordfield, pwordhbox, 
							pwordfieldcon, pwordhboxcon, 
							addtxtfield, addhbox, 
							numtxtfield, numhbox, 
							mailtxtfield, mailhbox, 
							emptyerrortxt, emptyerrorbox,
							passerrortxt, passerrorbox);
			   }
			}
		});
		HBox btnbox = new HBox(registerbtn);
		
		VBox vbox = new VBox(regtxt,unamehbox,pnamehbox,addhbox,numhbox,
		                     mailhbox,pwordhbox, pwordhboxcon,btnbox,emptyerrorbox,passerrorbox);
		
		HBox regmenubox = new HBox(vbox,logbox);
		
		regmenubox.getStyleClass().add("loginpageBox");
		logbox.getStyleClass().add("vbox");
		vbox.getStyleClass().add("regbox");
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
