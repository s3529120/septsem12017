import java.time.LocalDate;

import utils.database.DatabaseController;
import utils.database.DatabaseModel;

public class Seed {
   
   /*
    * Seeds table with data for demonstration purposes, also clears existing
    * data on startup.
    */
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
			sql = "DROP TABLE IF EXISTS Type; ";
         dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
         sql = "DROP TABLE IF EXISTS Spec; ";
         dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
         sql = "DROP TABLE IF EXISTS Trading; ";
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
			// Type
         sql = "CREATE TABLE Type(Type TEXT NOT NULL, Duration INTEGER NOT NULL, PRIMARY KEY (Type));";
         dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
      // Spec
         sql = "CREATE TABLE Spec(Type TEXT NOT NULL, EmployeeEmail TEXT NOT NULL, PRIMARY KEY (Type, EmployeeEmail), " +
               "FOREIGN KEY (Type) REFERENCES Type(Type), " +
               "FOREIGN KEY (EmployeeEmail) REFERENCES Employee(Email));";
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
					+ "FOREIGN KEY (Type) REFERENCES Type(Type), "
					+ "FOREIGN KEY (Username) REFERENCES Accounts(Username));";
			dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate();
		// Trading Hours
			sql = "CREATE TABLE Trading(" + "Day TEXT NOT NULL, " + "StartTime TEXT NOT NULL, "
               + "FinishTime TEXT NOT NULL, " + "Username TEXT NOT NULL, " + "PRIMARY KEY (Username,Day,StartTime),"
               + "FOREIGN KEY (Username) REFERENCES Accounts(Username));";
         dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();

