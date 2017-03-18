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
<<<<<<< HEAD

		Text head = new Text("Register");
		HBox headhbox = new HBox(head);

=======
		
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
		
>>>>>>> 547cb3c4523712c3fa8a261a2da9e32321aac4e7
		Label unamelbl = new Label("Username: ");
		TextField usertxtfield = new TextField();
		HBox unamehbox = new HBox(unamelbl,usertxtfield);

		Label pnamelbl = new Label("Your name: ");
		TextField pnametxtfield = new TextField();
		HBox pnamehbox = new HBox(pnamelbl,pnametxtfield);

		Label addlbl = new Label("Your Address: ");
		TextField addtxtfield = new TextField();
		HBox addhbox = new HBox(addlbl,addtxtfield);

		Label numlbl = new Label("Your Contact Number: ");
		TextField numtxtfield = new TextField();
		HBox numhbox = new HBox(numlbl,numtxtfield);

		Label maillbl = new Label("Your Email address: ");
		TextField mailtxtfield = new TextField();
		HBox mailhbox = new HBox(maillbl,mailtxtfield);

		Label pwordlbl = new Label("Password: ");
		PasswordField pwordfield = new PasswordField();
		HBox pwordhbox = new HBox(pwordlbl,pwordfield);

		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
<<<<<<< HEAD
				if(cont.checkValues(usertxtfield,
						pnametxtfield,pwordfield,
						numtxtfield,addtxtfield,mailtxtfield)){
					cont.register(usertxtfield.getText(),
							pnametxtfield.getText(),pwordfield.getText(),
							numtxtfield.getText(),addtxtfield.getText(),mailtxtfield.getText());
				}else{
					headhbox.getChildren().add(new Label("Invalid entry made, " +
							"please review input."));
				}
			}
		});
		HBox btnbox = new HBox(registerbtn);

		VBox vbox = new VBox(headhbox,unamehbox,pnamehbox,addhbox,numhbox,
				mailhbox,pwordhbox,btnbox);
		StackPane pane = new StackPane(vbox);
=======
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
		                     mailhbox,pwordhbox,btnbox);
		
		HBox regmenubox = new HBox(vbox,logbox);
		
		StackPane pane = new StackPane(regmenubox);
>>>>>>> 547cb3c4523712c3fa8a261a2da9e32321aac4e7
		Scene scene = new Scene(pane);
		stage.setScene(scene);

	}
}
