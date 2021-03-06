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
import utils.DatabaseController;
import utils.DatabaseModel;

public class ViewBookingsTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
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
		DayOfWeek dow = focusdate.getDayOfWeek();
		String empemail = "newemail@email.com";
		LocalTime start=LocalTime.of(14, 00),finish=LocalTime.of(15, 00);
		
		bcont.addBookings(dow, start, finish, empemail);
		
		try{
			assertTrue(bcont.getBookings().contains(dow));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testForBooking(){
		BookingController bcont = new BookingController();
		LocalDate focusdate=LocalDate.now();
		DayOfWeek dow = focusdate.getDayOfWeek();
		String empemail = "newemail@email.com";
		LocalTime start=LocalTime.of(16, 00),finish=LocalTime.of(18, 00);
		
		bcont.addBookings(dow, start, finish, empemail);
		
		try{
			assertFalse(bcont.getBookings().isEmpty());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
