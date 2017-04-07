package Controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Model.BookingModel;

import Model.DatabaseModel;
import View.AddEmployeeView;
import View.BookingsView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BookingController
{
   private BookingsView view;

   
   //Returns associated view
 	public BookingsView getView(){
 		return this.view;
 	}
   
 //Sets associated view
   public Boolean setView(BookingsView view) {
   	this.view=view;
   	return true;
   }

//Creates bookings on startup for a month ahead of time and remove old bookings
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
      }
      
      //Remove old bookings
      sql="SELECT * FROM Booking;";
      dbcont.prepareStatement(sql);
      res=dbcont.runSQLRes();
      try
      {
         while(res.next()){
            focus=LocalDate.parse(res.getString("Date"), dtf);
            if(focus.isBefore(LocalDate.now().minusWeeks(2))){
               sql="DELETE FROM Booking WHERE Date=? AND StartTime=? AND FinishTime=?;";
               dbcont.prepareStatement(sql);
               dbcont.getState().setString(1, res.getString("Date"));
               dbcont.getState().setString(2, res.getString("StartTime"));
               dbcont.getState().setString(3, res.getString("FinishTime"));
               dbcont.runSQLUpdate();
            }
         }
      }
      catch (SQLException e)
      {
      }
      //Close Database connection
      dbcont.closeConnection();
   }
   
   //Removes bookings for given employee on given date
   public void removeBookings(DayOfWeek dow,String empemail){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      LocalDate focus;
      ArrayList<LocalDate> days = new ArrayList<LocalDate>();
      
      //Get all days matching day of week
      focus=LocalDate.now();
      while(!focus.isAfter(LocalDate.now().plusWeeks(4))){
         if(focus.getDayOfWeek()==dow){
            days.add(focus);
         }
         focus=focus.plusDays(1);
      }
      
      //Open database connection
      dbcont.createConnection();
      
      days.forEach(x ->{
         //Prepare and run sql
         dbcont.prepareStatement("DELETE FROM Booking WHERE Date=? AND EmployeeEmail=?;");
         try
         {
            dbcont.getState().setString(1, x.toString());
            dbcont.getState().setString(2, empemail);
            dbcont.runSQLUpdate();
         }
         catch (SQLException e)
         {
         }
   });
      dbcont.closeConnection();
   }
   
   //Add bookings for employee on given day
   public void addBookings(DayOfWeek dow,LocalTime start,LocalTime finish,String empemail){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      LocalTime focus=start;
      LocalDate focusdate;
      
      //Open database connection
      dbcont.createConnection();
      
      //Loop through days
      focusdate=LocalDate.now();
      while(!focusdate.isAfter(LocalDate.now().plusWeeks(4))){
         //Check if matches day of week
         if(focusdate.getDayOfWeek()==dow){
            //Loop through available hours creating bookings
            while(focus.isBefore(finish)){
               //Prepare and run sql
               sql="INSERT INTO Booking(Date,StartTime,FinishTime,EmployeeEmail) " +
                     "Value(?,?,?,?);";
               dbcont.prepareStatement(sql);
               try
               {
                  dbcont.getState().setString(1, focusdate.toString());
                  dbcont.getState().setString(2, start.toString());
                  dbcont.getState().setString(3, start.plusMinutes(15).toString());
                  dbcont.getState().setString(4, empemail.toString());
                  dbcont.runSQLUpdate();
               }
               catch (SQLException e) {}
               //Increment appointment
               focus=focus.plusMinutes(15);
            }
         }
            focusdate=focusdate.plusDays(1);
      }
      dbcont.closeConnection();
   }
   
   public List<BookingModel> getPastBookings(){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql;
      List<BookingModel> bookings = new ArrayList<BookingModel>();
      ResultSet res;
      BookingModel mod;
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
      
      //Get all bookings
      dbcont.createConnection();
       sql="SELECT * FROM Booking;";
       dbcont.prepareStatement(sql);
       res=dbcont.runSQLRes();
       try
      {
          //Loop through bookings
         while(res.next()){
            //Create booking model
             mod=new BookingModel(LocalDate.parse(res.getString("Date"),dtf),
                                  LocalTime.parse(res.getString("StartTime")),
                                                LocalTime.parse(res.getString("FinishTime")));
             mod.setEmployee(res.getString("EmployeeEmail"));
             //Check if filled
             try{
                mod.setUser(res.getString("User"));
             }catch(SQLException e1){
                mod.setUser("Unfilled");
             }
             //If date has passed add to list to be returned
             if(mod.getDate().isBefore(LocalDate.now())){
                bookings.add(mod);
             }
          }
      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }
       dbcont.closeConnection();
       return bookings;
   }
   public List<BookingModel> getBookings(){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql;
      List<BookingModel> bookings = new ArrayList<BookingModel>();
      ResultSet res;
      BookingModel mod;
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
      
      //Get all bookings
      dbcont.createConnection();
       sql="SELECT * FROM Booking;";
       dbcont.prepareStatement(sql);
       res=dbcont.runSQLRes();
       try
      {
          //Loop through bookings
         while(res.next()){
            //Create booking model
             mod=new BookingModel(LocalDate.parse(res.getString("Date"),dtf),
                                  LocalTime.parse(res.getString("StartTime")),
                                                LocalTime.parse(res.getString("FinishTime")));
             mod.setEmployee(res.getString("EmployeeEmail"));
             //Check if filled
             try{
                mod.setUser(res.getString("User"));
             }catch(SQLException e1){
                mod.setUser("Unfilled");
             }
             //If date has not passed add to list to be returned
             if(!mod.getDate().isBefore(LocalDate.now())){
                bookings.add(mod);
             }
          }
      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }
       dbcont.closeConnection();
       return bookings;
   }
   
 //Calls associated view to update window
  	public void updateView(){
  		view.updateView();
  	}
   
}
