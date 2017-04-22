package unitTests;

import static org.junit.Assert.*;

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
		String sql;
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);

		try {
			cont.createConnection();
			sql = "DROP TABLE IF EXISTS Accounts;";
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
			cont.getState().setString(1, "bus002");
			cont.getState().setString(2, "password");
			cont.getState().setString(3, "Billy's Bargains");
			cont.getState().setString(4, "0387654321");
			cont.getState().setString(5, "13 JUnit Place");
			cont.getState().setString(6, "bbargains@gmail.com");
			cont.getState().setString(7, "Business");
			cont.runSQLUpdate();

			sql="INSERT INTO ACCOUNTS(Username,Password,Name,ContactNo,Address,Email,Type) " +
					"Values(?,?,?,?,?,?,?);";
			cont.prepareStatement(sql);
			cont.getState().setString(1, "usr002");
			cont.getState().setString(2, "wifesname");
			cont.getState().setString(3, "Jacob Genericson");
			cont.getState().setString(4, "0412345678");
			cont.getState().setString(5, "88  Warning Avenue");
			cont.getState().setString(6, "jgsons@gmail.com");
			cont.getState().setString(7, "User");
			cont.runSQLUpdate();
		} catch (Exception e) {
			cont.closeConnection();
			e.printStackTrace();
		}finally{
			cont.closeConnection();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		String sql = null;
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);

		/*
		 * Method to remove any temporary data used for the testing
		 * 	-Purge the database of any and all tables made during the tests  
		 *  -Close active connection
		 */
		try {
			cont.createConnection();
			sql = "DROP TABLE IF EXISTS Accounts;";
			cont.prepareStatement(sql);
			cont.runSQLUpdate();
		} catch (Exception e) {
			cont.closeConnection();
			e.printStackTrace();
		} finally {
			cont.closeConnection();
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
	public void testCheckAccountType()
	{
		AccountController acont = new AccountController();
		try {
			assertEquals("User",acont.checkAccountType("usr002"));
			assertEquals("Business",acont.checkAccountType("bus002"));
		}catch(Exception e) {
			fail("SQLException generated");
		}
	}
	
	/*
	 * Test for valid users
	 */
	@Test
	public void testCheckValidUsername()
	{
		AccountController acont = new AccountController();
		try{
			assertTrue(acont.checkUsername("usr002"));
			assertTrue(acont.checkUsername("bus002"));
		}catch(Exception e) {
			fail("SQLException generated");
		}
	}

	/*
	 * Test for invalid users
	 */
	@Test
	public void testCheckInvalidUsername()
	{
		AccountController acont = new AccountController();
		try{
			assertFalse(acont.checkUsername("ihgbfduhiew"));
		}catch(Exception e) {
			fail("SQLException generated");
		}
	}
	/*
	 * Test for Correct creds
	 */
	@Test
	public void testComparePassword()
	{
		AccountController acont = new AccountController();
		try{
			assertTrue(acont.comparePassword("usr002","wifesname"));
			assertTrue(acont.comparePassword("bus002","password"));
		}catch(Exception e) {
			fail("SQLException generated");
		}
	}

	/*
	 * Test for mismatched credentials (both password and username are in db)
	 */
	@Test
	public void testInvalidCreds(){
		AccountController acont = new AccountController();
		try{
			//check for non-validation with other user creds
			assertFalse(acont.comparePassword("bus002","wifesname"));
			assertFalse(acont.comparePassword("usr002","password"));
		}catch(Exception e){
			fail("SQLException generated"); 
		}
	}
	
	/*
	 * Test for incorrect password
	 */
	@Test
	public void testIncorrectPassword()
	{
		AccountController acont = new AccountController();
		try{
			assertFalse(acont.comparePassword("usr002","ihgbfduhiew"));
			assertFalse(acont.comparePassword("bus002","ihgbfduhiew"));
		}catch(Exception e) {
			fail("SQLException generated");
		}
	}

}
