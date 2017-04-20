package unitTests;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Controller.DatabaseController;
import Controller.LoginController;
import Controller.UserRegistrationController;
import Model.DatabaseModel;
import View.LoginView;
import View.UserRegistrationView;

/*
 * Need to separate tests.
 * Just call 
 * 	String sql;
	DatabaseModel mod = new DatabaseModel();
	DatabaseController cont = new DatabaseController(mod);
 * */

public class RegistrationTest
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);
		String sql; 
		try{
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
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);
		String sql;
		try{
			cont.createConnection();
			sql="DROP TABLE IF EXISTS Accounts;";
			cont.prepareStatement(sql);
			cont.runSQLUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
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
	public void testValidRegister()
	{
		DatabaseModel mod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(mod);
		String sql,uname="",pname="",pword="",add="",num="",email="",type="";
		ResultSet res;

		UserRegistrationController cont = new UserRegistrationController(new UserRegistrationView(null));
		try{
			cont.register("usr004", "Anany Levitin", "pass", "3 Edition Street",
					"0132316811", "pearson@gmail.com");
			dbcont.createConnection();
			sql="SELECT * FROM ACCOUNTS WHERE Username='usr004';";
			dbcont.prepareStatement(sql);

			res=dbcont.runSQLRes();
			uname=res.getString("Username");
			pname=res.getString("Name");
			pword=res.getString("Password");
			add=res.getString("Address");
			num=res.getString("ContactNo");
			email=res.getString("Email");
			type=res.getString("Type");

			assertEquals(uname,"usr004");
			assertEquals(pname,"Anany Levitin");
			assertEquals(pword,"pass");
			assertEquals(add,"3 Edition Street");
			assertEquals(num,"0132316811");
			assertEquals(email,"pearson@gmail.com");
			assertEquals(type,"User");
			
			dbcont.closeConnection();
		}catch(SQLException e){
			fail("SQLException generated");
		}
	}
	
	@Test
	public void testDuplicateRegister()
	{
		UserRegistrationController cont = new UserRegistrationController(null);
		LoginController lcont = new LoginController(null);
		String uname="usr004",pname="Jonathon Smithone",pword="mycatsname123",
				address="62 Eagle Drive",contactNo="0123456789",email="user123@email.com";
		
		try{
			cont.register(uname, pname, pword, address, contactNo, email);
			
			assertFalse(lcont.login(uname, pword));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
