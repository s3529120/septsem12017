package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import Model.DatabaseModel;

public class BookingController
{
   public void updateBookings(){
      DatabaseController dbcont=new DatabaseController(new DatabaseModel());
      LocalDate focus,booksCurrent,booksUntil=LocalDate.now().plusWeeks(4);
      String sql="";
      ResultSet res;
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
      LocalTime start,finish,focustime;
      String emp;
      
      
      dbcont.createConnection();
      sql="SELECT BookingsUntil FROM System;";
      dbcont.prepareStatement(sql);
      res=dbcont.runSQLRes();
      try
      {
         booksCurrent=LocalDate.parse(res.getString("BookingsUntil"), dtf);
      }
      catch (SQLException e)
      {
         booksCurrent=LocalDate.now();
      }
      
      focus=booksCurrent;
      while(focus.isAfter(booksUntil)==false){
         sql="SELECT * FROM Availability WHERE Day=?;";
         dbcont.prepareStatement(sql);
         try
         {
            dbcont.getState().setString(1, focus.getDayOfWeek().toString());
            res=dbcont.runSQLRes();
            while(res.next()){
               start=LocalTime.parse(res.getString("StartTime"));
               finish=LocalTime.parse(res.getString("FinishTime"));
               emp=res.getString("EmployeeEmail");
               focustime=start;
               while(focustime.isBefore(finish)){
                  sql="INSERT INTO Booking(Date,StartTime,FinishTime,EmployeeEmail) " +
                        "Values(?,?,?,?);";
                  dbcont.prepareStatement(sql);
                  dbcont.getState().setString(1, focus.toString());
                  dbcont.getState().setString(2, focustime.toString());
                  dbcont.getState().setString(3, focustime.plusMinutes(15).toString());
                  dbcont.getState().setString(4, emp);
                  dbcont.runSQLUpdate();
                  focustime.plusMinutes(15);
               }
            }
         }catch(NullPointerException e){
            return;
         }catch(SQLException e){
            
         }
         focus=focus.plusDays(1);
      }
      sql="DELETE FROM System;";
      dbcont.prepareStatement(sql);
      dbcont.runSQLUpdate();
      sql="INSERT INTO System(BookingsUntil) Values(?);";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, booksUntil.toString());
      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }
   }
}