			// Enter dummy data
			// BusAccount 1
			sql = "INSERT INTO Accounts(Username, Password, Name, ContactNo, Type, Address, Email) "
					+ "VALUES(?,?,?,?,?,?,?);";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "bus001");
			dataCont.getState().setString(2, "abc123");
			dataCont.getState().setString(3, "Francois le toiletteur pour animaux");
			dataCont.getState().setString(4, "1300655506");
			dataCont.getState().setString(5, "Business");
			dataCont.getState().setString(6, "1 Groomer Avenue");
			dataCont.getState().setString(7, "francois@gmail.com");
			dataCont.runSQLUpdate();
			
			// Super User
			sql = "INSERT INTO Accounts(Username, Password, Name, ContactNo, Type, Address, Email) "
					+ "VALUES(?,?,?,?,?,?,?);";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "super");
			dataCont.getState().setString(2, "abc123");
			dataCont.getState().setString(3, "Admin");
			dataCont.getState().setString(4, "");
			dataCont.getState().setString(5, "Super");
			dataCont.getState().setString(6, "");
			dataCont.getState().setString(7, "admin@operator.owner");
			dataCont.runSQLUpdate();
			
			//Availabilities
			//Monday
			sql = "INSERT INTO Trading(Username, Day, StartTime, FinishTime)"
               + "VALUES(?,?,?,?);";
         dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "bus001");
         dataCont.getState().setString(2, "Monday");
         dataCont.getState().setString(3, "10:00");
         dataCont.getState().setString(4, "18:00");
         dataCont.runSQLUpdate();
       //Tuesday
         sql = "INSERT INTO Trading(Username, Day, StartTime, FinishTime)"
               + "VALUES(?,?,?,?);";
         dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "bus001");
         dataCont.getState().setString(2, "Tuesday");
         dataCont.getState().setString(3, "10:00");
         dataCont.getState().setString(4, "18:00");
         dataCont.runSQLUpdate();
       //Wednesday
         sql = "INSERT INTO Trading(Username, Day, StartTime, FinishTime)"
               + "VALUES(?,?,?,?);";
         dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "bus001");
         dataCont.getState().setString(2, "Wednesday");
         dataCont.getState().setString(3, "10:00");
         dataCont.getState().setString(4, "18:00");
         dataCont.runSQLUpdate();
       //Thursday
         sql = "INSERT INTO Trading(Username, Day, StartTime, FinishTime)"
               + "VALUES(?,?,?,?);";
         dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "bus001");
         dataCont.getState().setString(2, "Thursday");
         dataCont.getState().setString(3, "10:00");
         dataCont.getState().setString(4, "18:00");
         dataCont.runSQLUpdate();
       //Friday
         sql = "INSERT INTO Trading(Username, Day, StartTime, FinishTime)"
               + "VALUES(?,?,?,?);";
         dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "bus001");
         dataCont.getState().setString(2, "Friday");
         dataCont.getState().setString(3, "10:00");
         dataCont.getState().setString(4, "18:00");
         dataCont.runSQLUpdate();
       //Saturday
         sql = "INSERT INTO Trading(Username, Day, StartTime, FinishTime)"
               + "VALUES(?,?,?,?);";
         dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "bus001");
         dataCont.getState().setString(2, "Saturday");
         dataCont.getState().setString(3, "10:00");
         dataCont.getState().setString(4, "18:00");
         dataCont.runSQLUpdate();
       //Sunday
         sql = "INSERT INTO Trading(Username, Day, StartTime, FinishTime)"
               + "VALUES(?,?,?,?);";
         dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "bus001");
         dataCont.getState().setString(2, "Sunday");
         dataCont.getState().setString(3, "10:00");
         dataCont.getState().setString(4, "18:00");
         dataCont.runSQLUpdate();
         
			// Booking date
			sql = "INSERT INTO System(BookingsUntil) " + "VALUES(?);";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, LocalDate.now().toString());
			dataCont.runSQLUpdate();
			
			//Types
			sql="INSERT INTO Type(Type,Duration) Values(?,?);";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "Grooming");
			dataCont.getState().setInt(2, 15);
			dataCont.runSQLUpdate();
			sql="INSERT INTO Type(Type,Duration) Values(?,?);";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "None");
			dataCont.getState().setInt(2, 15);
         dataCont.runSQLUpdate();
         sql="INSERT INTO Type(Type,Duration) Values(?,?);";
			dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "Cut");
			dataCont.getState().setInt(2, 30);
         dataCont.runSQLUpdate();
         sql="INSERT INTO Type(Type,Duration) Values(?,?);";
			dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "Style");
			dataCont.getState().setInt(2, 30);
         dataCont.runSQLUpdate();
         sql="INSERT INTO Type(Type,Duration) Values(?,?);";
			dataCont.prepareStatement(sql);
         dataCont.getState().setString(1, "Cut and Style");
			dataCont.getState().setInt(2, 45);
         dataCont.runSQLUpdate();

