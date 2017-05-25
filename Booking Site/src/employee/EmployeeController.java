package employee;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import utils.DataMatcher;
import utils.DatabaseController;
import utils.DatabaseModel;


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
			String streetadd,String city,String state,String postcode, String business){
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		String sql;

		//Check if employee already exists
		if(checkEmployee(email)){
			return false;
		}


		//Open database connection
		dbcont.createConnection();

		//Prepare and run employee sql statement
		sql="INSERT INTO Employee(Name,ContactNo,Email,Business) " +
				"Values(?,?,?,?);";
		dbcont.prepareStatement(sql);
		try
		{
			dbcont.getState().setString(1, name);
			dbcont.getState().setString(2, contactno);
			dbcont.getState().setString(3, email);
			dbcont.getState().setString(4, business);

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
		}
		return false;
	}
	

	//Returns array of employees
	public Map<String,String> getAllEmployees(){
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
	
	  //Returns array of employees
   public Map<String,String> getEmployees(String bus){
      String sql="";
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      ResultSet res;
      Map<String,String> emps = new HashMap<String,String>();

      //Create database connection
      dbcont.createConnection();
      //Prepare and run sql
      sql="SELECT * FROM Employee WHERE Business=?;";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, bus);
      }
      catch (SQLException e1)
      {
         e1.printStackTrace();
      }
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
	 * @return List of all employees email addresses.
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
	/**
	 * Get the employee's email address
	 * @param name
	 * @return the email address of an employee with name
	 */
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
	
	/**
	 * Set error text's on appropriate hboxes if there are any errors (This desperately needs abstraction, WIP)
	 * @param fname Firstname
	 * @param fnamehbox hbox for firstname
	 * @param sname
	 * @param snamehbox
	 * @param address
	 * @param addresshbox
	 * @param pcode
	 * @param pcodehbox
	 * @param contactno
	 * @param contactnohbox
	 * @param email
	 * @param emailhbox
	 * @param city
	 * @param cityhbox
	 * @param emptyerrortxt
	 * @param emptyerrorbox
	 * @param empaddedtxt
	 * @param empaddedhbox
	 * @param takenerrortxt
	 * @param takenerrorbox
	 * @param fnameerrortxt
	 * @param fnameerrorbox
	 * @param snameerrortxt
	 * @param snameerrorbox
	 * @param emailerrortxt
	 * @param emailerrorbox
	 * @param phoneerrortxt
	 * @param phoneerrorbox
	 * @param streeterrortxt
	 * @param streeterrorbox
	 * @param cityerrortxt
	 * @param cityerrorbox
	 * @param postcerrortxt
	 * @param postcerrorbox
	 * @param state
	 * @return true if no errors
	 */
	public boolean validateEntries(
			TextField fname, HBox fnamehbox, 
			TextField sname, HBox snamehbox, 
			TextField address, HBox addresshbox, 
			TextField pcode, HBox pcodehbox,
			TextField contactno, HBox contactnohbox, 
			TextField email, HBox emailhbox,
			TextField city, HBox cityhbox,
			Text emptyerrortxt, HBox emptyerrorbox, 
			Text empaddedtxt, HBox empaddedhbox,
			Text takenerrortxt, HBox takenerrorbox,
			Text fnameerrortxt, HBox fnameerrorbox,
			Text snameerrortxt, HBox snameerrorbox,
			Text emailerrortxt, HBox emailerrorbox,
			Text phoneerrortxt, HBox phoneerrorbox,
			Text streeterrortxt, HBox streeterrorbox,
			Text cityerrortxt, HBox cityerrorbox,
			Text postcerrortxt, HBox postcerrorbox,
			String state) {

		// checking for empty
		boolean hasEmpty = false, numerror = false, fnameerror = false, 
				snameerror = false, adderror = false, pcodeerror = false,
				emailerror = false, cityerror = false;

		//Stringify all inputs
		String fnameTrim = fname.getText().trim(), snameTrim = sname.getText().trim(),
				addTrim = address.getText().trim(), pcodeTrim = pcode.getText().trim(),
				contactnoTrim = contactno.getText().trim(), emailTrim = email.getText().trim(),
				cityTrim = city.getText().trim();

		if (fnameTrim.equals("")) {
			fname.setId("incorrectForm");
			hasEmpty = true;
			fnameerror = true;
		} else if(!DataMatcher.fnameMatcher(fnameTrim)){
			fname.setId("incorrectForm");
			fnameerror = true;
		} else {
			fnameerror = false;
			fname.setId("form");
		}

		if (snameTrim.equals("")) {
			sname.setId("incorrectForm");
			hasEmpty = true;
			snameerror = true;
		} else if(!DataMatcher.snameMatcher(snameTrim)){
			sname.setId("incorrectForm");
			snameerror = true;
		} else {
			snameerror = false;
			sname.setId("form");
		}

		if (addTrim.equals("")) {
			address.setId("incorrectForm");
			hasEmpty = true;
			adderror = true;
		} else if(!DataMatcher.addMatcher(addTrim)) {
			address.setId("incorrectForm");
			adderror = true;
		} else {
			adderror = false;
			address.setId("form");
		}

		if (pcodeTrim.equals("")) {
			pcode.setId("incorrectForm");
			hasEmpty = true;
			pcodeerror = true;
		} else if(!DataMatcher.postcMatcher(pcodeTrim, state)){
			pcode.setId("incorrectForm");
			pcodeerror = true;
		} else {
			pcodeerror = false;
			pcode.setId("form");
		}

		if (contactnoTrim.equals("")) {
			contactno.setId("incorrectForm");
			hasEmpty = true;
			numerror = true;
		} else if(!DataMatcher.phoneMatcher(contactnoTrim)){
			contactno.setId("incorrectForm");
			numerror = true;
		}else{
			numerror = false;
			contactno.setId("form");
		}

		if (emailTrim.equals("")) {
			email.setId("incorrectForm");
			hasEmpty = true;
			emailerror = true;
		} else if(!DataMatcher.emailMatcher(emailTrim)){
			email.setId("incorrectForm");
			emailerror = true;
		}else{
			emailerror = false;
			email.setId("form");
		}

		if (cityTrim.equals("")) {
			city.setId("incorrectForm");
			hasEmpty = true;
			cityerror = true;
		} else if(!DataMatcher.cityMatcher(cityTrim)){
			city.setId("incorrectForm");
			cityerror = true;
		} else {
			cityerror = false;
			city.setId("form");
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
		if (this.checkEmployee(emailTrim)) {
			if (!takenerrorbox.getChildren().contains(takenerrortxt)) {
				takenerrorbox.getChildren().add(takenerrortxt);
			}
		} else {
			if (takenerrorbox.getChildren().contains(takenerrortxt)) {
				takenerrorbox.getChildren().remove(takenerrortxt);
			}
		}

		//Set error text for incorrect first name
		if(fnameerror){
			if(!fnameerrorbox.getChildren().contains(fnameerrortxt)){
				fnameerrorbox.getChildren().add(fnameerrortxt);
			}
		}else{
			if (fnameerrorbox.getChildren().contains(fnameerrortxt)) {
				fnameerrorbox.getChildren().remove(fnameerrortxt);
			}
		}

		//Set error text for incorrect last name
		if(snameerror){
			if(!snameerrorbox.getChildren().contains(snameerrortxt)){
				snameerrorbox.getChildren().add(snameerrortxt);
			}
		}else{
			if (snameerrorbox.getChildren().contains(snameerrortxt)) {
				snameerrorbox.getChildren().remove(snameerrortxt);
			}
		}

		//check if number is wrong
		if(numerror){
			if(!phoneerrorbox.getChildren().contains(phoneerrortxt)){
				phoneerrorbox.getChildren().add(phoneerrortxt);
			}
		}else{
			if(phoneerrorbox.getChildren().contains(phoneerrortxt)){
				phoneerrorbox.getChildren().remove(phoneerrortxt);
			}
		}

		//check if street address is wrong
		if(adderror){
			if(!streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().add(streeterrortxt);
			}
		}else{
			if(streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().remove(streeterrortxt);
			}
		}
		
		// Check city error
		if(cityerror){
			if(!cityerrorbox.getChildren().contains(cityerrortxt)){
				cityerrorbox.getChildren().add(cityerrortxt);
			}
		} else {
			if(cityerrorbox.getChildren().contains(cityerrortxt)){
				cityerrorbox.getChildren().remove(cityerrortxt);
			}
		}

		//check if mail is wrong
		if(emailerror){
			if(!emailerrorbox.getChildren().contains(emailerrortxt)){
				emailerrorbox.getChildren().add(emailerrortxt);
			}
		}else{
			if(emailerrorbox.getChildren().contains(emailerrortxt)){
				emailerrorbox.getChildren().remove(emailerrortxt);
			}
		}
		
		if(pcodeerror){
			if(!postcerrorbox.getChildren().contains(postcerrortxt)){
				postcerrorbox.getChildren().add(postcerrortxt);
			}
		} else {
			if(postcerrorbox.getChildren().contains(postcerrortxt)){
				postcerrorbox.getChildren().remove(postcerrortxt);
			}
		}
		
		if(!(numerror || fnameerror || 
				snameerror || adderror || pcodeerror ||
				emailerror || cityerror)){
			return true;
		}
		
		return false;


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
		sql="SELECT Name FROM Employee WHERE Email=?;";
		dbcont.prepareStatement(sql);
		try{
			dbcont.getState().setString(1, email);
			res=dbcont.runSQLRes();
			name=res.getString("Name");
		}catch(SQLException e){
			dbcont.closeConnection();
			return "Employee name not found";
		}
		dbcont.closeConnection();
		return name;
	}
	
	/**
	 * Get email from name
	 * @param name
	 * @return
	 */
	public String getEmployeeMail(String name){
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		String sql="", email;
		ResultSet res;

		//Create connection
		dbcont.createConnection();

		//Prepare statement
		sql="SELECT Email FROM Employee WHERE Name=?;";
		dbcont.prepareStatement(sql);
		try{
			dbcont.getState().setString(1, name);
			res=dbcont.runSQLRes();
			email=res.getString("Email");
		}catch(SQLException e){
			dbcont.closeConnection();
			return "Employee email not found";
		}
		dbcont.closeConnection();
		return email;
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
