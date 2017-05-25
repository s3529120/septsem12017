package unitTests;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import booking.BookingController;
import employee.AvailabilitiesController;
import employee.EmployeeController;
import utils.DatabaseController;
import utils.DatabaseModel;

/**
 * Contains Tests related to making and checking for bookings
 */

public class BookingsTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		String sql="";
		DatabaseModel dataMod = new DatabaseModel();
		DatabaseController dataCont = new DatabaseController(dataMod);
		
		EmployeeController econt = new EmployeeController();
		econt.addEmployee("John Smith", "0123456789", 
				"empemail@email.com", "77 Fake st", "Melbourne", "Victoria", "3000", "bus001");

		//Drop tables
		sql="DROP TABLE IF EXISTS Accounts; ";
		try {
			dataCont.createConnection();
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS Employee; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS Address; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS Availability; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS Booking; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS System; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();

			//Create tables
			//System
			sql="CREATE TABLE System("
					+ "BookingsUntil TEXT NOT NULL,PRIMARY KEY(BookingsUntil));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			//Accounts
			sql="CREATE TABLE Accounts("
					+ "Username TEXT NOT NULL, "
					+ "Password TEXT NOT NULL, "
					+ "Name TEXT NOT NULL, "
					+ "ContactNo TEXT NOT NULL, "
					+ "Type TEXT NOT NULL, "
					+ "Address TEXT NOT NULL, " 
					+"Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Username));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			//Employee
			sql="CREATE TABLE Employee("
					+ "Name TEXT NOT NULL, "
					+ "ContactNo TEXT NOT NULL, "
					+ "Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Email));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			//Address
			sql="CREATE TABLE Address("
					+ "EmployeeEmail TEXT NOT NULL, "
					+ "StreetAddress TEXT NOT NULL, "
					+ "City TEXT NOT NULL, " 
					+ "State TEXT NOT NULL, "
					+ "PostCode TEXT NOT NULL, "
					+ "PRIMARY KEY (EmployeeEmail)," +
					"FOREIGN KEY (EmployeeEmail) REFERENCES Employee(Email));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			//Availability
			sql="CREATE TABLE Availability("
					+ "Day TEXT NOT NULL, "
					+ "StartTime TEXT NOT NULL, "
					+ "FinishTime TEXT NOT NULL, "
					+ "Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Email,Day,StartTime)," 
					+ "FOREIGN KEY (Email) REFERENCES Employee(Email));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			//Booking
			sql="CREATE TABLE Booking("
					+ "Date TEXT NOT NULL, "
					+ "StartTime TEXT NOT NULL, "
					+ "FinishTime NOT NULL, "
					+"EmployeeEmail TEXT NOT NULL, "
					+ "Username TEXT, " 
					+ "Type TEXT, "
					+ "PRIMARY KEY (Date,StartTime,EmployeeEmail)," 
					+ "FOREIGN KEY (EmployeeEmail) REFERENCES Employee(Email), " 
					+ "FOREIGN KEY (Username) REFERENCES Accounts(Username));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();



			//Enter dummy data
			//BusAccount 1
			sql="INSERT INTO Accounts(Username, Password, Name, ContactNo, Type, Address, Email) "
					+ "VALUES(?,?,?,?,?,?,?);";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "bus001");
			dataCont.getState().setString(2, "abc123");
			dataCont.getState().setString(3, "Johns Hairdressing");
			dataCont.getState().setString(4, "1300655506");
			dataCont.getState().setString(5, "Business");
			dataCont.getState().setString(6, "1 SQL Avenue");
			dataCont.getState().setString(7, "jwares@gmail.com");
			dataCont.runSQLUpdate();
			//Booking date
			sql="INSERT INTO System(BookingsUntil) "
					+ "VALUES(?);";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, LocalDate.now().toString());
			dataCont.runSQLUpdate();   
		
		} catch (Exception e) {
			dataCont.closeConnection();
			e.printStackTrace();
		}finally{
			dataCont.closeConnection();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		String sql="";
		DatabaseModel dataMod = new DatabaseModel();
		DatabaseController dataCont = new DatabaseController(dataMod);

		//Drop tables
		sql="DROP TABLE IF EXISTS Accounts; ";
		try {
			dataCont.createConnection();
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS Employee; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS Address; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS Availability; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS Booking; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql="DROP TABLE IF EXISTS System; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			
		} catch (Exception e) {
			dataCont.closeConnection();
			e.printStackTrace();
		}finally{
			dataCont.closeConnection();
		}
	}

	@Before
	public void setUp() throws Exception
	{

	}

	@After
	public void tearDown() throws Exception
	{

	}
	
	@Test
	public void testGetBooking(){
		BookingController bcont = new BookingController();
		LocalDate focusdate=LocalDate.now();
		DayOfWeek dow = focusdate.getDayOfWeek().plus(1);
		String empemail = "newemail@email.com";
		LocalTime start=LocalTime.now(),finish=LocalTime.now().plusHours(2);
		
		bcont.addBookings(dow, start, finish, empemail);
		
		assertTrue(bcont.getBookings().contains(dow));
		assertTrue(bcont.getPastBookings().contains(focusdate.getDayOfWeek().minus(1)));
	}
	
	@Test
	public void makeEmpBooking(){
		BookingController bcont = new BookingController();
		AvailabilitiesController acont = new AvailabilitiesController();
		LocalDate focusdate=LocalDate.now();
		DayOfWeek dow = focusdate.getDayOfWeek().plus(1);
		String empemail = "empemail@email.com";
		LocalTime start=LocalTime.now(),finish=LocalTime.now().plusHours(2);
		String startstring = start.toString(), finishstring = finish.toString();
		
		acont.addAvailability(empemail, dow, startstring, finishstring);
		bcont.addBookings(dow, start, finish, empemail);
		
		assertFalse(bcont.getBookings().isEmpty());
		assertTrue(bcont.getBookings().contains(dow));
		
	}
	
	@Test
	public void makeBookingCustomer(){
		BookingController bcont = new BookingController();
		AvailabilitiesController avcont = new AvailabilitiesController();
		LocalDate focusdate=LocalDate.now();
		DayOfWeek dow = focusdate.getDayOfWeek().plus(1);
		String empemail = "empemail@email.com";
		LocalTime start=LocalTime.now(),finish=LocalTime.now().plusHours(2);
		String startstring = start.toString(), finishstring = finish.toString();
		
		avcont.addAvailability(empemail, dow, startstring, finishstring);
		bcont.addBookings(dow, start, finish, empemail);
		
		assertFalse(bcont.getBookings().isEmpty());
		assertTrue(bcont.getBookings().contains(dow));
		
	}
	
}
