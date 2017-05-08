package admin;

import java.util.List;

import accounts.AdminAccountModel;
import accounts.BusinessAccountModel;
import admin.AdminController;
import booking.BookingModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.main.MainMenuController;
import menu.main.MainMenuView;
import utils.AppData;

public class AdminView {


	private ScrollPane sp;
	private String currentdate;

	private AdminController cont;
	public Stage stage;

	public AdminView(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Sets associated controller
	 * 
	 * @param controller
	 * @return
	 */
	public Boolean setController(AdminController controller) {
		this.cont = controller;
		return true;
	}

	public void updateView() {
		sp = new ScrollPane();

		// Heading
		Text h1 = new Text("Administration Panel");
		h1.getStyleClass().add("main-heading");
		HBox bookingstitle = new HBox(h1);
		bookingstitle.setId("bookingstitle");
		Button addbtn = new Button("Add Business");

		addbtn.setId("switchbtn");


		// Logout button
		Button logoutbtn = new Button("Logout");
		logoutbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				MainMenuView mainview = new MainMenuView(stage);
				MainMenuController maincont = new MainMenuController(stage, mainview);
				AppData.CALLER = null;
				maincont.updateView();
			}
		});
		logoutbtn.setAlignment(Pos.TOP_RIGHT);
		logoutbtn.getStyleClass().add("linkbtn");

		// Header navigation
		HBox header = new HBox(h1, logoutbtn);
		header.setId("headerbox");

		// Create a list of businesses and pass through each business to list
		List<BusinessAccountModel> busList = cont.getBusinesses();
		VBox businessList = new VBox();
		// TODO: admincontroller.getBusinesses(). otherwise null reference error is returned
		
		busList.forEach(business -> {
			// Display business data
			Text busName = new Text("Business: " + business.getBusinessName().toString());
			Text contactNo = new Text("Phone No: " + business.getContactNo().toString());
			Text address = new Text("Address: " + business.getAddress().toString());
			
			VBox bus = new VBox(busName, contactNo, address);
			HBox busBox = new HBox(bus);
			
			// Apply styles
			bus.getStyleClass().add("bookingcol");
			busBox.setId("bookingBox");
			
			// Add this business data to business list
			businessList.getChildren().add(busBox);
		});


		// Layout
		HBox bookingscontainer = new HBox(businessList);
		bookingscontainer.setId("bookings-container");

		VBox main = new VBox(header, bookingscontainer);

		// Create the stage and apply css
		sp.getStyleClass().add("scroll-pane");
		main.setId("bookings-main");

		sp.setContent(main);
		Scene scene = new Scene(sp);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());

		stage.setScene(scene);
		stage.show();
	}
}