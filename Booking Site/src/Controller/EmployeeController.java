package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sqlite.core.DB;

import Model.DatabaseModel;
import View.AddEmployeeView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class EmployeeController
{
	private AddEmployeeView view;

	//Returns associated view
	public AddEmployeeView getView(){
		return this.view;
	}

	/**Sets associated view.
	 * @param view View to associate with.
	 * @return True to indicate success.
	 */
	public Boolean setView(AddEmployeeView view){
		this.view=view;
		return true;
	}


	/**Check if employee already exists in database, 
	 * returns TRUE if its already exists.
	 * @param email Email address of employee to check.
	 * @return True if employee exists, false if not.
	 */
	public Boolean checkEmployee(String email){
		String[] emps = this.getEmployeeEmails();
		
		if(emps==null){
		   return false;
		}
		
		for (int i=0;i<emps.length;i++){
		   if(emps[i].compareToIgnoreCase(email)==0){
		      return true;
		   }
		}
		return false;
	}

	/**Add employee to database.
	 * @param name Name of employee.
	 * @param contactno Contact number of employee.
	 * @param email Email address of employee.
	 * @param streetadd Employee's street address.
	 * @param city Employee's city of residence.
	 * @param state Employee's state of residence.
	 * @param postcode Employee's postcode.
	 * @return True if successfully added, false if and error occurs.
	 */
	public Boolean addEmployee(String name,String contactno,String email,
			String streetadd,String city,String state,String postcode){
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		String sql;

		//Check if employee already exists
		if(checkEmployee(email)){
			return false;
		}


		//Open database connection
		dbcont.createConnection();

		//Prepare and run employee sql statement
		sql="INSERT INTO Employee(Name,ContactNo,Email) " +
				"Values(?,?,?);";
		dbcont.prepareStatement(sql);
		try
		{
			dbcont.getState().setString(1, name);
			dbcont.getState().setString(2, contactno);
			dbcont.getState().setString(3, email);

		}
		catch (SQLException e)
		{
			dbcont.closeConnection();
			return false;
		}
		if(!dbcont.runSQLUpdate()){
			dbcont.closeConnection();
			return false;
		}

		//Prepare and run address sql statement
		sql="INSERT INTO Address(EmployeeEmail,StreetAddress,City,State,PostCode) " 
				+"Values(?,?,?,?,?) ;";
		dbcont.prepareStatement(sql);
		try
		{
			dbcont.getState().setString(1, email);
			dbcont.getState().setString(2, streetadd);
			dbcont.getState().setString(3, city);
			dbcont.getState().setString(4, state);
			dbcont.getState().setString(5, postcode);

		}
		catch (SQLException e)
		{
			dbcont.closeConnection();
			return false;
		}

		//Close database and return true if successful
		if(dbcont.runSQLUpdate()){
			dbcont.closeConnection();
			return true;
		}else{
			dbcont.closeConnection();
			return false;
		}
	}

	//Returns array of employees
		public Map<String,String> getEmployees(){
			String sql="";
			DatabaseController dbcont = new DatabaseController(new DatabaseModel());
			ResultSet res;
			Map<String,String> emps = new HashMap<String,String>();

			//Create database connection
			dbcont.createConnection();
			//Prepare and run sql
			sql="SELECT * FROM Employee;";
			dbcont.prepareStatement(sql);
			res=dbcont.runSQLRes();

			try
			{
				while(res.next()){
					//Add returned employees to list
					emps.put(res.getString("Name"),res.getString("Email"));
				}
			}
			catch (SQLException e)
			{
				return null;
			}

			//Close database connection
			dbcont.closeConnection();
			if (emps.isEmpty()) {
				return null;
			}
			//Convert list of employees to array and return
			return emps;
		}
	
	/**Returns array of employee emails.
	 * @return List of all employees email address'.
	 */
	public String[] getEmployeeEmails(){
		String sql="";
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		ResultSet res;
		List<String> emps = new ArrayList<String>();

		//Create database connection
		dbcont.createConnection();
		//Prepare and run sql
		sql="SELECT * FROM Employee;";
		dbcont.prepareStatement(sql);
		res=dbcont.runSQLRes();

		try
		{
			while(res.next()){
				//Add returned employyes to list
				emps.add(res.getString("Email"));
			}
		}
		catch (SQLException e)
		{
		   e.printStackTrace();
			return null;
		}

		//Close database connection
		dbcont.closeConnection();
		if (emps.isEmpty()) {
			return null;
		}
		//Convert list of employees to array and return
		return emps.toArray(new String[emps.size()]);
	}

	//Returns employee email given their name
	public String getEmail(String name){
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		String sql="",email;
		ResultSet res;

		//Create database connection
		dbcont.createConnection();

		//Prepare and run sql
		sql="SELECT Email FROM Employee WHERE Name=?;";
		dbcont.prepareStatement(sql);
		try{
			dbcont.getState().setString(1, name);
			res=dbcont.runSQLRes();
			//Retrieve email from results
			email=res.getString("Email");
		}catch(SQLException e){
			//Given no results return ""
			dbcont.closeConnection();
			return "";
		}

		//Close connection and return value
		dbcont.closeConnection();
		return email;
	}

	//Determines whether given fields are empty
	public Boolean checkValues(TextField fname, TextField sname, TextField address, TextField pcode,
			TextField contactNo, TextField email, TextField city) {

		if (fname.getText().isEmpty() || sname.getText().isEmpty() || address.getText().isEmpty()
				|| pcode.getText().isEmpty() || contactNo.getText().isEmpty()
				|| email.getText().isEmpty()) {
			return false;
		} else {
			return true;
		}

	}
	
	public void validateEntries(
			TextField fname, HBox fnamehbox, 
			TextField sname, HBox snamehbox, 
			TextField address, HBox addresshbox, 
			TextField pcode, HBox pcodehbox,
			TextField contactno, HBox contactnohbox, 
			TextField email, HBox emailhbox,
			TextField city, HBox cityhbox,
			Text emptyerrortxt, HBox emptyerrorbox, 
			Text empaddedtxt, HBox empaddedhbox,
			Text takenerrortxt, HBox takenerrorbox) {

		// checking for empty
		boolean hasEmpty = false;
		if (fname.getText().trim().equals("")) {
			fnamehbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			fnamehbox.setId("form");
		}
		if (sname.getText().trim().equals("")) {
			snamehbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			snamehbox.setId("form");
		}
		if (address.getText().trim().equals("")) {
			addresshbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			address.setId("form");
		}
		if (pcode.getText().trim().equals("")) {
			pcodehbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			pcode.setId("form");
		}
		if (contactno.getText().trim().equals("")) {
			contactnohbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			contactno.setId("form");
		}
		if (email.getText().trim().equals("")) {
			emailhbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			emailhbox.setId("form");
		}
		if (city.getText().trim().equals("")) {
			cityhbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			cityhbox.setId("form");
		}

		// checking if any of the fields were empty and if they were add the
		// "empty error text", else "employee added" text
		if (hasEmpty) {
			if (!emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().add(emptyerrortxt);
			}
		} else {
			//empaddedhbox.getChildren().add(empaddedtxt);
			if (emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().remove(emptyerrortxt);
			}
		}

		// checking if the employee email has been taken
		if (this.checkEmployee(email.getText().trim())) {
			if (!takenerrorbox.getChildren().contains(takenerrortxt)) {
				takenerrorbox.getChildren().add(takenerrortxt);
			}
		} else {
			if (takenerrorbox.getChildren().contains(takenerrortxt)) {
				takenerrorbox.getChildren().remove(takenerrortxt);
			}
		}
	}

	/**Retrieves name from their email
	 * @param email Employee email
	 * @return Name of employee with given email
	 */
	public String getEmployeeName(String email){
	   DatabaseController dbcont = new DatabaseController(new DatabaseModel());
	   String sql="",name;
	   ResultSet res;
	   
	   //Create connection
	   dbcont.createConnection();
	   
	   //Prepare statement
	   sql="SELECT Email FROM Employee WHERE Name=?;";
	   dbcont.prepareStatement(sql);
	   try{
	      dbcont.getState().setString(1, email);
	      res=dbcont.runSQLRes();
	      name=res.getString("Email");
	   }catch(SQLException e){
	      dbcont.closeConnection();
	      return "Employee name not found";
	   }
	   dbcont.closeConnection();
	   return name;
	}
	
	//Adds error message to error box
	public void empAddedMessage(HBox empaddedhbox, Text empaddedtxt) {
		if (!empaddedhbox.getChildren().contains(empaddedtxt)) {
			empaddedhbox.getChildren().add(empaddedtxt);
		}
	}

	/**Calls associated view to update window.
	 */
	public void updateView(){
		view.updateView();
	}
}
