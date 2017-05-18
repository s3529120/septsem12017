package admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import accounts.AccountController;
import accounts.AdminAccountModel;
import accounts.BusinessAccountModel;
import accounts.UserAccountModel;
import admin.AdminController;
import booking.BookingController;
import booking.BookingModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import menu.MainMenuController;
import menu.MainMenuView;
import service.TypeController;
import service.TypeModel;
import service.TypeView;
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
				addBusiness(cont);
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
		// TODO: admincontroller.getBusinesses(). otherwise null reference error
		// is returned

		busList.forEach(business -> {
			// Display business data
			Text busName = new Text("Business: " + business.getBusinessName().toString());
			Text contactNo = new Text("Phone No: " + business.getContactNo().toString());
			Text address = new Text("Address: " + business.getAddress().toString());

			// Delete business button
			Button delBusBtn = new Button("Delete Business");
			delBusBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					confirmDelete(business, cont);
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
		body.getStyleClass().add("scroll-pane");
		body.setId("mainPageVBox");

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

	/**
	 * Delete business confirmation pop up
	 * 
	 * @param business
	 * @param adcont
	 */
	public void confirmDelete(BusinessAccountModel business, AdminController adcont) {
		Stage popup = new Stage();
		popup.initModality(Modality.WINDOW_MODAL);
		popup.initOwner(adcont.getView().stage);
		StackPane pane = new StackPane();

		String busname = business.getBusinessName().toString();

		Text message = new Text("You are about to delete " + busname + ".\n Are you sure?");

		Button confirm = new Button("Confirm");
		confirm.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				AdminController.delBusiness(business.getUsername().toString());
				popup.close();
				cont.updateView();
			}
		});
		confirm.getStyleClass().add("orangebtn-small");

		// Cancel button
		Button cancel = new Button("Cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				popup.close();
			}
		});
		cancel.getStyleClass().add("redbtn-small");

		HBox buttons = new HBox(10, confirm, cancel);

		// Add to pane
		VBox all = new VBox(20, message, buttons);
		buttons.setAlignment(Pos.CENTER);
		message.setTextAlignment(TextAlignment.CENTER);
		pane.getChildren().addAll(all);

		// Set scene for making booking
		pane.setId("pop-up");
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		popup.setScene(scene);
		popup.show();
	}

	public void addBusiness(AdminController cont) {
		Stage popup = new Stage();
		popup.initModality(Modality.WINDOW_MODAL);
		popup.initOwner(cont.getView().stage);
		StackPane pane = new StackPane();

		Text h1 = new Text("Add a Business");

		// Add business form

		// Username
		TextField usertxtfield = new TextField();
		usertxtfield.setPromptText("Username");
		HBox unamehbox = new HBox(usertxtfield);
		usertxtfield.setId("form");
		// unamehbox.setAlignment(Pos.CENTER);

		// Name
		TextField pnametxtfield = new TextField();
		pnametxtfield.setPromptText("Business Name");
		HBox pnamehbox = new HBox(pnametxtfield);
		pnametxtfield.setId("form");
		// pnamehbox.setAlignment(Pos.CENTER);

		// Address
		TextField addtxtfield = new TextField();
		addtxtfield.setPromptText("Address");
		HBox addhbox = new HBox(addtxtfield);
		addtxtfield.setId("form");
		// addhbox.setAlignment(Pos.CENTER);

		// Contact number
		TextField numtxtfield = new TextField();
		numtxtfield.setPromptText("Contact Number");
		HBox numhbox = new HBox(numtxtfield);
		numtxtfield.setId("form");
		// numhbox.setAlignment(Pos.CENTER);

		// Email address
		TextField mailtxtfield = new TextField();
		mailtxtfield.setPromptText("Email Address");
		HBox mailhbox = new HBox(mailtxtfield);
		mailtxtfield.setId("form");
		// mailhbox.setAlignment(Pos.CENTER);

		// Password
		PasswordField pwordfield = new PasswordField();
		pwordfield.setPromptText("Password");
		HBox pwordhbox = new HBox(pwordfield);
		pwordfield.setId("form");
		// pwordhbox.setAlignment(Pos.CENTER);

		// Confirm password
		PasswordField pwordfieldcon = new PasswordField();
		pwordfieldcon.setPromptText("Confirm Password");
		HBox pwordhboxcon = new HBox(pwordfieldcon);
		pwordfieldcon.setId("form");
		// pwordhboxcon.setAlignment(Pos.CENTER);
		VBox dets = new VBox(unamehbox, pnamehbox, addhbox, mailhbox, numhbox, pwordhbox, pwordhboxcon);

		// Cancel button
		Button cancel = new Button("Cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				popup.close();
			}
		});
		cancel.getStyleClass().add("orangebtn-small");

		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (cont.addBusiness(usertxtfield.getText().toString(), pwordfield.getText().toString(),
						pnametxtfield.getText().toString(), numtxtfield.getText().toString(),
						addtxtfield.getText().toString(), mailtxtfield.getText().toString())) {
					
					// Confirm text
					Text conftxt = new Text("Add business Confirmed!");
					// Close button
					Button close = new Button("Close");
					close.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							popup.close();
							cont.updateView();
						}
					});
					close.getStyleClass().add("orangebtn-small");
					VBox vbox = new VBox(conftxt, close);
					vbox.setId("pop-up");
					Scene confscene = new Scene(vbox);
					confscene.getStylesheets()
							.add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
					popup.setScene(confscene);
					cont.updateView();
				} else {
					// False message
				}
			}
		});
		submit.getStyleClass().add("orangebtn-small");

		HBox buttons = new HBox(10, submit, cancel);

		VBox all = new VBox(15, h1, dets, buttons);

		// Set scene for add business
		pane.getChildren().addAll(all);
		pane.setId("pop-up");
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		popup.setScene(scene);
		popup.show();
	}

}