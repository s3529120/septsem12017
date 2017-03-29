package Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import Model.DatabaseModel;
import View.EditAvailabilitiesView;

public class AvailabilitiesController
{
   private EditAvailabilitiesView view;
   //Appointment duration subject to change
   public final int duration=15;
   
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
   
   public String getEmail(String name){
      EmployeeController empcont = new EmployeeController();
      return empcont.getEmail(name);
   }
   
   public String[] getPossibleTimes(){
      String[] times=new String[(60/duration)*24];
      
      for(int i=0;i<24;i++){
         for(int j=0;j<(60/duration);j++){
            times[i+j]=String.format("%02d:%02d",i,j*duration);
         }
      }
      return times;
   }
   
   public String[] getEmployees(){
      EmployeeController empcont = new EmployeeController();
      return empcont.getEmployees();
   }
   
   public Boolean addAvailability(String email,LocalDate date,String startstring,String finishstring){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      EmployeeController empcont = new EmployeeController();
      String sql="";
      
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
      LocalTime start = LocalTime.parse(startstring, dtf);
      LocalTime finish = LocalTime.parse(finishstring, dtf);
      
      if(empcont.checkEmployee(email)==false){
         return false;
      }
      
      dbcont.createConnection();
      sql="INSERT INTO Availability(Email,Date,StartTime,FinishTime) " +
            "Values(?,?,?,?);";
      dbcont.prepareStatement(sql);
      try{
         dbcont.getState().setString(1, email);
         dbcont.getState().setString(2, date.toString());
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
