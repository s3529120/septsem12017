package View;


import Controller.AvailabilitiesController;
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
import javafx.stage.Stage;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class EditAvailabilitiesView
{
	public Stage stage;
	private AvailabilitiesController cont;

	/** Constructor, sets stage.
	 * @param stage Window to be manipulated.
	 */
	public EditAvailabilitiesView(Stage stage){
		this.stage=stage;
	}

	public Boolean setController(AvailabilitiesController cont){
		this.cont=cont;
		return true;
	}

	/**Updates associated window.
	 */
	public void updateView(){


		//Prompt texts
		Text employeeerrortxt = new Text("Employee not selected");
		HBox employeeerrorbox = new HBox();

		Text emptyerrortxt = new Text("No employees to edit");
		HBox emptyerrorbox = new HBox();

		Text timeerrortxt = new Text("Invalid time selection");
		HBox timenerrorbox = new HBox();

		HBox donebox = new HBox();

		Text insertconfirm = new Text("");

		//Title
		Text pageTitle = new Text("Manage Availabilities");
		pageTitle.setId("heading");

		//Select Employee
		Label selectEmployeeText = new Label("Select an employ");
		//instructions
		Label instructions = new Label("To indicate no roster for a given day, set boths times to 00:00");

		Map<String,String> emps;
		ComboBox<String> employee = new ComboBox<String>();
		employee.setPromptText("Employee");
		emps=cont.getEmployees();
		if(emps.isEmpty()){
			employee.getItems().add("None Exist");
			if (!emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().add(emptyerrortxt);
			}
		}else{
			employee.getItems().addAll(emps.keySet());
			if (emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().remove(emptyerrortxt);
			}
		}
		employee.getSelectionModel().selectFirst();
		
		HBox employeebox = new HBox(selectEmployeeText,employee,employeeerrorbox);
		employeebox.setId("form");

		//Back Button
		Button backbtn = new Button("Go Back");
		backbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				stage.close();
			}
		});
		HBox backbox = new HBox(backbtn);

		//top box construction
		VBox titleAndEmployee = new VBox(pageTitle,employeebox,instructions);
		HBox topBox = new HBox(titleAndEmployee,backbox);

		//Lots of text, because apparently i cant reuse the same ones
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

		//setting up days
		DayOfWeek sunday = DayOfWeek.valueOf("SUNDAY");
		DayOfWeek monday = DayOfWeek.valueOf("MONDAY");
		DayOfWeek tuesday = DayOfWeek.valueOf("TUESDAY");
		DayOfWeek wednesday = DayOfWeek.valueOf("WEDNESDAY");
		DayOfWeek thursday = DayOfWeek.valueOf("THURSDAY");
		DayOfWeek friday = DayOfWeek.valueOf("FRIDAY");
		DayOfWeek saturday = DayOfWeek.valueOf("SATURDAY");
		DayOfWeek[] days = {sunday,monday,tuesday,wednesday,thursday,friday,saturday};

		//Setting up boxes
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

		//creating labels
		Label sundayText = new Label("Sunday");
		Label mondayText = new Label("Monday");
		Label tuesdayText = new Label("Tuesday");
		Label wednesdayText = new Label("Wednesday");
		Label thursdayText = new Label("Thursday");
		Label fridayText = new Label("Friday");
		Label saturdayText = new Label("Saturday");

		//populating lists, id like to do this in a loop, but apparently cant create an array of combo boxes
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

		//Setting default selections
		cont.setSelection(sunday, emps.get(employee.getSelectionModel().getSelectedItem()), sundayStartTime, sundayEndTime);
		cont.setSelection(monday, emps.get(employee.getSelectionModel().getSelectedItem()), mondayStartTime, mondayEndTime);
		cont.setSelection(tuesday, emps.get(employee.getSelectionModel().getSelectedItem()), tuesdayStartTime, tuesdayEndTime);
		cont.setSelection(wednesday, emps.get(employee.getSelectionModel().getSelectedItem()), wednesdayStartTime, wednesdayEndTime);
		cont.setSelection(thursday, emps.get(employee.getSelectionModel().getSelectedItem()), thursdayStartTime, thursdayEndTime);
		cont.setSelection(friday, emps.get(employee.getSelectionModel().getSelectedItem()), fridayStartTime, fridayEndTime);
		cont.setSelection(saturday, emps.get(employee.getSelectionModel().getSelectedItem()), saturdayStartTime, saturdayEndTime);

		//creating boxes
		VBox sundayBox = new VBox(sundayText,s1,sundayStartTime,e1,sundayEndTime);
		VBox mondayBox = new VBox(mondayText,s2,mondayStartTime,e2,mondayEndTime);
		VBox tuesdayBox = new VBox(tuesdayText,s3,tuesdayStartTime,e3,tuesdayEndTime);
		VBox wednesdayBox = new VBox(wednesdayText,s4,wednesdayStartTime,e4,wednesdayEndTime);
		VBox thursdayBox = new VBox(thursdayText,s5,thursdayStartTime,e5,thursdayEndTime);
		VBox fridayBox = new VBox(fridayText,s6,fridayStartTime,e6,fridayEndTime);
		VBox saturdayBox = new VBox(saturdayText,s7,saturdayStartTime,e7,saturdayEndTime);

		//setting styles
		sundayBox.getStyleClass().add("daybox");
		mondayBox.getStyleClass().add("daybox");
		tuesdayBox.getStyleClass().add("daybox");
		wednesdayBox.getStyleClass().add("daybox");
		thursdayBox.getStyleClass().add("daybox");
		fridayBox.getStyleClass().add("daybox");
		saturdayBox.getStyleClass().add("daybox");

		//putt em all together
		HBox dayBox = new HBox(sundayBox,mondayBox,tuesdayBox,wednesdayBox,thursdayBox,fridayBox,saturdayBox);
		
		//make it so that the fields update
		employee.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String t, String t1) {
				donebox.getChildren().clear();
				cont.setSelection(sunday, emps.get(employee.getSelectionModel().getSelectedItem()), sundayStartTime, sundayEndTime);
				cont.setSelection(monday, emps.get(employee.getSelectionModel().getSelectedItem()), mondayStartTime, mondayEndTime);
				cont.setSelection(tuesday, emps.get(employee.getSelectionModel().getSelectedItem()), tuesdayStartTime, tuesdayEndTime);
				cont.setSelection(wednesday, emps.get(employee.getSelectionModel().getSelectedItem()), wednesdayStartTime, wednesdayEndTime);
				cont.setSelection(thursday, emps.get(employee.getSelectionModel().getSelectedItem()), thursdayStartTime, thursdayEndTime);
				cont.setSelection(friday, emps.get(employee.getSelectionModel().getSelectedItem()), fridayStartTime, fridayEndTime);
				cont.setSelection(saturday, emps.get(employee.getSelectionModel().getSelectedItem()), saturdayStartTime, saturdayEndTime);
			}
		});

		//Save
		Button savebtn = new Button("Save");
		savebtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				//Creating arrays of the day to loop through
				//this might not be pretty but i dont know a datatype to suit
				String[] startTimes = {
						sundayStartTime.getSelectionModel().getSelectedItem().toString(),
						mondayStartTime.getSelectionModel().getSelectedItem().toString(),
						tuesdayStartTime.getSelectionModel().getSelectedItem().toString(),
						wednesdayStartTime.getSelectionModel().getSelectedItem().toString(),
						thursdayStartTime.getSelectionModel().getSelectedItem().toString(),
						fridayStartTime.getSelectionModel().getSelectedItem().toString(),
						saturdayStartTime.getSelectionModel().getSelectedItem().toString(),};
				String[] endTimes = {
						sundayEndTime.getSelectionModel().getSelectedItem().toString(),
						mondayEndTime.getSelectionModel().getSelectedItem().toString(),
						tuesdayEndTime.getSelectionModel().getSelectedItem().toString(),
						wednesdayEndTime.getSelectionModel().getSelectedItem().toString(),
						thursdayEndTime.getSelectionModel().getSelectedItem().toString(),
						fridayEndTime.getSelectionModel().getSelectedItem().toString(),
						saturdayEndTime.getSelectionModel().getSelectedItem().toString(),};
				//Preparing string for prompts
				String prompt = "Time saved for ";
				int daysSaved = 0;
				for (int i = 0;i < 7; i++) {
					if (cont.validateEntries( 
							cont.getEmail(employee.getSelectionModel().getSelectedItem()),
							days[i],
							startTimes[i],
							endTimes[i],
							employeeerrortxt,employeeerrorbox,
							timeerrortxt,timenerrorbox)) {
						cont.addAvailability( 
								emps.get(employee.getSelectionModel().getSelectedItem()), 
								days[i], 
								startTimes[i],
								endTimes[i]);
						insertconfirm.getText().replaceAll(".*?",employee.getSelectionModel().getSelectedItem().toString()
								.concat("'s available time added"));
						if (daysSaved == 0) {
							prompt = prompt + days[i];						
						} else {
							prompt = prompt + ", " + days[i];
							prompt.toLowerCase();
						}
						daysSaved++;
					}
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



		//box Construction
		HBox bottom = new HBox(savebtn,donebox);
		VBox page = new VBox(topBox,dayBox,bottom);

		page.getStyleClass().add("loginpageBox");
		backbtn.setId("largebtn");
		savebtn.setId("largebtn");
		backbtn.setId("logoutbtn");
		backbox.setId("logoutbox");



		StackPane pane = new StackPane(page,insertconfirm);

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}

