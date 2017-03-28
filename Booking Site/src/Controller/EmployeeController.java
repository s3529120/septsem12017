package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.sqlite.core.DB;

import Model.DatabaseModel;
import View.AddEmployeeView;

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
      sql="SELECT ? FROM Employees WHERE Email=?;";
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
            "Values(?,?,?,?); " 
            +"INSERT INTO Address(EmployeeEmail,StreetAddress,City,State,PostCode) " 
            +"Values(?,?,?,?,?) ;";
      try
      {
         dbcont.getState().setString(1, name);
         dbcont.getState().setString(2, contactno);
         dbcont.getState().setString(3, email);
         dbcont.getState().setString(4, email);
         dbcont.getState().setString(5, streetadd);
         dbcont.getState().setString(6, city);
         dbcont.getState().setString(7, state);
         dbcont.getState().setString(8, postcode);
         
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
   
   public void updateView(){
      view.updateView();
   }
}
