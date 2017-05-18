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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.MainMenuController;
import menu.MainMenuView;
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
	/**
	 * Updates the associated view of the admin dashboard
	 */
	public void updateView() {
		sp = new ScrollPane();

		// Heading
		Text h1 = new Text("Administration Panel");

		HBox bookingstitle = new HBox(h1);
		bookingstitle.setId("bookingstitle");
		
		Button addbtn = new Button("Add Business");
		addbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO : Implement add bus btn
			}
		});
		addbtn.getStyleClass().add("orangebtn");

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
		HBox header = new HBox(h1, addbtn, logoutbtn);
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

			/* Create a confirmation popup window. 
			 *  (in reality all this delete button does is create that popup,
			 *  the business is only deleted when the delete button is pressed there. 
			 */

			Button delBusBtn = new Button("Delete Business");
			delBusBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					AdminController.delBusiness(business.getUsername().toString());
					updateView();
				}

			});
			delBusBtn.setAlignment(Pos.CENTER_RIGHT);
			delBusBtn.getStyleClass().add("delete-btn");

			VBox bus = new VBox(busName, contactNo, address);
			HBox busBox = new HBox(bus, delBusBtn);

			// Apply styles
			bus.getStyleClass().add("bookingcol");
			busBox.setId("bookingBox");

			// Add this business data to business list
			businessList.getChildren().add(busBox);
		});


		// Layout
		h1.getStyleClass().add("main-heading");
		header.setId("headerbox");
		businessList.setId("bookings-main");
		
		ScrollPane body = new ScrollPane(businessList);
		// Border not showing up, no idea why
		body.setId("mainPageVBox");
		body.getStyleClass().add("scroll-pane");
		body.setStyle("-fx-border-width: 10; -fx-border-color: #ff8833;");

		VBox page = new VBox(header, businessList);
		
		page.setId("border");
		page.getStyleClass().add("loginpageBox");

		StackPane pane = new StackPane(page);
		pane.getChildren().addAll();

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());

		stage.setScene(scene);
		stage.show();
	}

}