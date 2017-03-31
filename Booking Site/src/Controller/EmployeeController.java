package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
   
   public AddEmployeeView getView(){
      return this.view;
   }
   
   public Boolean setView(AddEmployeeView view){
      this.view=view;
      return true;
   }
   
   
   //Check if employee already exists in database
   public Boolean checkEmployee(String email){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql;
      ResultSet res;
      int comp=-1;
      
      //Open database connection
      dbcont.createConnection();
      
      //Prepare sql statement for execution
      sql="SELECT ? FROM Employee WHERE Email=?;";
      dbcont.prepareStatement(sql);
      
      //Insert statement variable run and compare to expected
      try
      {
         dbcont.getState().setString(1, email);
         res=dbcont.runSQLRes();
         res.getString("Email").compareTo(email);
      }
      catch (SQLException e)
      {
         dbcont.closeConnection();
         return false;
      }
      
      //Close database and return true if employee exists
      dbcont.closeConnection();
      if(comp==0){
         return true;
      }else{
         return false;
      }
   }
   
   //Add employee to database
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
      
      //Prepare sql statement
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
   
   public String[] getEmployees(){
      String sql="";
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      ResultSet res;
      int count=0;
      List<String> emps = new ArrayList<String>();
      
      dbcont.createConnection();
      sql="SELECT * FROM Employee;";
      dbcont.prepareStatement(sql);
      res=dbcont.runSQLRes();
      
      try
      {
         while(res.next()){
            emps.add(res.getString("Name"));
         }
      }
      catch (SQLException e)
      {
         return null;
      }
      
      dbcont.closeConnection();
      if (emps.isEmpty()) {
    	  return null;
      }
      return emps.toArray(new String[emps.size()]);
   }
   
   public String getEmail(String name){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="",email;
      ResultSet res;
      
      dbcont.createConnection();
      sql="SELECT Email FROM Employee WHERE Name=?;";
      dbcont.prepareStatement(sql);
      try{
         dbcont.getState().setString(1, name);
         res=dbcont.runSQLRes();
         email=res.getString("Email");
      }catch(SQLException e){
         dbcont.closeConnection();
         return "";
      }
      dbcont.closeConnection();
      return email;
   }
   
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

	public void validateEntries(TextField fname, HBox fnamehbox, TextField sname, HBox snamehbox, TextField address,
			HBox addresshbox, TextField pcode, HBox pcodehbox,
			TextField contactno, HBox contactnohbox, TextField email, HBox emailhbox, TextField city, HBox cityhbox,
			Text emptyerrortxt, HBox emptyerrorbox, Text empaddedtxt, HBox empaddedhbox) {

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
			empaddedhbox.getChildren().add(empaddedtxt);
			if (emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().remove(emptyerrortxt);
			}
		}

	}
   
   public void updateView(){
      view.updateView();
   }
}
