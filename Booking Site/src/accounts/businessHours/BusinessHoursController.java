package accounts.businessHours;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import accounts.AccountController;
import booking.BookingController;
import employee.EmployeeController;
import utils.database.DatabaseController;
import utils.database.DatabaseModel;

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
   
   public Boolean addAvailability(
        String Username,
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

        //Verify employee exists
        if(!acont.checkUsername(Username) || acont.checkAccountType(Username).compareToIgnoreCase("Business")!=0){
              return false;
        }

        //Open database connection
        dbcont.createConnection();

        //Prepare and run sql
        sql="INSERT INTO Trading(Username,Day,StartTime,FinishTime) " +
            "Values(?,?,?,?);";
        dbcont.prepareStatement(sql);
        try{
            dbcont.getState().setString(1, Username);
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
