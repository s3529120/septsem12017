package View;

import Controller.BookingController;
import Controller.DefaultController;
import Controller.EmployeeController;
import Controller.UserAccountMenuController;
import Model.UserAccountModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserAccountMenuView {

	public Stage stage;
	private UserAccountMenuController cont;

	public UserAccountMenuView(Stage stage) {
		this.stage=stage;
	}

	/**Constructor, sets stage.
    * @param stage Window to manipulate.
    */
	public Boolean setController(UserAccountMenuController controller){
		this.cont=controller;
		return true;
	}

	/**Updates associated window.
    */
	public void updateView(UserAccountModel model){
	   
		//Logout button
		Button logoutbtn = new Button("Logout");
		logoutbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				MainMenuView mainview = new MainMenuView(stage);
				DefaultController maincont = new DefaultController(stage,mainview);
				maincont.updateView();
			}
		});
		HBox logoutbox = new HBox(logoutbtn);

		//Headings and text
		Text h1 = new Text("Welcome "+model.getName()+"!");
		Text h2 = new Text("Select from the menu options below.");

		

		//Make booking
		Button makebookbtn = new Button("Make a Booking");
		makebookbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
			   BookingController bcont = new BookingController();
            bcont.setView(new BookingsView(new Stage()));
            bcont.getView().setController(bcont);
            bcont.setCaller(cont.getModel());
            bcont.updateView();
			}
		});
		
		//Layout
		
		VBox page = new VBox(logoutbox,h1,h2,makebookbtn);
		
		makebookbtn.setId("menubtn");
		logoutbtn.setId("logoutbtn");
		logoutbox.setId("logoutbox");
		h1.setId("heading");
		page.setId("menubox");
		
		StackPane pane = new StackPane();
		pane.getChildren().add(page);
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		stage.setScene(scene);
	}

}
