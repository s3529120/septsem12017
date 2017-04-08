package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

	/**Constructor - currently unmodified
	 * 
	 */
	public AvailabilitiesController(){

	}

	/**Return associated view.
	 * @return Associated EditAvailabilitiesView
	 */
	public EditAvailabilitiesView getView(){
		return view;
	}

	/**Set associated view.
	 * @param view View to associate with controller.
	 * @return True indicating success.
	 */
	public Boolean setView(EditAvailabilitiesView view){
		this.view=view;
		return true;
	}

	/**Call associated view method to update window
	 * 
	 */
	public void updateView(){
		view.updateView();
	}

	/**Call EmployeeController to get email from given username
	 * @param name Username of account to get associated email of.
	 * @return Email associated with account
	 */
	public String getEmail(String name){
		EmployeeController empcont = new EmployeeController();
		return empcont.getEmail(name);
	}

	/**Returns list of times in day based on duration value
	 * @return List of all possible appointment times that could be assigned 
	 * throughout day.
	 */
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

	/**Calls EmployeeController to return list of all employees.
	 * @return Map containing names and email addresses of all employees.
	 */
	public Map<String,String> getEmployees(){
		EmployeeController empcont = new EmployeeController();
		return empcont.getEmployees();
	}


	public boolean validateEntries(
			String email,
			DayOfWeek dow,
			String startstring,
			String finishstring,
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
		//checking that the times are entered
		if (startstring.equals("") || finishstring.equals("")) {
			goodInputs = false;

		} else {
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
		}



		return goodInputs;
	}

	/**Adds availability for given employee to database
	 * @param email Employee email
	 * @param dow Day of week availability is on.
	 * @param startstring Start of availability.
	 * @param finishstring End of Availability.
	 * @return True if successful, or false if error occurs.
	 */
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
		System.out.println("Adding " + startstring + " - " + finishstring + " on + " + dow + " for " + email);

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
			System.out.println("Failed to add the availability");
			return false;
		}
		dbcont.runSQLUpdate();
		dbcont.closeConnection();

		//Adjust bookings
		BookingController bcont= new BookingController();
		bcont.removeBookings(dow, email);
		bcont.addBookings(dow, LocalTime.parse(startstring), LocalTime.parse(finishstring), email);

		return true;
	}

	/**Returns currently set availability as a string for employee on given day
	 * @param dow Day of week to get availability for
	 * @param empemail Email address of employye whose availability to get.
	 * @return Map of start and finish times, for given employee, on given day.
	 */
	public Map<String,String> getAvailabilityString(DayOfWeek dow, String empemail){
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		Map<String,String> map = new HashMap<String,String>();
		String sql;
		ResultSet res;
		map.put("StartTime", null);
		map.put("FinishTime", null);

		//Create database connection
		dbcont.createConnection();

		//Prepare and run sql
		sql="SELECT * FROM Availability;";
		dbcont.prepareStatement(sql);
		try
		{
			res=dbcont.runSQLRes();
			//Get times and add to map
			while(res.next()){
			   if(res.getString("Day").compareToIgnoreCase(dow.toString())==0 && empemail.compareToIgnoreCase(res.getString("Email"))==0){
			      map.remove("StartTime");
			      map.remove("FinishTime");
			      map.put("StartTime", res.getString("StartTime"));
			      map.put("FinishTime", res.getString("FinishTime"));
			   }
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbcont.closeConnection();
			return map;
		}
		dbcont.closeConnection();
		return map;
	}
	
	public void setSelection(DayOfWeek dow, String email, ComboBox<String> startBox, ComboBox<String> endBox) {
		Map<String,String> sundayTimes = this.getAvailabilityString(dow,email);
		if (sundayTimes.get("StartTime")!=null) {
			startBox.getSelectionModel().select(sundayTimes.get("StartTime"));
		} else {
			startBox.getSelectionModel().selectFirst();
		}
		if (sundayTimes.get("FinishTime") != null) {
			endBox.getSelectionModel().select(sundayTimes.get("FinishTime"));
		} else {
			endBox.getSelectionModel().selectFirst();
		}
	}
}
