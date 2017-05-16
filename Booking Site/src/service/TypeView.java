package service;

import java.util.List;

import booking.BookingController;
import booking.BookingsView;
import employee.AddEmployeeView;
import employee.AvailabilitiesController;
import employee.EditAvailabilitiesView;
import employee.EmployeeController;
import businessHours.BusinessHoursController;
import businessHours.BusinessHoursView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.MainMenuController;
import menu.MainMenuView;
import utils.AppData;

public class TypeView {
	private TypeController cont;
	public Stage stage;

	/**
	 * Constructor, sets stage
	 * @param stage
	 */
	public TypeView(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Sets associated controller
	 * @param cont
	 */
	public void setCont(TypeController cont) {
		this.cont = cont;
	}

	/**
	 * Updates associated window
	 */
	public void updateView() {
		Text h1 = new Text(cont.getEmpname() + "'s Specializations");

		// Add
		List<TypeModel> unknown = cont.getUnknownTypes();
		Label addlbl = new Label("Specialization to add");
		ComboBox<String> addbox = new ComboBox<String>();
		unknown.forEach(x -> {
			addbox.getItems().add(x.getName());
		});
		Button addbtn = new Button("Add");
		addbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cont.addSpec(addbox.getSelectionModel().getSelectedItem());
			}
		});
		VBox addVbox = new VBox(addlbl, addbox, addbtn);

		// Remove
		List<TypeModel> known = cont.getSetTypes();
		Label remlbl = new Label("Specialization to remove");
		ComboBox<String> rembox = new ComboBox<String>();
		known.forEach(x -> {
			rembox.getItems().add(x.getName());
		});
		Button rembtn = new Button("Remove");
		rembtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cont.removeSpec(rembox.getSelectionModel().getSelectedItem());
			}
		});
		VBox remVbox = new VBox(remlbl, rembox, rembtn);

		// Done
		Button done = new Button("Done");
		done.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
			}
		});

		// Current specialisations list
		String specString = "Currently consists of";
		for (TypeModel type : known) {
			specString += " " + type.getName() + ",";
		}
		specString = specString.substring(0, specString.length() - 1) + ".";
		Text specText = new Text(specString);

		// Set scene for type view
		StackPane pane = new StackPane();
		VBox all = new VBox(h1, specText, addVbox, remVbox, done);
		pane.getChildren().addAll(all);
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Updates scene when type is added or removed
	 */
	public void updateTypeView() {
		// Header init
		Text heading = new Text("Booking Site");
		heading.getStyleClass().add("main-heading");

		// View Bookings
		Button viewbookbtn = new Button("View Bookings");
		viewbookbtn.setUserData(cont);
		viewbookbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BookingController bcont = new BookingController();
				bcont.setView(new BookingsView(stage));
				bcont.getView().setController(bcont);
				bcont.updateView();
			}
		});
		viewbookbtn.getStyleClass().add("orangebtn");

		// Add employee
		Button addempbtn = new Button("Add Employee");
		addempbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				EmployeeController empcont = new EmployeeController();
				empcont.setView(new AddEmployeeView(stage));
				empcont.getView().setController(empcont);
				empcont.updateView();
			}
		});
		addempbtn.getStyleClass().add("orangebtn");

		// Edit availability
		Button editavailbtn = new Button("Manage Employees");
		editavailbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AvailabilitiesController acont = new AvailabilitiesController();
				acont.setView(new EditAvailabilitiesView(stage));
				acont.getView().setController(acont);
				acont.updateView();
			}
		});
		editavailbtn.getStyleClass().add("orangebtn");

		// Manage services button
		Button edittypebtn = new Button("Manage Services");
		edittypebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				TypeController tcont = new TypeController();
				TypeView tview = new TypeView(stage);
				tcont.setView(tview);
				tview.setCont(tcont);
				tview.updateTypeView();
			}
		});
		edittypebtn.getStyleClass().add("orangebtn");

		// Business settings
		Button bussettingsbtn = new Button("Business Settings");
		bussettingsbtn.setUserData(cont);
		bussettingsbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BusinessHoursController bhcont = new BusinessHoursController();
				BusinessHoursView bhview = new BusinessHoursView(bhcont);
				bhcont.setView(bhview);
				bhview.updateView(stage);
			}
		});
		bussettingsbtn.getStyleClass().add("orangebtn");

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

		HBox header = new HBox(10, heading, viewbookbtn, addempbtn, editavailbtn, edittypebtn, bussettingsbtn, logoutbtn);
		header.setId("headerbox");
		heading.getStyleClass().add("main-heading");
		
		
		Text h1 = new Text("Edit Services");
		h1.getStyleClass().add("pageheading");

		// Add (LEFT)
		Text h2 = new Text("Add service");
		Label typelbl = new Label("Service name: ");
		TextField typefield = new TextField();
		typefield.setId("form");

		Label numlbl = new Label("Duration in minutes: ");
		TextField numfield = new TextField();
		numfield.setId("form");

		Button addbtn = new Button("Add");
		addbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cont.addType(typefield.getText(), Integer.parseInt(numfield.getText()));
				updateTypeView();
			}
		});
		addbtn.getStyleClass().add("orangebtn");
		addbtn.setAlignment(Pos.BOTTOM_CENTER);

		HBox row1 = new HBox(typelbl, typefield);
		HBox row2 = new HBox(numlbl, numfield);
		VBox addVbox = new VBox(h2, row1, row2, addbtn);
		addVbox.getStyleClass().add("loginpageBox");

		// Remove (RIGHT)
		Text h3 = new Text("Remove service");
		List<TypeModel> known = TypeController.getAllTypes();
		Label remlbl = new Label("Service to remove");
		ComboBox<String> rembox = new ComboBox<String>();
		known.forEach(x -> {
			rembox.getItems().add(x.getName());
		});
		rembox.setId("form");
		Button rembtn = new Button("Remove");
		rembtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cont.removeType(rembox.getSelectionModel().getSelectedItem());
				updateTypeView();
			}
		});
		rembtn.getStyleClass().add("orangebtn");
		rembtn.setAlignment(Pos.BOTTOM_CENTER);

		VBox remVbox = new VBox(h3, remlbl, rembox, rembtn);
		remVbox.getStyleClass().add("loginpageBox");

		// current types
		String servicesString = "The current avaialable services are";
		for (TypeModel type : known) {
			servicesString += " " + type.getName() + ",";
		}
		servicesString = servicesString.substring(0, servicesString.length() - 1) + ".";
		Text servicesText = new Text(servicesString);

		// Set scene for update type view
		HBox functions = new HBox(addVbox, remVbox);
		VBox body = new VBox(h1, servicesText, functions);
		VBox page = new VBox(header, body);
		body.setId("mainPageVBox");
		header.setId("headerbox");
		page.setId("border");
		page.getStyleClass().add("loginpageBox");

		StackPane pane = new StackPane();
		pane.getChildren().addAll(page);

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