///////////// EMPLOYEE 1
			sql = "INSERT INTO Employee(Name, ContactNo, Email) " + "VALUES(?,?,?)";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "John Doe");
			dataCont.getState().setString(2, "0455555555");
			dataCont.getState().setString(3, "johndoe@example.com");
			dataCont.runSQLUpdate();
			
			//Employee 1 Spec
			sql="INSERT INTO Spec(Type,EmployeeEmail) Values('Grooming','johndoe@example.com');";
			dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
         sql="INSERT INTO Spec(Type,EmployeeEmail) Values('None','johndoe@example.com');";
			dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();

			// Employee 1 address
			sql = "INSERT INTO Address(EmployeeEmail, StreetAddress, City, State, PostCode) " + "VALUES(?,?,?,?,?)";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "johndoe@example.com");
			dataCont.getState().setString(2, "123 Some St");
			dataCont.getState().setString(3, "Melbourne");
			dataCont.getState().setString(4, "VIC");
			dataCont.getState().setString(5, "3000");
			dataCont.runSQLUpdate();

			// Employee 1 Availability 1
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "MONDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "johndoe@example.com");
			dataCont.runSQLUpdate();

			// Employee 1 Availbility 2
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "TUESDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "johndoe@example.com");
			dataCont.runSQLUpdate();

			// Employee 1 Availbility 3
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "WEDNESDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "johndoe@example.com");
			dataCont.runSQLUpdate();

			///////////// EMPLOYEE 2
			sql = "INSERT INTO Employee(Name, ContactNo, Email) " + "VALUES(?,?,?)";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "Jane Oliver");
			dataCont.getState().setString(2, "0455555555");
			dataCont.getState().setString(3, "janeoliver@example.com");
			dataCont.runSQLUpdate();
			
			//Employee 2 Spec
         sql="INSERT INTO Spec(Type,EmployeeEmail) Values('Cut','janeoliver@example.com');";
			dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
         sql="INSERT INTO Spec(Type,EmployeeEmail) Values('None','johndoe@example.com');";
			dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
         sql="INSERT INTO Spec(Type,EmployeeEmail) Values('Style','johndoe@example.com');";
			dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();
         sql="INSERT INTO Spec(Type,EmployeeEmail) Values('Cut and Style','johndoe@example.com');";
			dataCont.prepareStatement(sql);
         dataCont.runSQLUpdate();

			// Employee 2 address
			sql = "INSERT INTO Address(EmployeeEmail, StreetAddress, City, State, PostCode) " + "VALUES(?,?,?,?,?)";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "janeoliver@example.com");
			dataCont.getState().setString(2, "456 Some St");
			dataCont.getState().setString(3, "Melbourne");
			dataCont.getState().setString(4, "VIC");
			dataCont.getState().setString(5, "3000");
			dataCont.runSQLUpdate();

			// Employee 2 Availbility 1
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "THURSDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "janeoliver@example.com");
			dataCont.runSQLUpdate();

			// Employee 2 Availbility 2
			sql = "INSERT INTO Availability(Day, StartTime, FinishTime, Email) " + "VALUES(?,?,?,?)";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "FRIDAY");
			dataCont.getState().setString(2, "10:00");
			dataCont.getState().setString(3, "18:00");
			dataCont.getState().setString(4, "janeoliver@example.com");
			dataCont.runSQLUpdate();
			
			//Customer 1
			sql = "INSERT INTO Accounts(Username, Password, Name, ContactNo, Type, Address, Email) "
					+ "VALUES(?,?,?,?,?,?,?);";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "cus001");
			dataCont.getState().setString(2, "abc123");
			dataCont.getState().setString(3, "Adam Adamson");
			dataCont.getState().setString(4, "1234567890");
			dataCont.getState().setString(5, "User");
			dataCont.getState().setString(6, "1 abc Avenue");
			dataCont.getState().setString(7, "aadamson@gmail.com");
			dataCont.runSQLUpdate();
			
			//Customer 2
			sql = "INSERT INTO Accounts(Username, Password, Name, ContactNo, Type, Address, Email) "
					+ "VALUES(?,?,?,?,?,?,?);";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "cus002");
			dataCont.getState().setString(2, "abc234");
			dataCont.getState().setString(3, "Bob Bobson");
			dataCont.getState().setString(4, "2345678901");
			dataCont.getState().setString(5, "User");
			dataCont.getState().setString(6, "2 abc Avenue");
			dataCont.getState().setString(7, "bbobson@gmail.com");
			dataCont.runSQLUpdate();
			
			//Customer 3
			sql = "INSERT INTO Accounts(Username, Password, Name, ContactNo, Type, Address, Email) "
					+ "VALUES(?,?,?,?,?,?,?);";
			dataCont.prepareStatement(sql);
			dataCont.getState().setString(1, "cus003");
			dataCont.getState().setString(2, "abc345");
			dataCont.getState().setString(3, "Carl Carlson");
			dataCont.getState().setString(4, "3456789012");
			dataCont.getState().setString(5, "User");
			dataCont.getState().setString(6, "3 abc Avenue");
			dataCont.getState().setString(7, "ccarlson@gmail.com");
			dataCont.runSQLUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		dataCont.closeConnection();

	}
}
