package View;

import Controller.AvailabilitiesController;
import Controller.BookingController;
import Controller.BusinessAccountMenuController;
import Controller.DefaultController;
import Controller.EmployeeController;
import Model.BusinessAccountModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BusinessAccountMenuView {

	public Stage stage;
	public BusinessAccountMenuController cont;

	/**Constructor, assigns stage.
	 * @param stage Window to manipulate.
	 */
	public BusinessAccountMenuView(Stage stage) {
		this.stage=stage;
	}

	/**Assigns given controller to self.
	 * @param controller Controller to associate.
	 * @return True upon success.
	 */
	public Boolean setController(BusinessAccountMenuController controller){
		this.cont=controller;
		return true;
	}

	/**Update assigned window.
	 * @param model
	 */
	public void updateView(BusinessAccountModel model){

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
		Text h1 = new Text("Welcome "+model.getBusinessName()+"!");
		Text h2 = new Text("Select from the menu options below.");

		//Functionality buttons

		//Add employee
		Button addempbtn = new Button("Add Employee");
		addempbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				EmployeeController empcont = new EmployeeController();
				empcont.setView(new AddEmployeeView(new Stage()));
				empcont.getView().setController(empcont);
				empcont.updateView();
			}
		});

		//Edit availability
		Button editavailbtn = new Button("Edit Availabilities");
		editavailbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				AvailabilitiesController acont =  new AvailabilitiesController();
				acont.setView(new EditAvailabilitiesView(new Stage()));
				acont.getView().setController(acont);
				acont.updateView();
			}
		});
		

		//View Availabilities
		Button viewavailbtn = new Button("View Availabilities");
		viewavailbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
			}
		});

		//View Bookings
		Button viewbookbtn = new Button("View Bookings");
		viewbookbtn.setUserData(cont);
		viewbookbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				BookingController bcont = new BookingController();
				bcont.setView(new BookingsView(new Stage()));
				bcont.getView().setController(bcont);
				bcont.setCaller(cont.getModel());
				bcont.updateView();
			}
		});

		HBox toprow = new HBox(addempbtn,editavailbtn);
		HBox botrow = new HBox(viewavailbtn,viewbookbtn);

		//VBox headings = new  VBox(h1,h2);
		//VBox buttons = new  VBox(toprow,botrow);

		VBox page = new VBox(logoutbox,h1,h2,toprow,botrow);

		//Styles

		//mainMenuBox.getStyleClass().add("loginpageBox");
		//logbox.getStyleClass().add("vbox");
		//regbox.getStyleClass().add("regbox");
		viewbookbtn.setId("menubtn");
		viewavailbtn.setId("menubtn");
		editavailbtn.setId("menubtn");
		addempbtn.setId("menubtn");
		logoutbtn.setId("logoutbtn");
		logoutbox.setId("logoutbox");
		h1.setId("heading");
		page.setId("menubox");
		//logtxt.setId("heading");
		//welcometxt.setId("heading");

		//Layout
		StackPane pane = new StackPane();
		pane.getChildren().add(page);
		//pane.getChildren().add(headings);
		//pane.getChildren().add(buttons);
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/view/css/styles.css").toExternalForm());
		stage.setScene(scene);
	}

}
