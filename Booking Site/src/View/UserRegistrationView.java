package View;

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
		
		Text head = new Text("Register");
		HBox headhbox = new HBox(head);
		
		Label unamelbl = new Label("Username: ");
		TextField usertxtfield = new TextField();
		HBox unamehbox = new HBox(unamelbl,usertxtfield);
		
		Label pnamelbl = new Label("Your name: ");
		TextField pnametxtfield = new TextField();
		HBox pnamehbox = new HBox(pnamelbl,pnametxtfield);
		
		Label pwordlbl = new Label("Password: ");
		PasswordField pwordfield = new PasswordField();
		HBox pwordhbox = new HBox(pwordlbl,pwordfield);
		
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				cont.register(usertxtfield.getText(),
						pnametxtfield.getText(),pwordfield.getText());
			}
		});
		HBox btnbox = new HBox(registerbtn);
		
		VBox vbox = new VBox(headhbox,unamehbox,pnamehbox,pwordhbox,btnbox);
		StackPane pane = new StackPane(vbox);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		
	}
}
