

import java.time.LocalDate;

import Controller.DatabaseController;
import Model.DatabaseModel;

public class Seed {
	public static void initialize(){
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
               + "PRIMARY KEY (Email,Day,StartTime)," +
               "FOREIGN KEY (Email) REFERENCES Employee(Email));";
         dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
         //Booking
         sql="CREATE TABLE Booking("
               + "Date TEXT NOT NULL, "
               + "StartTime TEXT NOT NULL, "
               + "FinishTime NOT NULL, "
               +"EmployeeEmail TEXT NOT NULL, "
               + "PRIMARY KEY (Date,StartTime,EmployeeEmail)," +
               "FOREIGN KEY (EmployeeEmail) REFERENCES Employee(Email));";
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
			e.printStackTrace();
		}
		dataCont.closeConnection();
	}
}
