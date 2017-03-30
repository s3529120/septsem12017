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
import Controller.UserRegistrationController;
import Model.DatabaseModel;
import View.UserRegistrationView;

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
			cont.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
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
	public void testRegister()
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

}
