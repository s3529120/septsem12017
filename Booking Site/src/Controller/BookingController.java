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
   //Creates bookings on startup for a month ahead of time
   public void updateBookings(){
      DatabaseController dbcont=new DatabaseController(new DatabaseModel());
      LocalDate focus,booksCurrent,booksUntil=LocalDate.now().plusWeeks(4);
      String sql="";
      ResultSet res;
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
      LocalTime start,finish,focustime;
      String emp;
      
      //Open database connection
      dbcont.createConnection();
      
      /*Prepare and run sql to retrieve value for what bookings have 
      already been generated until*/
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
      
      //Loops through dates and availabilities to be generated and inserts into database
      focus=booksCurrent;
      //Date loop
      while(focus.isAfter(booksUntil)==false){
         sql="SELECT * FROM Availability WHERE Day=?;";
         dbcont.prepareStatement(sql);
         try
         {
            dbcont.getState().setString(1, focus.getDayOfWeek().toString());
            res=dbcont.runSQLRes();
            //Get data to booking values
            while(res.next()){
               start=LocalTime.parse(res.getString("StartTime"));
               finish=LocalTime.parse(res.getString("FinishTime"));
               emp=res.getString("EmployeeEmail");
               focustime=start;
               //Availability loop
               while(focustime.isBefore(finish)){
                  sql="INSERT INTO Booking(Date,StartTime,FinishTime,EmployeeEmail) " +
                        "Values(?,?,?,?);";
                  dbcont.prepareStatement(sql);
                  dbcont.getState().setString(1, focus.toString());
                  dbcont.getState().setString(2, focustime.toString());
                  dbcont.getState().setString(3, focustime.plusMinutes(15).toString());
                  dbcont.getState().setString(4, emp);
                  dbcont.runSQLUpdate();
                  
                  //Increment appointment time
                  focustime.plusMinutes(15);
               }
            }
         }catch(NullPointerException e){
            dbcont.closeConnection();
            return;
         }catch(SQLException e){
         }
         //Increment date
         focus=focus.plusDays(1);
      }
      //Update value of date bookings have been generated until
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
         dbcont.closeConnection();
      }
      dbcont.closeConnection();
   }
   
   //Removes bookings for given employee on given date
   public void removeBookings(LocalDate date,String empemail){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      
      //Open database connection
      dbcont.createConnection();
      //Prepare and run sql
      sql="DELETE FROM Booking WHERE Date=? AND EmployeeEmail=?;";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, date.toString());
         dbcont.getState().setString(2, empemail);
         dbcont.runSQLUpdate();
      }
      catch (SQLException e)
      {
      }
      dbcont.closeConnection();
   }
   
   //Add bookings for employee on given day
   public void addBookings(LocalDate date,LocalTime start,LocalTime finish,String empemail){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      LocalTime focus=start;
      
      //Open database connection
      dbcont.createConnection();
      
      //Loop through available hours creating bookings
      while(focus.isBefore(finish)){
         //Prepare and run sql
         sql="INSERT INTO Booking(Date,StartTime,FinishTime,EmployeeEmail) " +
               "Value(?,?,?,?);";
         dbcont.prepareStatement(sql);
         try
         {
            dbcont.getState().setString(1, date.toString());
            dbcont.getState().setString(2, start.toString());
            dbcont.getState().setString(3, start.plusMinutes(15).toString());
            dbcont.getState().setString(4, empemail.toString());
            dbcont.runSQLUpdate();
         }
         catch (SQLException e) {}
         //Increment appointment
         focus=focus.plusMinutes(15);
      }
      dbcont.closeConnection();
   }
}
