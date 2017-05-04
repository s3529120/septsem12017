package booking;

import accounts.AccountController;
import accounts.BusinessAccountModel;
import accounts.UserAccountModel;
import employee.AddEmployeeView;
import employee.EmployeeController;
import employee.roster.AvailabilitiesController;
import employee.roster.EditAvailabilitiesView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import menu.main.MainMenuController;
import menu.main.MainMenuView;
import service.TypeController;
import service.TypeModel;
import service.TypeView;
import utils.AppData;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookingsView {

	private ScrollPane sp;
	private String currentdate;

	private BookingController cont;
	public Stage stage;

	public BookingsView(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Sets associated controller
	 * 
	 * @param controller
	 * @return
	 */
	public Boolean setController(BookingController controller) {
		this.cont = controller;
		return true;
	}

	/**
	 * Updates display of window
	 */
	public void updateView() {
		// Int scrollpane
		sp = new ScrollPane();
		// Header init
		Text heading = new Text("Booking Site");
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

		// Edit availability
		Button editavailbtn = new Button("Edit Employee");
		editavailbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AvailabilitiesController acont = new AvailabilitiesController();
				acont.setView(new EditAvailabilitiesView(stage));
				acont.getView().setController(acont);
				acont.updateView();
			}
		});

		// Edit type
		Button edittypebtn = new Button("Edit Type");
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

		// View Bookings
		Button viewbookbtn = new Button("View Bookings");
		viewbookbtn.setUserData(cont);
		viewbookbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BookingController bcont = new BookingController();
				bcont.setView(new BookingsView(new Stage()));
				bcont.getView().setController(bcont);
				bcont.updateView();
			}
		});
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
		HBox header = new HBox(heading, addempbtn, editavailbtn, edittypebtn, logoutbtn);

		// Past bookings switch
		Button switchbtn = null;
		if (AppData.CALLER instanceof BusinessAccountModel) {
			switchbtn = new Button("Past Bookings");
			switchbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					updateViewPast();
				}
			});
		}

		// Heading
		Text h1;
		if (AppData.CALLER instanceof BusinessAccountModel) {
			h1 = new Text("Bookings");
		} else {
			h1 = new Text("Available Appoinments");
		}

		h1.setId("bookingsh1");
		HBox bookingstitle = new HBox(h1);
		bookingstitle.setId("bookingstitle");

		HBox head;
		if (AppData.CALLER instanceof BusinessAccountModel) {
			switchbtn.setId("switchbtn");
			head = new HBox(bookingstitle, switchbtn);
		} else {
			head = new HBox(bookingstitle);
		}
		head.setId("bookingsHeader");

		// Bookings

		VBox bookingsList = new VBox(head);

		// Gets all bookings from controller
		List<BookingModel> bookings = cont.getBookings();

		// Iterates through bookings
		bookings.forEach(booking -> {
			// If user is of type customer and booking is filled, skip this
			// iteration
			if (booking.getUser().compareToIgnoreCase("Unfilled") != 0 && AppData.CALLER instanceof UserAccountModel) {
				return;
			}
			String newdate = booking.getDate().toString();
			if (currentdate == null || !currentdate.equals(newdate)) {
				currentdate = newdate;
				Text date = new Text(currentdate);
				date.setId("bookingDate");
				HBox datebox = new HBox(date);
				datebox.setId("datebox");
				bookingsList.getChildren().add(datebox);
			}

			// Available Booking data
			Text startTime = new Text("Start: " + booking.getStartTime().toString());
			Text finishTime = new Text("End: " + booking.getFinishTime().toString());
			Text employee = new Text("Employee: " + cont.getNameFromEmail(booking.getEmployee()));

			VBox who = new VBox(employee);
			VBox bookingType = new VBox();

			// Checks if booking is filled and displays customer and type if it
			// is
			if (booking.getUser() != "Unfilled") {
				Text customer = new Text("Customer: " + booking.getUser());
				Text type = new Text("Type: " + booking.getType());
				who.getChildren().add(customer);
				bookingType.getChildren().add(type);
			}

			VBox when = new VBox(startTime, finishTime);

			who.getStyleClass().add("bookingcol");
			when.getStyleClass().add("bookingcol");
			bookingType.getStyleClass().add("bookingcol");

			HBox bookingBox = new HBox(who, when, bookingType);
			bookingBox.setId("bookingBox");

			// Add "Make a booking" link to booking box
			bookingBox.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					assignToBooking(booking, cont);
				}
			});

			// Add current booking to bookings list
			bookingsList.getChildren().add(bookingBox);

		});
		HBox bookingscontainer = new HBox(bookingsList);
		bookingscontainer.setId("bookingscontainer");

		VBox main;

		// Change header menu based on account type
		if (AppData.CALLER instanceof BusinessAccountModel) {
			main = new VBox(header, heading, bookingscontainer);
		} else {
			main = new VBox(heading, logoutbtn, bookingscontainer);
		}
		main.setId("bookingsMain");

		// Set scene
		sp.setVmax(440);
		sp.setPrefSize(115, 150);
		sp.setContent(main);
		Scene scene = new Scene(sp, 750, 500);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Updates scene with past bookings
	 */
	public void updateViewPast() {

		// Header init
		sp = new ScrollPane();
		Text heading = new Text("Booking Site");
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

		// Edit availability
		Button editavailbtn = new Button("Edit Employee");
		editavailbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AvailabilitiesController acont = new AvailabilitiesController();
				acont.setView(new EditAvailabilitiesView(stage));
				acont.getView().setController(acont);
				acont.updateView();
			}
		});

		// Edit type
		Button edittypebtn = new Button("Edit Type");
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

		// View Bookings
		Button viewbookbtn = new Button("View Bookings");
		viewbookbtn.setUserData(cont);
		viewbookbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BookingController bcont = new BookingController();
				bcont.setView(new BookingsView(new Stage()));
				bcont.getView().setController(bcont);
				bcont.updateView();
			}
		});
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
		HBox header = new HBox(heading, addempbtn, editavailbtn, edittypebtn, logoutbtn);

		// Back to the future... bookings
		Button switchbtn = new Button("Future Bookings");
		switchbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				updateView();
			}
		});

		// Heading
		Text h1 = new Text("Past Bookings");
		h1.setId("bookingsh1");
		HBox bookingstitle = new HBox(h1);
		bookingstitle.setId("bookingstitle");

		HBox head = new HBox(bookingstitle, switchbtn);
		head.setId("bookingsHeader");

		// Bookings

		// Get all past bookings from controller
		List<BookingModel> bookings = cont.getPastBookings();

		VBox bookingsList = new VBox();

		// Iterate through past bookings
		bookings.forEach(booking -> {

			String newdate = booking.getDate().toString();
			if (currentdate == null || !currentdate.equals(newdate)) {
				currentdate = newdate;
				Text date = new Text(currentdate);
				date.setId("bookingDate");
				HBox datebox = new HBox(date);
				datebox.setId("datebox");
				bookingsList.getChildren().add(datebox);
			}

			// Display booking data
			Text startTime = new Text("Start: " + booking.getStartTime().toString());
			Text finishTime = new Text("End: " + booking.getFinishTime().toString());
			Text employee = new Text("Employee: " + cont.getNameFromEmail(booking.getEmployee()));

			VBox who = new VBox(employee);
			VBox bookingType = new VBox();

			if (booking.getUser() != "Unfilled") {
				Text customer = new Text("Customer: " + booking.getUser());
				Text type = new Text("Type: " + booking.getType());
				who.getChildren().add(customer);
				bookingType.getChildren().add(type);
			}

			VBox when = new VBox(startTime, finishTime);

			who.getStyleClass().add("bookingcol");
			when.getStyleClass().add("bookingcol");
			bookingType.getStyleClass().add("bookingcol");

			HBox bookingBox = new HBox(who, when, bookingType);
			bookingBox.setId("bookingBox");
			bookingBox.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					assignToBooking(booking, cont);
				}
			});

			// Add this booking data to booking list
			bookingsList.getChildren().add(bookingBox);

		});
		HBox bookingscontainer = new HBox(bookingsList);
		bookingscontainer.setId("bookingscontainer");
		// Layout

		VBox main = new VBox(header, head, bookingscontainer);
		main.setId("bookingsMain");

		// Set scene for past bookings
		sp.setVmax(440);
		sp.setPrefSize(115, 150);
		sp.setContent(main);
		Scene scene = new Scene(sp, 750, 500);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Make a Booking pop up page
	 * 
	 * @param book
	 * @param parcont
	 */
	public void assignToBooking(BookingModel book, BookingController parcont) {
		Stage popup = new Stage();
		popup.initModality(Modality.WINDOW_MODAL);
		popup.initOwner(parcont.getView().stage);
		StackPane pane = new StackPane();
		if (AppData.CALLER instanceof BusinessAccountModel) {
			// Heading
			Text h1 = new Text("Assign customer to booking.");
			AccountController acont = new AccountController();

			// Appointment details
			Label dlbl = new Label("Date: ");
			Text dtxt = new Text(book.getDate().toString());
			HBox dbox = new HBox(dlbl, dtxt);
			Label stlbl = new Label("Start Time: ");
			Text sttxt = new Text(book.getDate().toString());
			HBox stbox = new HBox(stlbl, sttxt);
			Label ftlbl = new Label("Finish Time: ");
			Text fttxt = new Text(book.getDate().toString());
			HBox ftbox = new HBox(ftlbl, fttxt);
			Label elbl = new Label("Employee: ");
			Text etxt = new Text(cont.getNameFromEmail(book.getEmployee()));
			HBox ebox = new HBox(elbl, etxt);
			VBox dets = new VBox(dbox, stbox, ftbox, ebox);

			// List initialization
			List<UserAccountModel> customers = acont.getAllCustomers();
			Map<String, String> map = new HashMap<String, String>();
			customers.forEach(x -> {
				map.put(x.getName(), x.getUsername());
			});

			// Customer selector
			Label cuslbl = new Label("Please select the customer you wish to assign.");
			ComboBox<String> selector = new ComboBox<String>();
			map.forEach((k, v) -> selector.getItems().add(k));
			VBox select = new VBox(cuslbl, selector);

			// Type selector
			TypeController tcont = new TypeController();
			tcont.setEmp(book.getEmployee());
			Label typelbl = new Label("Please select the appointment type you desire to book.");
			ComboBox<String> typeselector = new ComboBox<String>();
			List<TypeModel> settypes = tcont.getSetTypes();
			settypes.forEach(x -> typeselector.getItems().add(x.getName()));
			VBox typeselect = new VBox(typelbl, typeselector);

			// Cancel button
			Button cancel = new Button("Cancel");
			cancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					popup.close();
				}
			});

			// Submit button
			Button submit = new Button("Submit");
			
			submit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					BookingController cont = new BookingController();
					if (cont.setUser(book, map.get(selector.getSelectionModel().getSelectedItem()), TypeController
							.getModelByName(settypes, typeselector.getSelectionModel().getSelectedItem()))) {
						// Confirm text
						Text conftxt = new Text("Booking Confirmed!");
						// Close button
						Button close = new Button("Close");
						close.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								popup.close();
							}
						});
						VBox vbox = new VBox(conftxt, close);
						Scene confscene = new Scene(vbox);
						popup.setScene(confscene);
						parcont.updateView();
					} else {
						// False message
					}
				}
			});
			// Add to pane
			VBox all = new VBox(h1, dets, select, typeselect, cancel, submit);
			pane.getChildren().addAll(all);
		} else {
			// Heading
			Text h1 = new Text("Would you like to confirm this Booking?");

			// Appointment details
			Label dlbl = new Label("Date: ");
			Text dtxt = new Text(book.getDate().toString());
			HBox dbox = new HBox(dlbl, dtxt);
			Label stlbl = new Label("Start Time: ");
			Text sttxt = new Text(book.getDate().toString());
			HBox stbox = new HBox(stlbl, sttxt);
			Label ftlbl = new Label("Finish Time: ");
			Text fttxt = new Text(book.getDate().toString());
			HBox ftbox = new HBox(ftlbl, fttxt);
			Label elbl = new Label("Employee: ");
			Text etxt = new Text(cont.getNameFromEmail(book.getEmployee()));
			HBox ebox = new HBox(elbl, etxt);
			VBox dets = new VBox(dbox, stbox, ftbox, ebox);

			// Type selector
			TypeController tcont = new TypeController();
			tcont.setEmp(book.getEmployee());
			Label typelbl = new Label("Please select the appointment type you desire.");
			ComboBox<String> typeselector = new ComboBox<String>();
			List<TypeModel> settypes = tcont.getSetTypes();
			settypes.forEach(x -> typeselector.getItems().add(x.getName()));
			VBox typeselect = new VBox(typelbl, typeselector);

			// Cancel button
			Button cancel = new Button("Cancel");
			cancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					popup.close();
				}
			});

			// Submit button
			Button submit = new Button("Submit");
			submit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					BookingController cont = new BookingController();
					if (cont.setUser(book, AppData.CALLER.getUsername(), TypeController.getModelByName(settypes,
							typeselector.getSelectionModel().getSelectedItem()))) {
						// Confirm text
						Text conftxt = new Text("Booking Confirmed!");
						// Close button
						Button close = new Button("Close");
						close.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								popup.close();
							}
						});
						VBox vbox = new VBox(conftxt, close);
						Scene confscene = new Scene(vbox);
						popup.setScene(confscene);
						parcont.updateView();
					} else {
						// False message
					}
				}
			});
			// Add to pane
			VBox all = new VBox(h1, dets, typeselect, cancel, submit);
			pane.getChildren().addAll(all);
		}
		// Set scene for making booking
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		popup.setScene(scene);
		popup.show();
	}

}
