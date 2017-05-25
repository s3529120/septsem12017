package businessHours;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import accounts.AccountController;
import employee.AvailabilitiesController;
import javafx.scene.control.ComboBox;
import utils.AppData;
import utils.DatabaseController;
import utils.DatabaseModel;

public class BusinessHoursController
{
   private BusinessHoursView view;

   public BusinessHoursController(){
      
   }

   public BusinessHoursView getView()
   {
      return view;
   }

   public void setView(BusinessHoursView view)
   {
      this.view = view;
   }
   
   public String[] getPossibleTimes(){
      AvailabilitiesController acont = new AvailabilitiesController();
      return acont.getPossibleTimes();
   }
   public Map<String,String> getTradingHours(DayOfWeek dow){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      Map<String,String> map = new HashMap<String,String>();
      String sql;
      ResultSet res;
      map.put("StartTime", null);
      map.put("FinishTime", null);

      //Create database connection
      dbcont.createConnection();

      //Prepare and run sql
      sql="SELECT * FROM Trading WHERE Username=? AND Day=?;";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, AppData.CALLER.getUsername());
         dbcont.getState().setString(2, dow.toString());
         res=dbcont.runSQLRes();
         //Get times and add to map
         while(res.next()){
         map.put("StartTime", res.getString("StartTime"));
         map.put("FinishTime", res.getString("FinishTime"));
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
   public void setSelection(DayOfWeek dow, ComboBox<String> startBox, ComboBox<String> endBox) {
      Map<String,String> sundayTimes = this.getTradingHours(dow);
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
   
   public Boolean addBusinessHours(
        DayOfWeek dow,
        String startstring,
        String finishstring) {
        DatabaseController dbcont = new DatabaseController(new DatabaseModel());
        AccountController acont = new AccountController();
        String sql="";

        //Prepare DateTime objects
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startstring, dtf);
        LocalTime finish = LocalTime.parse(finishstring, dtf);


        //Open database connection
        dbcont.createConnection();
        
      //Prepare and run sql
        sql="DELETE FROM Trading(Username,Day,StartTime) " +
            "Values(?,?,?,?);";
        dbcont.prepareStatement(sql);
        try{
            dbcont.getState().setString(1, AppData.CALLER.getUsername());
            dbcont.getState().setString(2, dow.toString());
            dbcont.getState().setString(3, start.toString());
       }catch(SQLException e){
              dbcont.closeConnection();
              return false;
       }
       dbcont.runSQLUpdate();

        //Prepare and run sql
        sql="INSERT INTO Trading(Username,Day,StartTime,FinishTime) " +
            "Values(?,?,?,?);";
        dbcont.prepareStatement(sql);
        try{
            dbcont.getState().setString(1, AppData.CALLER.getUsername());
            dbcont.getState().setString(2, dow.toString());
            dbcont.getState().setString(3, start.toString());
            dbcont.getState().setString(4, finish.toString());
       }catch(SQLException e){
              dbcont.closeConnection();
              return false;
       }
       dbcont.runSQLUpdate();
       dbcont.closeConnection();

       //Adjust bookings
       //To be implemented

       return true;
    }
}

