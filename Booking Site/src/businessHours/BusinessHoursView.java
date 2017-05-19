package businessHours;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

import accountOperations.UserRegistrationController;
import accountOperations.UserRegistrationView;
import booking.BookingController;
import booking.BookingsView;
import employee.AddEmployeeView;
import employee.AvailabilitiesController;
import employee.EditAvailabilitiesView;
import employee.EmployeeController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
import menu.MainMenuController;
import menu.MainMenuView;
import service.TypeController;
import service.TypeView;
import utils.AppData;
import utils.ColourController;

public class BusinessHoursView
{

	private BusinessHoursController cont;

	public BusinessHoursView(BusinessHoursController cont){
		this.cont=cont;
	}

	public void updateView(Stage stage){
		// Prompt texts

		Text timeerrortxt = new Text("Invalid time selection");
		HBox timenerrorbox = new HBox();

		HBox donebox = new HBox();

		Text insertconfirm = new Text("");

		// Title
		Text pageTitle = new Text("Manage Opening Hours");
		pageTitle.getStyleClass().add("main-heading");

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

		// Select Employee
		// instructions
		Label instructions = new Label("To indicate no openings for a given day, set boths times to 00:00");


		//Colour selector
		ComboBox<String> col = new ComboBox<String>();
		col.getItems().addAll("BLUE","RED","YELLOW","BLACK","WHITE");

		Button colsub = new Button("Change Scheme");
		// Register button
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ColourController.setAccountColour(AppData.CALLER.getUsername(), 
						ColourController.colFromString(col.getSelectionModel().getSelectedItem()));
				updateView(stage);
				System.out.print(AppData.colour.toString());
			}
		});
		HBox colsel = new HBox(col,colsub);

		// top box construction
		VBox titleAndEmployee = new VBox(pageTitle, instructions,colsel);
		pageTitle.getStyleClass().add("main-heading");
		HBox topBox = new HBox(titleAndEmployee);

		// Lots of text, because apparently i cant reuse the same ones
		Text s1 = new Text("Start");
		Text e1 = new Text("End");
		Text s2 = new Text("Start");
		Text e2 = new Text("End");
		Text s3 = new Text("Start");
		Text e3 = new Text("End");
		Text s4 = new Text("Start");
		Text e4 = new Text("End");
		Text s5 = new Text("Start");
		Text e5 = new Text("End");
		Text s6 = new Text("Start");
		Text e6 = new Text("End");
		Text s7 = new Text("Start");
		Text e7 = new Text("End");

		// setting up days
		DayOfWeek sunday = DayOfWeek.valueOf("SUNDAY");
		DayOfWeek monday = DayOfWeek.valueOf("MONDAY");
		DayOfWeek tuesday = DayOfWeek.valueOf("TUESDAY");
		DayOfWeek wednesday = DayOfWeek.valueOf("WEDNESDAY");
		DayOfWeek thursday = DayOfWeek.valueOf("THURSDAY");
		DayOfWeek friday = DayOfWeek.valueOf("FRIDAY");
		DayOfWeek saturday = DayOfWeek.valueOf("SATURDAY");
		DayOfWeek[] days = { sunday, monday, tuesday, wednesday, thursday, friday, saturday };

		// Setting up boxes
		ComboBox<String> sundayStartTime = new ComboBox<String>();
		ComboBox<String> sundayEndTime = new ComboBox<String>();
		ComboBox<String> mondayStartTime = new ComboBox<String>();
		ComboBox<String> mondayEndTime = new ComboBox<String>();
		ComboBox<String> tuesdayStartTime = new ComboBox<String>();
		ComboBox<String> tuesdayEndTime = new ComboBox<String>();
		ComboBox<String> wednesdayStartTime = new ComboBox<String>();
		ComboBox<String> wednesdayEndTime = new ComboBox<String>();
		ComboBox<String> thursdayStartTime = new ComboBox<String>();
		ComboBox<String> thursdayEndTime = new ComboBox<String>();
		ComboBox<String> fridayStartTime = new ComboBox<String>();
		ComboBox<String> fridayEndTime = new ComboBox<String>();
		ComboBox<String> saturdayStartTime = new ComboBox<String>();
		ComboBox<String> saturdayEndTime = new ComboBox<String>();

		//applying CSS
		sundayStartTime.setId("form");
		sundayEndTime.setId("form");
		mondayStartTime.setId("form");
		mondayEndTime.setId("form");
		tuesdayStartTime.setId("form");
		tuesdayEndTime.setId("form");
		wednesdayStartTime.setId("form");
		wednesdayEndTime.setId("form");
		thursdayStartTime.setId("form");
		thursdayEndTime.setId("form");
		fridayStartTime.setId("form");
		fridayEndTime.setId("form");
		saturdayStartTime.setId("form");
		saturdayEndTime.setId("form");

		// creating labels
		Label sundayText = new Label("Sunday");
		Label mondayText = new Label("Monday");
		Label tuesdayText = new Label("Tuesday");
		Label wednesdayText = new Label("Wednesday");
		Label thursdayText = new Label("Thursday");
		Label fridayText = new Label("Friday");
		Label saturdayText = new Label("Saturday");

		// populating lists, id like to do this in a loop, but apparently cant
		// create an array of combo boxes
		sundayStartTime.getItems().addAll(cont.getPossibleTimes());
		sundayEndTime.getItems().addAll(cont.getPossibleTimes());
		mondayStartTime.getItems().addAll(cont.getPossibleTimes());
		mondayEndTime.getItems().addAll(cont.getPossibleTimes());
		tuesdayStartTime.getItems().addAll(cont.getPossibleTimes());
		tuesdayEndTime.getItems().addAll(cont.getPossibleTimes());
		wednesdayStartTime.getItems().addAll(cont.getPossibleTimes());
		wednesdayEndTime.getItems().addAll(cont.getPossibleTimes());
		thursdayStartTime.getItems().addAll(cont.getPossibleTimes());
		thursdayEndTime.getItems().addAll(cont.getPossibleTimes());
		fridayStartTime.getItems().addAll(cont.getPossibleTimes());
		fridayEndTime.getItems().addAll(cont.getPossibleTimes());
		saturdayStartTime.getItems().addAll(cont.getPossibleTimes());
		saturdayEndTime.getItems().addAll(cont.getPossibleTimes());

		// Setting default selections
		cont.setSelection(sunday, sundayStartTime,
				sundayEndTime);
		cont.setSelection(monday, mondayStartTime,
				mondayEndTime);
		cont.setSelection(tuesday, tuesdayStartTime,
				tuesdayEndTime);
		cont.setSelection(wednesday,wednesdayStartTime,
				wednesdayEndTime);
		cont.setSelection(thursday,thursdayStartTime,
				thursdayEndTime);
		cont.setSelection(friday,fridayStartTime,
				fridayEndTime);
		cont.setSelection(saturday,saturdayStartTime,
				saturdayEndTime);

		// creating boxes
		VBox sundayBox = new VBox(sundayText, s1, sundayStartTime, e1, sundayEndTime);
		VBox mondayBox = new VBox(mondayText, s2, mondayStartTime, e2, mondayEndTime);
		VBox tuesdayBox = new VBox(tuesdayText, s3, tuesdayStartTime, e3, tuesdayEndTime);
		VBox wednesdayBox = new VBox(wednesdayText, s4, wednesdayStartTime, e4, wednesdayEndTime);
		VBox thursdayBox = new VBox(thursdayText, s5, thursdayStartTime, e5, thursdayEndTime);
		VBox fridayBox = new VBox(fridayText, s6, fridayStartTime, e6, fridayEndTime);
		VBox saturdayBox = new VBox(saturdayText, s7, saturdayStartTime, e7, saturdayEndTime);

		// setting styles
		sundayBox.getStyleClass().add("daybox");
		mondayBox.getStyleClass().add("daybox");
		tuesdayBox.getStyleClass().add("daybox");
		wednesdayBox.getStyleClass().add("daybox");
		thursdayBox.getStyleClass().add("daybox");
		fridayBox.getStyleClass().add("daybox");
		saturdayBox.getStyleClass().add("daybox");

		// putt em all together
		HBox dayBox = new HBox(sundayBox, mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox);


		// Save
		Button savebtn = new Button("Save");
		savebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// Creating arrays of the day to loop through
				// this might not be pretty but i dont know a datatype to suit
				String[] startTimes = { sundayStartTime.getSelectionModel().getSelectedItem().toString(),
						mondayStartTime.getSelectionModel().getSelectedItem().toString(),
						tuesdayStartTime.getSelectionModel().getSelectedItem().toString(),
						wednesdayStartTime.getSelectionModel().getSelectedItem().toString(),
						thursdayStartTime.getSelectionModel().getSelectedItem().toString(),
						fridayStartTime.getSelectionModel().getSelectedItem().toString(),
						saturdayStartTime.getSelectionModel().getSelectedItem().toString(), };
				String[] endTimes = { sundayEndTime.getSelectionModel().getSelectedItem().toString(),
						mondayEndTime.getSelectionModel().getSelectedItem().toString(),
						tuesdayEndTime.getSelectionModel().getSelectedItem().toString(),
						wednesdayEndTime.getSelectionModel().getSelectedItem().toString(),
						thursdayEndTime.getSelectionModel().getSelectedItem().toString(),
						fridayEndTime.getSelectionModel().getSelectedItem().toString(),
						saturdayEndTime.getSelectionModel().getSelectedItem().toString(), };
				// Preparing string for prompts
				String prompt = "Time saved for ";
				int daysSaved = 0;
				for (int i = 0; i < 7; i++) {
					cont.addBusinessHours(days[i],startTimes[i], endTimes[i]);
					insertconfirm.getText().replaceAll(".*?", "Trading Hours updated");
					if (daysSaved == 0) {
						prompt = prompt + days[i];
					} else {
						prompt = prompt + ", " + days[i];
						prompt.toLowerCase();
					}
					daysSaved++;
				}
				donebox.getChildren().clear();
				if (daysSaved == 7) {
					prompt = "Times saved for all days";
				}
				if (daysSaved > 0) {
					Text successtxt = new Text(prompt);
					donebox.getChildren().add(successtxt);
				}
			}
		});
		savebtn.getStyleClass().add("orangebtn");

		// box Construction
		HBox bottom = new HBox(savebtn, donebox);
		VBox body = new VBox(topBox, dayBox, bottom);
		body.setId("mainPageVBox");
		VBox page = new VBox(header, body);
		page.setId("border");
		page.getStyleClass().add("loginpageBox");

		StackPane pane = new StackPane(page, insertconfirm);

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
}
