
import java.time.LocalDate;

import Controller.DatabaseController;
import Model.DatabaseModel;

public class Seed {
	public static void initialize() {
		String sql = "";
		DatabaseModel dataMod = new DatabaseModel();
		DatabaseController dataCont = new DatabaseController(dataMod);

		// Drop tables
		sql = "DROP TABLE IF EXISTS Accounts; ";
		try {
			dataCont.createConnection();
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql = "DROP TABLE IF EXISTS Employee; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql = "DROP TABLE IF EXISTS Address; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql = "DROP TABLE IF EXISTS Availability; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql = "DROP TABLE IF EXISTS Booking; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			sql = "DROP TABLE IF EXISTS System; ";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();

			// Create tables
			// System
			sql = "CREATE TABLE System(" + "BookingsUntil TEXT NOT NULL,PRIMARY KEY(BookingsUntil));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			// Accounts
			sql = "CREATE TABLE Accounts(" + "Username TEXT NOT NULL, " + "Password TEXT NOT NULL, "
					+ "Name TEXT NOT NULL, " + "ContactNo TEXT NOT NULL, " + "Type TEXT NOT NULL, "
					+ "Address TEXT NOT NULL, " + "Email TEXT NOT NULL, " + "PRIMARY KEY (Username));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			// Employee
			sql = "CREATE TABLE Employee(" + "Name TEXT NOT NULL, " + "ContactNo TEXT NOT NULL, "
					+ "Email TEXT NOT NULL, " + "PRIMARY KEY (Email));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			// Address
			sql = "CREATE TABLE Address(" + "EmployeeEmail TEXT NOT NULL, " + "StreetAddress TEXT NOT NULL, "
					+ "City TEXT NOT NULL, " + "State TEXT NOT NULL, " + "PostCode TEXT NOT NULL, "
					+ "PRIMARY KEY (EmployeeEmail)," + "FOREIGN KEY (EmployeeEmail) REFERENCES Employee(Email));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			// Availability
			sql = "CREATE TABLE Availability(" + "Day TEXT NOT NULL, " + "StartTime TEXT NOT NULL, "
					+ "FinishTime TEXT NOT NULL, " + "Email TEXT NOT NULL, " + "PRIMARY KEY (Email,Day,StartTime),"
					+ "FOREIGN KEY (Email) REFERENCES Employee(Email));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
			// Booking
			sql = "CREATE TABLE Booking(" + "Date TEXT NOT NULL, " + "StartTime TEXT NOT NULL, "
					+ "FinishTime TEXT NOT NULL, " + "EmployeeEmail TEXT NOT NULL, " + "Username TEXT, " + "Type TEXT, "
					+ "PRIMARY KEY (Date,StartTime,EmployeeEmail), "
					+ "FOREIGN KEY (EmployeeEmail) REFERENCES Employee(Email), "
					+ "FOREIGN KEY (Username) REFERENCES Accounts(Username));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();

			// Enter dummy data
			// BusAccount 1
			sql = "INSERT INTO Accounts(Username, Password, Name, ContactNo, Type, Address, Email) "
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

			// Booking date
			sql = "INSERT INTO System(BookingsUntil) " + "VALUES(?);";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, LocalDate.now().toString());
			dataCont.runSQLUpdate();

///////////// EMPLOYEE 1
			sql = "INSERT INTO Employee(Name, ContactNo, Email) " + "VALUES(?,?,?)";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "John Doe");
			dataCont.getState().setString(2, "0455555555");
			dataCont.getState().setString(3, "johndoe@example.com");
			dataCont.runSQLUpdate();

			// Employee 1 address
			sql = "INSERT INTO Address(EmployeeEmail, StreetAddress, City, State, PostCode) " + "VALUES(?,?,?,?,?)";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "johndoe@example.com");
			dataCont.getState().setString(2, "123 Some St");
			dataCont.getState().setString(3, "Melbourne");
			dataCont.getState().setString(4, "VIC");
			dataCont.getState().setString(5, "3000");
			dataCont.runSQLUpdate();

			// Employee 1 Availability 1
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "MONDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "johndoe@example.com");
			dataCont.runSQLUpdate();

			// Employee 1 Availbility 2
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "TUESDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "johndoe@example.com");
			dataCont.runSQLUpdate();

			// Employee 1 Availbility 3
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "WEDNESDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "johndoe@example.com");
			dataCont.runSQLUpdate();

			///////////// EMPLOYEE 2
			sql = "INSERT INTO Employee(Name, ContactNo, Email) " + "VALUES(?,?,?)";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "Jane Oliver");
			dataCont.getState().setString(2, "0455555555");
			dataCont.getState().setString(3, "janeoliver@example.com");
			dataCont.runSQLUpdate();
			dataCont.runSQLUpdate();

			// Employee 2 address
			sql = "INSERT INTO Address(EmployeeEmail, StreetAddress, City, State, PostCode) " + "VALUES(?,?,?,?,?)";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "janeoliver@example.com");
			dataCont.getState().setString(2, "456 Some St");
			dataCont.getState().setString(3, "Melbourne");
			dataCont.getState().setString(4, "VIC");
			dataCont.getState().setString(5, "3000");
			dataCont.runSQLUpdate();

			// Employee 2 Availbility 1
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "THURSDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "janeoliver@example.com");
			dataCont.runSQLUpdate();

			// Employee 2 Availbility 2
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.runSQLUpdate();
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "FRIDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "janeoliver@example.com");
			dataCont.runSQLUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		dataCont.closeConnection();

	}
}
