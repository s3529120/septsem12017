package unitTests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

import employee.AvailabilitiesController;
import utils.DatabaseController;
import utils.DatabaseModel;

public class RosterTest {


	public void setUpBeforeClass(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql="";

		try{
			sql = "DROP TABLE IF EXISTS Availibility;";
			dbcont.prepareStatement(sql);
			dbcont.runSQLUpdate();

			sql="CREATE TABLE Availability("
					+ "Date TEXT NOT NULL, "
					+ "StartTime TEXT NOT NULL, "
					+ "FinishTime TEXT NOT NULL, "
					+ "Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Email,Date,StartTime));";
			dbcont.prepareStatement(sql);
			dbcont.runSQLUpdate();
			//Employee
			sql="CREATE TABLE Employee("
					+ "Name TEXT NOT NULL, "
					+ "ContactNo TEXT NOT NULL, "
					+ "Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Email));";
			dbcont.prepareStatement(sql);
			dbcont.runSQLUpdate();

		}catch(Exception e){
			dbcont.closeConnection();
			e.printStackTrace();
		}finally{
			dbcont.closeConnection();
		}
	}

	public void tearDownAfterClass(){
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);
		String sql;
		try{
			cont.createConnection();
			sql="DROP TABLE IF EXISTS Employee; DROP TABLE IF EXISTS Availibility;";
			cont.prepareStatement(sql);
			cont.runSQLUpdate();
			cont.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			cont.closeConnection();
		}
	}

	@Test
	public void testAddValidAvail(){
		AvailabilitiesController avcont = new AvailabilitiesController();
		LocalDate focusdate=LocalDate.now();
		DayOfWeek today = focusdate.getDayOfWeek();
		String email = "newemail@gmail.com",start="12:00",end="12:45";
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime starttime = LocalTime.parse(end, dtf);
		LocalTime finishtime = LocalTime.parse(start, dtf);

		try{
			avcont.addAvailability(email, today, start, end);

			assertNotNull(avcont.getAvailabilityString(today, email));
			assertFalse(starttime.isAfter(finishtime) || starttime.equals(finishtime));

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testaAddInvalidAvail(){
		AvailabilitiesController avcont = new AvailabilitiesController();
		LocalDate focusdate=LocalDate.now();
		DayOfWeek today = focusdate.getDayOfWeek();
		String email = "newemail@gmail.com",start="13:00",end="12:45";
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime starttime = LocalTime.parse(end, dtf);
		LocalTime finishtime = LocalTime.parse(start, dtf);

		try{
			avcont.addAvailability(email, today, start, end);

			assertNotNull(avcont.getAvailabilityString(today, email));
			assertTrue(starttime.isAfter(finishtime) || starttime.equals(finishtime));
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddEmptyFields(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql="";
		ResultSet res;

		
		AvailabilitiesController avcont = new AvailabilitiesController();
		LocalDate focusdate=LocalDate.now();
		DayOfWeek today = focusdate.getDayOfWeek();
		String email = null,startstring="00:00",finishstring="12:45";
		try{
			avcont.addAvailability(email, today, startstring, finishstring);
			
			assertNull(avcont.getAvailabilityString(today, email));
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcont.closeConnection();
		}
	}
	
	@Test
	public void testEditRoster(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql="";
		ResultSet res;

		AvailabilitiesController avcont = new AvailabilitiesController();
		LocalDate focusdate=LocalDate.now();
		DayOfWeek dow = focusdate.getDayOfWeek();
		String email = "email@email.com",startstring="00:00",finishstring="12:45";
		String newstartstring = "12:00", newfinishstring = "13:15";
		
		try{
			avcont.addAvailability(email, dow, startstring, finishstring);
			assertNotNull(avcont.getAvailabilityString(dow, email));
			
			sql = "SELECT * FROM availabilities WHERE email = 'email@email.com';";
			dbcont.prepareStatement(sql);
			res = dbcont.runSQLRes();
			
			assertThat(res.getString(2), is("00:00"));
			assertThat(res.getString(3), is("12:45"));
			
			avcont.addAvailability(email, dow, newstartstring, newfinishstring);
			
			sql = "SELECT * FROM availabilities WHERE email = 'email@email.com';";
			dbcont.prepareStatement(sql);
			res = dbcont.runSQLRes();
			
			assertNotNull(avcont.getAvailabilityString(dow, email));
			assertThat(res.getString(2), is("12:00"));
			assertThat(res.getString(3), is("13:15"));
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcont.closeConnection();
		}
	}
}


