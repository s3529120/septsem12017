package Controller;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
import Model.DatabaseModel;
import View.EditAvailabilitiesView;

public class AvailabilitiesController
{
	private EditAvailabilitiesView view;
	//Appointment duration subject to change
	public final int duration=15;

	//Constructor - currently unmodified
	public AvailabilitiesController(){

	}

	//Return associated view
	public EditAvailabilitiesView getView(){
		return view;
	}

	//Set associated view
	public Boolean setView(EditAvailabilitiesView view){
		this.view=view;
		return true;
	}

	//Call associated view method to update window
	public void updateView(){
		view.updateView();
	}

	//Call EmployeeController to get email from given username
	public String getEmail(String name){
		EmployeeController empcont = new EmployeeController();
		return empcont.getEmail(name);
	}

	//Returns list of times in day based on duration value
	public String[] getPossibleTimes(){
		String[] times=new String[(60/duration)*24];

		//Hours loop
		for(int i=0;i<24;i++){
		   //Minutes loop
			for(int j=0;j<(60/duration);j++){
				times[i+j]=String.format("%02d:%02d",i,j*duration);
			}
		}
		return times;
	}

	//Calls EmployeeController to return list of all employees
	public String[] getEmployees(){
		EmployeeController empcont = new EmployeeController();
		return empcont.getEmployees();
	}

	////Checks that values of given boxes are valid
	public boolean validateEntries(
			String email, HBox emailbox,
			DayOfWeek dow, VBox datebox,
			String startstring, HBox startbox,
			String finishstring, HBox finbox,
			Text dateerrortxt, HBox dateerrorbox,
			Text employeeerrortxt, HBox employeeerrorbox,
			Text timeerrortxt, HBox timenerrorbox) {
		boolean goodInputs = true;

		//Checking that an employ is selected
		if (email == null || email.isEmpty()) {
			goodInputs = false;
			if (!employeeerrorbox.getChildren().contains(employeeerrortxt)) {
				employeeerrorbox.getChildren().add(employeeerrortxt);
			}
		} else {
			if (employeeerrorbox.getChildren().contains(employeeerrortxt)) {
				employeeerrorbox.getChildren().remove(employeeerrortxt);
			}
		}
		
		//Checking that times are ok
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime start = LocalTime.parse(startstring, dtf);
		LocalTime finish = LocalTime.parse(finishstring, dtf);
		
		if ((startstring.equals(finishstring)) || start.isAfter(finish)) {
			goodInputs = false;
			if (!timenerrorbox.getChildren().contains(timeerrortxt)) {
				timenerrorbox.getChildren().add(timeerrortxt);
			}
		} else {
			if (timenerrorbox.getChildren().contains(timeerrortxt)) {
				timenerrorbox.getChildren().remove(timeerrortxt);
			}
		}
		
		//checking if date is selected
		if (dow == null) {
			goodInputs = false;
			if (!dateerrorbox.getChildren().contains(dateerrortxt)) {
				dateerrorbox.getChildren().add(dateerrortxt);
			}
		} else {
			if (dateerrorbox.getChildren().contains(dateerrortxt)) {
				dateerrorbox.getChildren().remove(dateerrortxt);
			}
		}

		return goodInputs;
	}

	//Adds availability for given employee to database
	public Boolean addAvailability(
			String email,
			DayOfWeek dow,
			String startstring,
			String finishstring) {
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		EmployeeController empcont = new EmployeeController();
		String sql="";

		//Prepare DateTime objects
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime start = LocalTime.parse(startstring, dtf);
		LocalTime finish = LocalTime.parse(finishstring, dtf);

		//Verify employee exists
		if(empcont.checkEmployee(email)==false){
			return false;
		}

		//Open database connection
		dbcont.createConnection();
		
		//Prepare and run sql
		sql="INSERT INTO Availability(Email,Day,StartTime,FinishTime) " +
				"Values(?,?,?,?);";
		dbcont.prepareStatement(sql);
		try{
			dbcont.getState().setString(1, email);
			dbcont.getState().setString(2, dow.toString());
			dbcont.getState().setString(3, start.toString());
			dbcont.getState().setString(4, finish.toString());
		}catch(SQLException e){
			dbcont.closeConnection();
			return false;
		}
		dbcont.runSQLUpdate();
		dbcont.closeConnection();
		return true;
	}
}
