package unitTests;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import Controller.AccountController;
import Controller.DatabaseController;
import Controller.LoginController;
import Model.DatabaseModel;

public class LoginTest
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{

	}

	@Before
	public void setUp() throws Exception
	{
		PreparedStatement state;
		String sql;
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);

		try {
			sql = "DROP TABLE IF EXISTS Accounts;";
			cont.runSQLUpdate(cont.prepareStatement(sql));

			sql="CREATE TABLE Accounts("
					+ "Username TEXT NOT NULL, "
					+ "Password TEXT NOT NULL, "
					+ "Name TEXT NOT NULL, "
					+ "ContactNo TEXT NOT NULL, "
					+ "Type TEXT NOT NULL, "
					+ "Address TEXT NOT NULL, " 
					+"Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Username));";

			cont.runSQLUpdate(cont.prepareStatement(sql));



			sql="INSERT INTO ACCOUNTS(Username,Password,Name,ContactNo,Address,Email,Type) " +
					"Values(?,?,?,?,?,?,?);";
			state=cont.prepareStatement(sql);
			state.setString(1, "bus002");
			state.setString(2, "password");
			state.setString(3, "Billy;s Bargains");
			state.setString(4, "0387654321");
			state.setString(5, "13 JUnit Place");
			state.setString(6, "bbargains@gmail.com");
			state.setString(7, "Business");
			cont.runSQLUpdate(state);

			sql="INSERT INTO ACCOUNTS(Username,Password,Name,ContactNo,Address,Email,Type) " +
					"Values(?,?,?,?,?,?,?);";
			state=cont.prepareStatement(sql);
			state.setString(1, "usr002");
			state.setString(2, "wifesname");
			state.setString(3, "Jacob Genericson");
			state.setString(4, "0412345678");
			state.setString(5, "88  Warning Avenue");
			state.setString(6, "jgsons@gmail.com");
			state.setString(7, "User");
			cont.runSQLUpdate(state);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception
	{
		PreparedStatement state;
		String sql = null;
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);

		/*
		 * Method to remove any temporary data used for the testing
		 * 	-Purge the database of any and all tables made during the tests  
		 *  -Close active connection
		 */
		try {
			sql = "DROP TABLE IF EXISTS Accounts;";
			state=cont.prepareStatement(sql);
			cont.runSQLUpdate(state);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cont.prepareStatement(null).close();
		}
	}

	@Test
	public void testCheckAccountType()
	{
		AccountController acont = new AccountController();
		try {
			assertEquals("User",acont.checkAccountType("usr002"));
			assertEquals("Business",acont.checkAccountType("bus002"));
		} finally {
			
		}
	}

	@Test
	public void testCheckUsername()
	{
		AccountController acont = new AccountController();

		try{
			assertTrue(acont.checkUsername("usr002"));
			assertTrue(acont.checkUsername("bus002"));
			assertFalse(acont.checkUsername("ihgbfduhiew"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testComparePassword() throws SQLException
	{
		AccountController acont = new AccountController();

		assertTrue(acont.comparePassword("usr002","wifesname"));
		assertTrue(acont.comparePassword("bus002","password"));
		assertFalse(acont.comparePassword("usr002","ihgbfduhiew"));
		assertFalse(acont.comparePassword("bus002","ihgbfduhiew"));
		//check for non-validation with other user creds
		assertFalse(acont.comparePassword("bus002","wifesname"));
		assertFalse(acont.comparePassword("usr002","password"));
		
	}


}
