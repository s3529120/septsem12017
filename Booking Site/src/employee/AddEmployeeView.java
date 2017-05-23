package employee;

import booking.BookingController;
import booking.BookingsView;
import businessHours.BusinessHoursController;
import businessHours.BusinessHoursView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.MainMenuController;
import menu.MainMenuView;
import service.TypeController;
import service.TypeView;
import utils.AppData;
import utils.Colour;

public class AddEmployeeView {
	public Stage stage;
	private EmployeeController cont;

	/**
	 * Constructor, associates stage.
	 * 
	 * @param stage
	 *            Window to manipulate.
	 */
	public AddEmployeeView(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Sets associated controller.
	 * 
	 * @param cont
	 *            Controller to associate.
	 * @return True upon success.
	 */
	public Boolean setController(EmployeeController cont) {
		this.cont = cont;
		return true;
	}

	/**
	 * Updates display of window.
	 */
	public void updateView() {

		// Header init
		Text heading = new Text(AppData.CALLER.getName());
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

		// Confirmation message and heading

		Text empadded = new Text("");

		Text h1 = new Text("Add Employee");
		h1.setId("bookingsh1");

		// Vertical box 1 - user info

		// First name
		TextField fnamefield = new TextField();
		fnamefield.setPromptText("First Name");
		fnamefield.getStyleClass().add("textField");
		HBox fnamehbox = new HBox(fnamefield);
		fnamefield.setId("form");

		// Surname
		TextField snamefield = new TextField();
		snamefield.setPromptText("Last Name");
		snamefield.getStyleClass().add("textField");
		HBox snamehbox = new HBox(snamefield);
		snamefield.setId("form");

		// Email address
		TextField emailfield = new TextField();
		emailfield.setPromptText("Email");
		emailfield.getStyleClass().add("textField");
		HBox emailhbox = new HBox(emailfield);
		emailfield.setId("form");

		// Contact number
		TextField contactnofield = new TextField();
		contactnofield.setPromptText("Contact no.");
		contactnofield.getStyleClass().add("textField");
		HBox contactnohbox = new HBox(contactnofield);
		contactnofield.setId("form");

		// Add above elements to vertical box
		VBox userInfo = new VBox(15, fnamehbox, snamehbox, emailhbox, contactnohbox, empadded);

		// Vertical box 2 - address

		// Street address
		TextField streetaddfield = new TextField();
		streetaddfield.setPromptText("Street Address");
		streetaddfield.getStyleClass().add("textField");
		HBox streetaddhbox = new HBox(streetaddfield);
		streetaddfield.setId("form");

		// City
		TextField cityfield = new TextField();
		cityfield.setPromptText("City");
		cityfield.getStyleClass().add("textField");
		HBox cityhbox = new HBox(cityfield);
		cityfield.setId("form");

		// State
		ComboBox<String> statebox = new ComboBox<String>();
		statebox.setPromptText("State");
		statebox.getItems().addAll("A.C.T", "N.S.W", "N.T", "Queensland", "South Australia", "Tasmania", "Victoria",
				"Western Australia");
		statebox.getStyleClass().add("textField");
		HBox statehbox = new HBox(statebox);
		statebox.setId("form");

		// Postcode
		TextField pcodefield = new TextField();
		pcodefield.setPromptText("Post Code");
		pcodefield.getStyleClass().add("textField");
		HBox pcodehbox = new HBox(pcodefield);
		pcodefield.setId("form");

		// ERROR MESSAGES

		// Error text box
		Text emptyerrortxt = new Text("All fields must be filled.");
		HBox emptyerrorbox = new HBox();

		// Success box
		Text empaddedtxt = new Text("Employee has been successfully added! :)");
		HBox empaddedhbox = new HBox();

		// Email taken error box
		Text takenerrortxt = new Text("Email already in use.");
		HBox takenerrorbox = new HBox();

		// invalid first name
		Text fnamerrortxt = new Text("Please enter a first name using only letters, spaces, and hyphens");
		HBox fnameerrorbox = new HBox();

		// Surname error box
		Text snamerrortxt = new Text("Please enter a surname using only letters, spaces, and hyphens");
		HBox snameerrorbox = new HBox();

		// Invalid email
		Text emailerrortxt = new Text("Please enter an email in the correct format");
		HBox emailerrorbox = new HBox();

		// Phone too long
		Text phoneerrortxt = new Text("Please enter a valid phone number only 10 digits long");
		HBox phoneerrorbox = new HBox();

		// Street address invalid
		Text streeterrortxt = new Text("Please enter a street number followed by a street name");
		HBox streeterrorbox = new HBox();

		// City error
		Text cityerrortxt = new Text("Please enter a valid city");
		HBox cityerrorbox = new HBox();

		// Postcode error
		Text postcerrortxt = new Text("Please enter a valid postcode-state combination");
		HBox postcerrorbox = new HBox();

		// Submit button
		Button subbtn = new Button("Add");
		subbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				// checking to make sure all fields are filled
				if (cont.checkValues(fnamefield, snamefield, streetaddfield, pcodefield, contactnofield, emailfield,
						cityfield)) { // cont.empAddedMessage(empaddedhbox,
					// empaddedtxt);
					if (cont.validateEntries(fnamefield, fnamehbox, snamefield, snamehbox, streetaddfield,
							streetaddhbox, pcodefield, pcodehbox, contactnofield, contactnohbox, emailfield, emailhbox,
							cityfield, cityhbox, emptyerrortxt, emptyerrorbox, empaddedtxt, empaddedhbox, takenerrortxt,
							takenerrorbox, fnamerrortxt, fnameerrorbox, snamerrortxt, snameerrorbox, emailerrortxt,
							emailerrorbox, phoneerrortxt, phoneerrorbox, streeterrortxt, streeterrorbox, cityerrortxt,
							cityerrorbox, postcerrortxt, postcerrorbox, statebox.getValue())) {
						if (cont.addEmployee(fnamefield.getText().concat(" ").concat(snamefield.getText()),
								contactnofield.getText(), emailfield.getText(), streetaddfield.getText(),
								cityfield.getText(), statebox.getValue(), pcodefield.getText())) {
							if (!empaddedhbox.getChildren().contains(empaddedtxt)) {
								empaddedhbox.getChildren().add(empaddedtxt);
							}
						} else {
							if (empaddedhbox.getChildren().contains(empaddedtxt)) {
								empaddedhbox.getChildren().remove(empaddedtxt);
							}
						}
					}
				} else {
					// Perform validation if not empty
					cont.validateEntries(fnamefield, fnamehbox, snamefield, snamehbox, streetaddfield, streetaddhbox,
							pcodefield, pcodehbox, contactnofield, contactnohbox, emailfield, emailhbox, cityfield,
							cityhbox, emptyerrortxt, emptyerrorbox, empaddedtxt, empaddedhbox, takenerrortxt,
							takenerrorbox, fnamerrortxt, fnameerrorbox, snamerrortxt, snameerrorbox, emailerrortxt,
							emailerrorbox, phoneerrortxt, phoneerrorbox, streeterrortxt, streeterrorbox, cityerrortxt,
							cityerrorbox, postcerrortxt, postcerrorbox, statebox.getValue());
					if (empaddedhbox.getChildren().contains(empaddedtxt)) {
						empaddedhbox.getChildren().remove(empaddedtxt);
					}
				}
			}

		});
		subbtn.getStyleClass().add("orangebtn");

		// Add above elements to vertical box
		VBox addressInfo = new VBox(15, streetaddhbox, cityhbox, pcodehbox, statehbox, empaddedhbox);

		VBox errors = new VBox(10, emptyerrorbox,
				takenerrorbox, fnameerrorbox, snameerrorbox, emailerrorbox, phoneerrorbox, streeterrorbox, cityerrorbox,
				postcerrorbox);



		// Layout and styling
		heading.getStyleClass().add("main-heading");
		HBox form = new HBox(10, userInfo, addressInfo, errors);
		VBox body = new VBox(10, h1, form, subbtn);
		header.setId("headerbox");
		body.setId("mainPageVBox");
		VBox page = new VBox(header, body);
		page.setId("border");
		page.getStyleClass().add("loginpageBox");
		StackPane pane = new StackPane(page);

		// Set scene
		pane.getChildren().addAll();
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		System.out.println("The theme color is");
		System.out.println(AppData.colour);
		if (AppData.colour == Colour.BLUE) {
			scene.getStylesheets().add(getClass().getResource("/resources/display/css/scheme1.css").toExternalForm());
		} else if (AppData.colour == Colour.RED) {
			scene.getStylesheets().add(getClass().getResource("/resources/display/css/scheme2.css").toExternalForm());
		}
		stage.setScene(scene);
		stage.show();

	}

}
