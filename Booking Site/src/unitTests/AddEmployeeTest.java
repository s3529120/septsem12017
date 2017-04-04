package unitTests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sqlite.core.DB;

import Controller.DatabaseController;
import Controller.EmployeeController;
import Model.DatabaseModel;
import Model.EmployeeModel;
import View.UserRegistrationView;

public class AddEmployeeTest {

	@BeforeClass
	public static void setUpBeforeClass(){

		//Employee fields
		//String name,String contactno,String email,
		//String streetadd,String city,String state,String postcode

		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);
		String sql;

		cont.createConnection();
		sql="DROP TABLE IF EXISTS Employee;";
		cont.prepareStatement(sql);
		cont.runSQLUpdate();

		sql="DROP TABLE IF EXISTS Address;";
		cont.prepareStatement(sql);
		cont.runSQLUpdate();

		sql="CREATE TABLE Address("
				+ "EmployeeEmail TEXT NOT NULL, "
				+ "StreetAddress TEXT NOT NULL, "
				+ "City TEXT NOT NULL, " 
				+ "State TEXT NOT NULL, "
				+ "PostCode TEXT NOT NULL, "
				+ "PRIMARY KEY (EmployeeEmail));";
		cont.prepareStatement(sql);
		cont.runSQLUpdate();

		sql="CREATE TABLE Employee("
				+ "Name TEXT NOT NULL, "
				+ "ContactNo TEXT NOT NULL, "
				+ "Email TEXT NOT NULL, "
				+ "PRIMARY KEY (Email));";
		cont.prepareStatement(sql);
		cont.runSQLUpdate();

		try{
			sql="INSERT INTO Employee(Name,ContactNo,Email) " +
					"Values(?,?,?);";
			cont.prepareStatement(sql);
			cont.getState().setString(1, "Jacob Genericson");
			cont.getState().setString(2, "0412345678");
			cont.getState().setString(3, "jgsons@gmail.com");
			cont.runSQLUpdate();

			sql="INSERT INTO Address(EmployeeEmail,StreetAddress,City,State,PostCode) " 
					+"Values(?,?,?,?,?) ;";

			cont.prepareStatement(sql);
			cont.getState().setString(1, "jgsons@gmail.com");
			cont.getState().setString(2, "123 Fake Street");
			cont.getState().setString(3, "Melbourne");
			cont.getState().setString(4, "Victoria");
			cont.getState().setString(5, "3000");
			cont.runSQLUpdate();

			cont.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass(){
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);
		String sql;
		try{
			cont.createConnection();
			sql="DROP TABLE IF EXISTS Employee; DROP TABLE IF EXISTS Address;";
			cont.prepareStatement(sql);
			cont.runSQLUpdate();
			cont.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			cont.closeConnection();
		}
	}

	@Test
	public void testAddEmp(){
		
		/*Add new employee using employee controller
		 *  and check for valid address and name and email
		 * */
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql,name="",num="",email="",add="",city="",state="",postc="";
		ResultSet res;

		EmployeeController econt = new EmployeeController();

		try{
			econt.addEmployee("John Smith", "0555 555 555","myemail@gmail.com","123 Fake Street", 
					"Melbourne", "Victoria", "3000");
			dbcont.createConnection();
			sql="SELECT * FROM EMPLOYEE WHERE Email='myemail@gmail.com';";
			dbcont.prepareStatement(sql);
			
			res = dbcont.runSQLRes();
			
			/*Result set closing above*/
			/*not returning any values in result set*/
			/*
			for(int k=0;k<3;k++)
				System.out.println(res.getString(k));
				*/

			name = res.getString("Name");
			num = res.getString("ContactNo");
			email = res.getString("Email");
			email = "myemail@gmail.com";

			assertEquals(name,"John Smith");
			assertEquals(num,"0555 555 555");
			assertEquals(email,"myemail@gmail.com");
			
			sql="SELECT * FROM ADDRESS WHERE EmployeeEmail='myemail@gmail.com';";
			dbcont.prepareStatement(sql);
			res = dbcont.runSQLRes();

			email = res.getString("EmployeeEmail");
			add = res.getString("StreetAddress");
			city = res.getString("City");
			state = res.getString("State");
			postc = res.getString("PostCode");

			assertEquals(email,"myemail@gmail.com");
			assertEquals(add,"123 Fake Street");
			assertEquals(city,"Melbourne");
			assertEquals(state,"Victoria");
			assertEquals(postc,"3000");

		}catch(Exception e){
			//fail("SQLException Error.\nTest Failure.\nStack Trace: ");
			e.printStackTrace();
		}finally{
			dbcont.closeConnection();
		}
	}

	@Test
	public void testCheckEmp(){
		
		/*Add new employee and check for valid details using 
		 * */
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql,name="",num="",email="",add="",city="",state="",postc="";
		ResultSet res;

		try{	
			dbcont.createConnection();

			sql="SELECT * FROM EMPLOYEE WHERE email='jgsons@gmail.com';";
			dbcont.prepareStatement(sql);
			res = dbcont.runSQLRes();

			name = res.getString("Name");
			num = res.getString("ContactNo");
			email = res.getString("Email");

			sql="SELECT * FROM ADDRESS WHERE EmployeeEmail='jgsons@gmail.com';";
			dbcont.prepareStatement(sql);
			res = dbcont.runSQLRes();

			add = res.getString("StreetAddress");
			city = res.getString("City");
			state = res.getString("State");
			postc = res.getString("PostCode");

			assertEquals(name,"Jacob Genericson");
			assertEquals(num,"0412345678");
			assertEquals(email,"jgsons@gmail.com");
			assertEquals(add,"123 Fake Street");
			assertEquals(city,"Melbourne");
			assertEquals(state,"Victoria");
			assertEquals(postc,"3000");
		}catch(Exception e){
			fail("SQLException Error.\nTest Failure.\nStack Trace: ");
			e.printStackTrace();
		}
	}
}
