package booking;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import employee.EmployeeController;
import service.TypeModel;
import utils.database.DatabaseController;
import utils.database.DatabaseModel;

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

	//Calls associated view to update window
	public void updateView(){
		view.updateView();
	}

	/**Filter bookings based on attributes
	 * 
	 */
	public List<BookingModel> filterBookings(LocalDate date,LocalTime startTime,LocalTime finishTime,String employee,String user,String type){
		List<BookingModel> books=new ArrayList<BookingModel>(getBookings());
		//Date
		if(date!=null){
			books.forEach(x->{
				if(!x.getDate().equals(date)){
					books.remove(x);
				}
			});
			//StartTime
		}if(startTime!=null){
			books.forEach(x->{
				if(!x.getStartTime().equals(startTime)){
					books.remove(x);
				}
			});
			//Finish Time
		}if(finishTime!=null){
			books.forEach(x->{
				if(!x.getFinishTime().equals(finishTime)){
					books.remove(x);
				}
			});
			//Employee
		}if(employee!=null){
			books.forEach(x->{
				if(!(x.getEmployee().compareToIgnoreCase(employee)==0)){
					books.remove(x);
				}
			});
			//User
		}if(user!=null){
			books.forEach(x->{
				if(!(x.getUser().compareToIgnoreCase(user)==0)){
					books.remove(x);
				}
			});
			//Type
		}if(type!=null){
			books.forEach(x->{
				if(!(x.getType().compareToIgnoreCase(type)==0)){
					books.remove(x);
				}
			});
		}
		return books;
	}


	/**Assign user to booking slot
	 * @param booking Booking slot
	 * @param username Username of person to set booking to.
	 * @return True if successful, false if failed/
	 */
	public Boolean setUser(BookingModel booking,String uname,TypeModel type){
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		String sql="";
		int numBooks;

		//Assign to model
<<<<<<< HEAD:Booking Site/src/booking/BookingController.java
=======
		//booking.setUser(uname);
		//booking.setType(type.getName());
>>>>>>> master:Booking Site/src/Controller/BookingController.java

		//Connect to database
		dbcont.createConnection();

		//Determine number of booking slots required
		numBooks=type.getDuration()/15;
		//Set values
		try
		{
		   LocalTime startTime=booking.getStartTime();
		   //Loop through bookings assigning user and type
	      for(int i=0;i<numBooks;i++){
	         sql="UPDATE Booking SET Username=?, Type=? WHERE Date=? AND StartTime=? AND EmployeeEmail=?;";
	         dbcont.prepareStatement(sql);
	         dbcont.getState().setString(1, uname);
	         dbcont.getState().setString(2, type.getName());
	         dbcont.getState().setString(3, booking.getDate().toString());
	         dbcont.getState().setString(4, startTime.toString());
	         dbcont.getState().setString(5, booking.getEmployee());
	         dbcont.runSQLUpdate();
	         startTime=startTime.plusMinutes(15);
	      }
		}
		//Catch error return false
		catch (SQLException e)
		{
		   e.printStackTrace();
			dbcont.closeConnection();
			return false;
		}
		//Return true to indicate success
		return true;

	}

	/**Creates bookings on startup for a month ahead of time and remove old bookings.
	 */
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
					emp=res.getString("Email");
					focustime=start;

					//Availability loop
					while(focustime.isBefore(finish)){
						sql="INSERT INTO Booking(Date,StartTime,FinishTime,EmployeeEmail,Type,Username) " +
								"Values(?,?,?,?,?,?);";
						dbcont.prepareStatement(sql);
						dbcont.getState().setString(1, focus.toString());
						dbcont.getState().setString(2, focustime.toString());
						dbcont.getState().setString(3, focustime.plusMinutes(15).toString());
						dbcont.getState().setString(4, emp);
						dbcont.getState().setString(5, "None");
						dbcont.getState().setString(6, "Unfilled");
						dbcont.runSQLUpdate();

						//Increment appointment time
						focustime=focustime.plusMinutes(15);
					}
				}
			}catch(NullPointerException e){
				dbcont.closeConnection();
				return;
			}catch(SQLException e){
				e.printStackTrace();
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

	/**Removes bookings for given employee on given date
	 * @param dow Day of week to remove bookings for.
	 * @param empemail Email address of employee whose bookings to remove.
	 */
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

	/**Add bookings for employee on given day
	 * @param dow Day of week to create bookings for.
	 * @param start Time to generate bookings starting from.
	 * @param finish Time to finish generating bookings from.
	 * @param empemail Email address of employee for whom to generate bookings.
	 */
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
					sql="INSERT INTO Booking(Date, StartTime, FinishTime, EmployeeEmail) " +
							"Values(?,?,?,?);";
					dbcont.prepareStatement(sql);
					try
					{
						dbcont.getState().setString(1, focusdate.toString());
						dbcont.getState().setString(2, focus.toString());
						dbcont.getState().setString(3, focus.plusMinutes(15).toString());
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
	/**Get list of bookings whose timeslot has passed.
	 * @return List of bookings that have passed.
	 */
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

	/**Retrieves employee name from their email
	 * @param email Employee email
	 * @return Employee name
	 */
	public String getNameFromEmail(String email){
		EmployeeController cont = new EmployeeController();

		return cont.getEmployeeName(email);
	}

	/**Get list off bookings that have not yet passed
	 * @return List of bookings that have not passed.
	 */
	public List<BookingModel> getBookings(){
		/*if(allBooks!=null){
			return allBooks;
		}*/
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
				mod.setType(res.getString("Type"));
				//Check if filled
				try{
					mod.setUser(res.getString("Username"));
				}catch(SQLException e1){
<<<<<<< HEAD:Booking Site/src/booking/BookingController.java
					e1.printStackTrace();
=======
					e1.printStackTrace();;
>>>>>>> master:Booking Site/src/Controller/BookingController.java
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
<<<<<<< HEAD:Booking Site/src/booking/BookingController.java
=======
		//allBooks=bookings;
>>>>>>> master:Booking Site/src/Controller/BookingController.java
		return bookings;
	}
	

}