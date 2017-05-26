package booking;

import accounts.AccountController;
import accounts.BusinessAccountController;
import accounts.BusinessAccountModel;
import accounts.UserAccountModel;
import businessHours.BusinessHoursController;
import businessHours.BusinessHoursView;
import employee.AddEmployeeView;
import employee.AvailabilitiesController;
import employee.EditAvailabilitiesView;
import employee.EmployeeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import menu.MainMenuController;
import menu.MainMenuView;
import service.TypeController;
import service.TypeModel;
import service.TypeView;
import utils.AppData;
import utils.ColourController;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
//import java.awt.Insets;
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
	 * Updates display of window shows list of bookings
	 */
	public void updateView(List<BookingModel> bookings) {
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


		heading.getStyleClass().add("main-heading");

		//Customer header
		//Available bookings
		Button availbtn = new Button("Available Bookings");
		availbtn.setUserData(cont);
		availbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BookingController bcont = new BookingController();
				bcont.setView(new BookingsView(stage));
				bcont.getView().setController(bcont);
				bcont.updateView();
			}
		});
		availbtn.getStyleClass().add("orangebtn");

		//My bookings
		Button mybookbtn = new Button("My Bookings");
		mybookbtn.setUserData(cont);
		mybookbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BookingController bcont = new BookingController();
				bcont.setView(new BookingsView(stage));
				bcont.getView().setController(bcont);
				bcont.updateCusView();
			}
		});
		mybookbtn.getStyleClass().add("orangebtn");

		HBox cusheader = new HBox(10, availbtn, mybookbtn, logoutbtn);

		// Past bookings switch
		Button switchbtn = null;
		if (AppData.CALLER instanceof BusinessAccountModel) {
			switchbtn = new Button("Past Bookings");
			switchbtn.getStyleClass().add("orangebtn");
			switchbtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					updateViewPast(cont.getPastBookings());
				}
			});
		}

		//Filters
		Text filterHeading = new Text("Filters");
		filterHeading.setId("bookingsh1");

		//Date
		DatePicker dpick = new DatePicker(LocalDate.of(2000, 01, 01));
		dpick.setId("form");
		Text dText = new Text("Date");
		VBox dBox = new VBox(dText,dpick);

		//Business
      BusinessAccountController bcont = new BusinessAccountController();
      ComboBox<String> bpick = new ComboBox<String>();
      Map<String,String> busmap;
      busmap = bcont.getBusinesses();
      bpick.getItems().addAll(busmap.keySet());
      bpick.setId("form");
      Text bText = new Text("Business");
      VBox bBox = new VBox(bText,bpick);

		//StartTime
		AvailabilitiesController acont = new AvailabilitiesController();
		ComboBox<String> spick = new ComboBox<String>();
		spick.getItems().add("none");
		spick.getItems().addAll(acont.getPossibleTimes());
		spick.setId("form");
		Text sText = new Text("Start Time");
		VBox sBox = new VBox(sText,spick);

		//FinishTime
		ComboBox<String> fpick = new ComboBox<String>();
		fpick.getItems().add("none");
		fpick.getItems().addAll(acont.getPossibleTimes());
		fpick.setId("form");
		Text fText = new Text("Finish Time");
		VBox fBox = new VBox(fText,fpick);

		//Type
		ComboBox<String> tpick = new ComboBox<String>();
		tpick.getItems().add("none");
		TypeController.getAllTypes().forEach(x->{
			tpick.getItems().add(x.getName());
		});
		tpick.setId("form");
		Text tText = new Text("Service");
		VBox tBox = new VBox(tText,tpick);

		//Employee
		EmployeeController econt = new EmployeeController();
		ComboBox<String> epick = new ComboBox<String>();
		epick.getItems().add("none");
		Map<String,String> empmap;
      if(AppData.CALLER instanceof UserAccountModel){
         empmap = econt.getAllEmployees();
      }else{
         empmap = econt.getEmployees(AppData.CALLER.getUsername());
      }
		epick.getItems().addAll(empmap.keySet());
		epick.setId("form");
		Text eText = new Text("Employee");
		VBox eBox = new VBox(eText,epick);

		//User
		AccountController ucont = new AccountController();
		ComboBox<String> upick = new ComboBox<String>();
		upick.getItems().add("none");
		ucont.getAllCustomers().forEach(x->{
			upick.getItems().add(x.getName());
		});
		upick.setId("form");
		Text uText = new Text("Customer");
		VBox uBox = new VBox(uText,upick);

		//Submit
      Button filterbtn = new Button("Filter");
      filterbtn.getStyleClass().add("orangebtn");
      filterbtn.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent e) {
            if(AppData.CALLER instanceof UserAccountModel){
            updateView(cont.filterBookings(cont.getBookings(),bpick.getSelectionModel().getSelectedItem(),dpick.getValue(),
                  spick.getSelectionModel().getSelectedItem(),
                  fpick.getSelectionModel().getSelectedItem(),
                  epick.getSelectionModel().getSelectedItem(),
                  upick.getSelectionModel().getSelectedItem(),
                  tpick.getSelectionModel().getSelectedItem()));
            }else{
               updateView(cont.filterBookings(cont.getBookings(),null,dpick.getValue(),
                     spick.getSelectionModel().getSelectedItem(),
                     fpick.getSelectionModel().getSelectedItem(),
                     epick.getSelectionModel().getSelectedItem(),
                     upick.getSelectionModel().getSelectedItem(),
                     tpick.getSelectionModel().getSelectedItem()));
            }
         }
      });
      HBox filterFields;
      if(AppData.CALLER instanceof UserAccountModel){
         filterFields = new HBox(dBox,bBox,sBox,fBox,tBox,eBox,uBox,filterbtn);
      }else{
         filterFields = new HBox(dBox,sBox,fBox,tBox,eBox,uBox,filterbtn);
      }
      VBox filters = new VBox(filterHeading,filterFields,filterbtn);

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

		VBox head;
		if (AppData.CALLER instanceof BusinessAccountModel) {
			switchbtn.setId("switchbtn");
			HBox head1 = new HBox(bookingstitle, switchbtn);
			head = new VBox(filters,head1);
		} else {
			head = new VBox(bookingstitle);
		}

		head.setId("bookingsHeader");

		// Bookings

		VBox bookingsList = new VBox(head);


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

		// Set layout
		bookingsList.setId("bookings-main");
		ScrollPane body = new ScrollPane(bookingsList);
		body.setId("mainPageVBox");
		body.getStyleClass().add("scroll-pane");

		VBox page;

		// Change header menu based on account type
		if (AppData.CALLER instanceof BusinessAccountModel) {
			HBox busheader = new HBox(10, viewbookbtn, addempbtn, editavailbtn, edittypebtn, bussettingsbtn, logoutbtn);
			busheader.setId("headerbox");
			page = new VBox(heading, busheader, body);
		} else {
			page = new VBox(heading, cusheader, body);
		}

		page.setId("border");
		page.getStyleClass().add("loginpageBox");

		StackPane pane = new StackPane(page);
		pane.getChildren().addAll();

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		if (ColourController.getAccountColour(AppData.CALLER.getUsername())) {
			System.out.println("The theme color is");
			System.out.println(AppData.colour);
			String themeLocal = "/resources/display/css/" + AppData.colour + ".css";
			scene.getStylesheets().add(getClass().getResource(themeLocal).toExternalForm());
		}
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Updates scene with past bookings
	 */
	public void updateViewPast(List<BookingModel> bookings) {

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

		HBox header = new HBox(10, viewbookbtn, addempbtn, editavailbtn, edittypebtn, bussettingsbtn, logoutbtn);
		header.setId("headerbox");
		heading.getStyleClass().add("main-heading");

		// Back to the future... bookings
		Button switchbtn = new Button("Future Bookings");
		switchbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cont.updateView();
			}
		});
		switchbtn.getStyleClass().add("orangebtn");


		//Filters
		Text filterHeading = new Text("Filters");
		filterHeading.setId("bookingsh1");

		//Date
		DatePicker dpick = new DatePicker(LocalDate.of(2000, 01, 01));
		dpick.setId("form");
		Text dText = new Text("Date");
		VBox dBox = new VBox(dText,dpick);

		//Business
		   BusinessAccountController bcont = new BusinessAccountController();
         ComboBox<String> bpick = new ComboBox<String>();
         Map<String,String> busmap;
         busmap = bcont.getBusinesses();
         bpick.getItems().addAll(busmap.keySet());
         bpick.setId("form");
         Text bText = new Text("Business");
         VBox bBox = new VBox(bText,bpick);

		//StartTime
		AvailabilitiesController acont = new AvailabilitiesController();
		ComboBox<String> spick = new ComboBox<String>();
		spick.getItems().addAll(acont.getPossibleTimes());
		spick.setId("form");
		Text sText = new Text("Start Time");
		VBox sBox = new VBox(sText,spick);

		//FinishTime
		ComboBox<String> fpick = new ComboBox<String>();
		spick.getItems().addAll(acont.getPossibleTimes());
		fpick.setId("form");
		Text fText = new Text("Finish Time");
		VBox fBox = new VBox(fText,fpick);

		//Type
		ComboBox<String> tpick = new ComboBox<String>();
		TypeController.getAllTypes().forEach(x->{
			tpick.getItems().add(x.getName());
		});
		tpick.setId("form");
		Text tText = new Text("Service");
		VBox tBox = new VBox(tText,tpick);

		//Employee
		EmployeeController econt = new EmployeeController();
		ComboBox<String> epick = new ComboBox<String>();
		Map<String,String> empmap;
		if(AppData.CALLER instanceof UserAccountModel){
		   empmap = econt.getAllEmployees();
		}else{
		   empmap = econt.getEmployees(AppData.CALLER.getUsername());
		}
		epick.getItems().addAll(empmap.keySet());
		epick.setId("form");
		Text eText = new Text("Employee");
		VBox eBox = new VBox(eText,epick);

		//User
		AccountController ucont = new AccountController();
		ComboBox<String> upick = new ComboBox<String>();
		ucont.getAllCustomers().forEach(x->{
			upick.getItems().add(x.getName());
		});
		upick.setId("form");
		Text uText = new Text("Customer");
		VBox uBox = new VBox(uText,upick);

		//Submit
		Button filterbtn = new Button("Filter");
		filterbtn.getStyleClass().add("orangebtn");
		filterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			   if(AppData.CALLER instanceof UserAccountModel){
				updateView(cont.filterBookings(cont.getBookings(),bpick.getSelectionModel().getSelectedItem(),dpick.getValue(),
						spick.getSelectionModel().getSelectedItem(),
						fpick.getSelectionModel().getSelectedItem(),
						epick.getSelectionModel().getSelectedItem(),
						upick.getSelectionModel().getSelectedItem(),
						tpick.getSelectionModel().getSelectedItem()));
			   }else{
			      updateView(cont.filterBookings(cont.getBookings(),null,dpick.getValue(),
			            spick.getSelectionModel().getSelectedItem(),
			            fpick.getSelectionModel().getSelectedItem(),
			            epick.getSelectionModel().getSelectedItem(),
			            upick.getSelectionModel().getSelectedItem(),
			            tpick.getSelectionModel().getSelectedItem()));
			   }
			}
		});
		HBox filterFields;
		if(AppData.CALLER instanceof UserAccountModel){
		   filterFields = new HBox(dBox,bBox,sBox,fBox,tBox,eBox,uBox,filterbtn);
		}else{
		   filterFields = new HBox(dBox,sBox,fBox,tBox,eBox,uBox,filterbtn);
		}
		VBox filters = new VBox(filterHeading,filterFields,filterbtn);

		// Heading
		Text h1 = new Text("Past Bookings");
		h1.setId("bookingsh1");
		HBox bookingstitle = new HBox(h1);
		bookingstitle.setId("bookingstitle");

		HBox head = new HBox(bookingstitle, switchbtn);
		head.setId("bookingsHeader");


		VBox bookingsList = new VBox(filters, head);

		// Iterate through past bookings and add them to bookingsList
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

		// Set Layout
		bookingsList.setId("bookings-main");
		ScrollPane body = new ScrollPane(bookingsList);
		body.setId("mainPageVBox");
		body.getStyleClass().add("scroll-pane");

		VBox page = new VBox(heading, header, body);
		page.setId("border");
		page.getStyleClass().add("loginpageBox");

		StackPane pane = new StackPane(page);
		pane.getChildren().addAll();

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		if (ColourController.getAccountColour(AppData.CALLER.getUsername())) {
			System.out.println("The theme color is");
			System.out.println(AppData.colour);
			String themeLocal = "/resources/display/css/" + AppData.colour + ".css";
			scene.getStylesheets().add(getClass().getResource(themeLocal).toExternalForm());
		}
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Make a Booking pop up page
	 *
	 * @param book Booking selected
	 * @param parcont Parent controller
	 */
	public void assignToBooking(BookingModel book, BookingController parcont) {
		Stage popup = new Stage();
		popup.initModality(Modality.WINDOW_MODAL);
		popup.initOwner(parcont.getView().stage);
		StackPane pane = new StackPane();
		if (AppData.CALLER instanceof BusinessAccountModel) {
			// Heading
			Text h1 = new Text("Make a Booking");
			h1.setId("bookingsh1");
			AccountController acont = new AccountController();

			// Appointment details
			Label dlbl = new Label("Date: ");
			Text dtxt = new Text(book.getDate().toString());
			HBox dbox = new HBox(dlbl, dtxt);
			Label stlbl = new Label("Start Time: ");
			Text sttxt = new Text(book.getStartTime().toString());
			HBox stbox = new HBox(stlbl, sttxt);
			Label ftlbl = new Label("Finish Time: ");
			Text fttxt = new Text(book.getFinishTime().toString());
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
			Label cuslbl = new Label("Customer:");
			cuslbl.getStyleClass().add("booking-label");
			ComboBox<String> selector = new ComboBox<String>();
			selector.setId("form");
			map.forEach((k, v) -> selector.getItems().add(k));
			VBox select = new VBox(cuslbl, selector);

			// Type selector
			TypeController tcont = new TypeController();
			tcont.setEmp(book.getEmployee());
			Label typelbl = new Label("Service:");
			typelbl.getStyleClass().add("booking-label");
			ComboBox<String> typeselector = new ComboBox<String>();
			typeselector.setId("form");
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
			cancel.getStyleClass().add("orangebtn-small");

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
						close.getStyleClass().add("orangebtn-small");
						VBox vbox = new VBox(conftxt, close);
						vbox.setId("pop-up");
						Scene confscene = new Scene(vbox);
						confscene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
						if (ColourController.getAccountColour(AppData.CALLER.getUsername())) {
							System.out.println("The theme color is");
							System.out.println(AppData.colour);
							String themeLocal = "/resources/display/css/" + AppData.colour + ".css";
							confscene.getStylesheets().add(getClass().getResource(themeLocal).toExternalForm());
						}
						popup.setScene(confscene);
						parcont.updateView();
					} else {
						// False message
					}
				}
			});
			submit.getStyleClass().add("orangebtn-small");

			HBox buttons = new HBox(10, submit, cancel);

			// Add to pane
			VBox all = new VBox(10, h1, dets, select, typeselect, buttons);
			pane.getChildren().addAll(all);
		} else {
			// Heading
			Text h1 = new Text("Make a Booking");
			h1.setId("bookingsh1");

			// Appointment details
			Label dlbl = new Label("Date: ");
			Text dtxt = new Text(book.getDate().toString());
			HBox dbox = new HBox(dlbl, dtxt);
			Label stlbl = new Label("Start Time: ");
			Text sttxt = new Text(book.getStartTime().toString());
			HBox stbox = new HBox(stlbl, sttxt);
			Label ftlbl = new Label("Finish Time: ");
			Text fttxt = new Text(book.getFinishTime().toString());
			HBox ftbox = new HBox(ftlbl, fttxt);
			Label elbl = new Label("Employee: ");
			Text etxt = new Text(cont.getNameFromEmail(book.getEmployee()));
			HBox ebox = new HBox(elbl, etxt);
			VBox dets = new VBox(dbox, stbox, ftbox, ebox);

			// Type selector
			TypeController tcont = new TypeController();
			tcont.setEmp(book.getEmployee());
			Label typelbl = new Label("Service:");
			ComboBox<String> typeselector = new ComboBox<String>();
			typeselector.setId("form");
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
			cancel.getStyleClass().add("orangebtn-small");

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
						close.getStyleClass().add("orangebtn-small");
						close.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								popup.close();
							}
						});
						VBox vbox = new VBox(conftxt, close);
						vbox.setId("pop-up");
						Scene confscene = new Scene(vbox);
						confscene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
						if (ColourController.getAccountColour(AppData.CALLER.getUsername())) {
							System.out.println("The theme color is");
							System.out.println(AppData.colour);
							String themeLocal = "/resources/display/css/" + AppData.colour + ".css";
							confscene.getStylesheets().add(getClass().getResource(themeLocal).toExternalForm());
						}
						popup.setScene(confscene);
						parcont.updateView();
					} else {
						// False message
					}
				}
			});
			submit.getStyleClass().add("orangebtn-small");

			HBox buttons = new HBox(10, cancel, submit);

			// Add to pane
			VBox body = new VBox(10, h1, dets, typeselect, buttons);
			pane.getChildren().addAll(body);
		}

		// Styling


		// Set scene for making booking
		pane.setId("pop-up");
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		if (ColourController.getAccountColour(AppData.CALLER.getUsername())) {
			System.out.println("The theme color is");
			System.out.println(AppData.colour);
			String themeLocal = "/resources/display/css/" + AppData.colour + ".css";
			scene.getStylesheets().add(getClass().getResource(themeLocal).toExternalForm());
		}
		popup.setScene(scene);
		popup.show();
	}

	/**
	 * Updates display of window showing only customers own bookings
	 */
	public void updateCusView() {
		// Int scrollpane
		sp = new ScrollPane();

		// Header init
		Text heading = new Text(AppData.CALLER.getName());
		heading.getStyleClass().add("main-heading");
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

		//Customer header
		//Available bookings
		Button availbtn = new Button("Available Bookings");
		availbtn.setUserData(cont);
		availbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BookingController bcont = new BookingController();
				bcont.setView(new BookingsView(stage));
				bcont.getView().setController(bcont);
				bcont.updateView();
			}
		});
		availbtn.getStyleClass().add("orangebtn");

		//My bookings
		Button mybookbtn = new Button("My Bookings");
		mybookbtn.setUserData(cont);
		mybookbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BookingController bcont = new BookingController();
				bcont.setView(new BookingsView(stage));
				bcont.getView().setController(bcont);
				bcont.updateCusView();
			}
		});
		mybookbtn.getStyleClass().add("orangebtn");

		HBox cusheader = new HBox(10, availbtn, mybookbtn, logoutbtn);
		cusheader.setId("headerbox");

		// Heading
		Text h1 = new Text("My Appoinments");

		h1.setId("bookingsh1");
		HBox bookingstitle = new HBox(h1);
		bookingstitle.setId("bookingstitle");

		HBox head;
		head = new HBox(bookingstitle);

		head.setId("bookingsHeader");

		// Bookings

		VBox bookingsList = new VBox(head);

		List<BookingModel> bookings = cont.filterBookings(cont.getBookings(),null,
				null, null, null, null,
				AppData.CALLER.getUsername().toString(), null);

		// Iterates through bookings
		bookings.forEach(booking -> {
			// If user is of type customer and booking is filled, skip this
			// iteration
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
			Text employee = new Text("Employee: " + cont.getNameFromEmail(booking.getEmployee()).toString());

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
					Stage popup = new Stage();
					cancelBooking(cont,booking);
				}
			});

			// Add current booking to bookings list
			bookingsList.getChildren().add(bookingBox);

		});

		// Set layout
		bookingsList.setId("bookings-main");

		ScrollPane body = new ScrollPane(bookingsList);
		body.setId("mainPageVBox");
		body.getStyleClass().add("scroll-pane");

		VBox page = new VBox(heading, cusheader, body);

		page.setId("border");
		page.getStyleClass().add("loginpageBox");

		StackPane pane = new StackPane(page);
		pane.getChildren().addAll();

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		if (ColourController.getAccountColour(AppData.CALLER.getUsername())) {
			System.out.println("The theme color is");
			System.out.println(AppData.colour);
			String themeLocal = "/resources/display/css/" + AppData.colour + ".css";
			scene.getStylesheets().add(getClass().getResource(themeLocal).toExternalForm());
		}
		stage.setScene(scene);
		stage.show();

	}

	/**Window allowing customers to cancel their bookings
	 *
	 * @param parcont Parent controller
	 * @param booking Selected booking
	 */

	public void cancelBooking(BookingController parcont,BookingModel booking){
		Stage popup = new Stage();
		popup.initModality(Modality.WINDOW_MODAL);
		popup.initOwner(parcont.getView().stage);
		Text heading = new Text("Cancel your booking?");

		// Available Booking data
		Text startTime = new Text("Start: " + booking.getStartTime().toString());
		Text finishTime = new Text("End: " + booking.getFinishTime().toString());
		Text employee = new Text("Employee: " + cont.getNameFromEmail(booking.getEmployee()));

		VBox who = new VBox(employee);
		VBox bookingType = new VBox();

		// Checks if booking is filled and displays customer and type if it
		// is
		Text customer = new Text("Customer: " + booking.getUser());
		Text type = new Text("Type: " + booking.getType());
		who.getChildren().add(customer);
		bookingType.getChildren().add(type);
		VBox when = new VBox(startTime, finishTime);

		who.getStyleClass().add("bookingcol");
		when.getStyleClass().add("bookingcol");
		bookingType.getStyleClass().add("bookingcol");

		HBox bookingBox = new HBox(who, when, bookingType);
		bookingBox.setId("bookingBox");

		// Cancel button
		Button close = new Button("Keep booking");
		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				updateCusView();
			}
		});
		close.getStyleClass().add("orangebtn-small");

		// Submit button
		Button cancel = new Button("Cancel booking");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BookingController bcont = new BookingController();
				bcont.cancelBooking(booking.getId());
				updateCusView();
			}
		});

		cancel.getStyleClass().add("orangebtn-small");


		//Boxing
		HBox buttons= new HBox(close,cancel);
		VBox vbox = new VBox(heading,bookingBox,buttons);

		//Layout
		Scene scene = new Scene(vbox);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		if (ColourController.getAccountColour(AppData.CALLER.getUsername())) {
			System.out.println("The theme color is");
			System.out.println(AppData.colour);
			String themeLocal = "/resources/display/css/" + AppData.colour + ".css";
			scene.getStylesheets().add(getClass().getResource(themeLocal).toExternalForm());
		}
		stage.setScene(scene);
		stage.show();
	}

}
