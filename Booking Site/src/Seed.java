

import Controller.DatabaseController;
import Model.DatabaseModel;

public class Seed {
	public static void initialize(){
		String sql="";
		DatabaseModel dataMod = new DatabaseModel();
		DatabaseController dataCont = new DatabaseController(dataMod);
		
		
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
         sql="CREATE TABLE Availability("
               + "Date TEXT NOT NULL, "
               + "StartTime TEXT NOT NULL, "
               + "FinishTime TEXT NOT NULL, "
               +"Email TEXT NOT NULL, "
               + "PRIMARY KEY (Email,Date,StartTime));";
         dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
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
			sql="CREATE TABLE Address("
               + "EmployeeEmail TEXT NOT NULL, "
               + "StreetAddress TEXT NOT NULL, "
               + "City TEXT NOT NULL, " 
               + "State TEXT NOT NULL, "
               + "PostCode TEXT NOT NULL, "
               + "PRIMARY KEY (EmployeeEmail));";
         dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
         sql="CREATE TABLE Employee("
               + "Name TEXT NOT NULL, "
               + "ContactNo TEXT NOT NULL, "
               + "Email TEXT NOT NULL, "
               + "PRIMARY KEY (Email));";
         dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
			sql="INSERT INTO Accounts(Username, Password, Name, ContactNo, Type, Address, Email) "
					+ "VALUES(?,?,?,?,?,?,?);";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "bus001");
			dataCont.getState().setString(2, "abc123");
			dataCont.getState().setString(3, "Johns Wares");
			dataCont.getState().setString(4, "1300655506");
			dataCont.getState().setString(5, "Business");
			dataCont.getState().setString(6, "1 SQL Avenue");
			dataCont.getState().setString(7, "jwares@gmail.com");
			dataCont.runSQLUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataCont.closeConnection();
	}
}
