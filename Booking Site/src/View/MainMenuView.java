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
	
	/**Constructor, sets stage.
	 * @param stage Window to manipulate.
	 */
	public MainMenuView(Stage stage){
		this.stage=stage;
	}
	
	/**Updates associated window.
	 */
	public void updateView(){
		
	   //Headings
		Text txt = new Text("Booking Site.");
		Text logtxt = new Text("Already have an account?");
      Text regtxt = new Text("Register with us.");
		
		//Login button
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				cont.loginEvent();
			}
		});
		VBox logbox = new VBox(logtxt,loginbtn);
		
		//Register button
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				UserRegistrationView view = new UserRegistrationView(stage);
				UserRegistrationController cont = new UserRegistrationController(view);
				cont.updateView();
			}
		});
		VBox regbox = new VBox(regtxt,registerbtn);
		
		//Layout
		
		HBox hbox = new HBox();
		hbox.getChildren().add(regbox);
		hbox.getChildren().add(logbox);
		
		VBox mainMenuBox = new VBox(txt,hbox);
		
		//Styles
		mainMenuBox.getStyleClass().add("loginpageBox");
		logbox.getStyleClass().add("vbox");
		regbox.getStyleClass().add("regbox");
		//usernameBox.setId("form");
		//pwordBox.setId("form");
		registerbtn.setId("largebtn");
		loginbtn.setId("largebtn");
		regtxt.setId("heading");
		logtxt.setId("heading");
		//welcometxt.setId("heading");
		
		StackPane frame = new StackPane(mainMenuBox);
		Scene scene = new Scene(frame);
		scene.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	/**Sets associated controller
	 * @param cont Controller to associate.
	 * @return True upon success.
	 */
	public Boolean setController(DefaultController cont){
		this.cont=cont;
		return true;
	}
}
