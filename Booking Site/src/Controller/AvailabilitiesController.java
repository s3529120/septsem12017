package Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import Model.DatabaseModel;
import View.EditAvailabilitiesView;

public class AvailabilitiesController
{
   private EditAvailabilitiesView view;
   
   public AvailabilitiesController(){
      
   }
   
   public EditAvailabilitiesView getView(){
      return view;
   }
   
   public Boolean setView(EditAvailabilitiesView view){
      this.view=view;
      return true;
   }
   
   public void updateView(){
      view.updateView();
   }
   
   public Boolean addAvailability(String email,LocalDate date,LocalTime start,LocalTime finish){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      EmployeeController empcont = new EmployeeController();
      String sql="";
      
      if(empcont.checkEmployee(email)==false){
         return false;
      }
      
      dbcont.createConnection();
      sql="INSERT INTO Availabilities(Email,Date,StartTime,FinishTime) " +
            "Values(?,?,?,?);";
      dbcont.prepareStatement(sql);
      try{
         dbcont.getState().setString(1, email);
         dbcont.getState().setString(2, date.toString());
         dbcont.getState().setString(3, start.toString());
         dbcont.getState().setString(4, finish.toString());
      }catch(SQLException e){
         return false;
      }
      
      return true;
   }
}
