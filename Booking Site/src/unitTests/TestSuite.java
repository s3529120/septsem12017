package unitTests;



import org.junit.AfterClass;
import org.junit.BeforeClass;

import Controller.DatabaseController;
import Model.DatabaseModel;

public class TestSuite {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		try{
			DatabaseModel mod = new DatabaseModel();
			DatabaseController cont = new DatabaseController(mod);
			String sql;

			cont.createConnection();
			sql="DROP TABLE IF EXISTS Accounts;";
			cont.prepareStatement(sql);
			cont.runSQLUpdate();

			sql="CREATE TABLE Accounts("
					+ "Username TEXT NOT NULL, "
					+ "Password TEXT NOT NULL, "
					+ "Name TEXT NOT NULL, "
					+ "ContactNo TEXT NOT NULL, "
					+ "Type TEXT NOT NULL, "
					+ "Address TEXT NOT NULL, " 
					+ "Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Username));";
			cont.prepareStatement(sql);
			cont.runSQLUpdate();

			sql="INSERT INTO ACCOUNTS(Username,Password,Name,ContactNo,Address,Email,Type) " +
					"Values(?,?,?,?,?,?,?);";
			cont.prepareStatement(sql);
			cont.getState().setString(1, "usr003");
			cont.getState().setString(2, "wifesname");
			cont.getState().setString(3, "Jacob Genericson");
			cont.getState().setString(4, "0412345678");
			cont.getState().setString(5, "88  Warning Avenue");
			cont.getState().setString(6, "jgsons@gmail.com");
			cont.getState().setString(7, "User");
			cont.runSQLUpdate();
			cont.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		try{
			DatabaseModel mod = new DatabaseModel();
			DatabaseController cont = new DatabaseController(mod);
			String sql;

			cont.createConnection();
			sql="DROP TABLE IF EXISTS Accounts;";
			cont.prepareStatement(sql);
			cont.runSQLUpdate();
			cont.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runAllTests()
	{
		LoginTest ltest = new LoginTest();
		RegistrationTest rtest = new RegistrationTest();
		AddEmployeeTest aetest = new AddEmployeeTest();

		ltest.testCheckAccountType();
		ltest.testComparePassword();
		ltest.testCheckUsername();
		
		rtest.testRegister();
	}
}
