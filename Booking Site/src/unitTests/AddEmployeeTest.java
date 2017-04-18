package unitTests;

import static org.junit.Assert.*;

import java.awt.TextField;
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

		}catch (Exception e) {
			cont.closeConnection();
			e.printStackTrace();
		}finally{
			cont.closeConnection();
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
			cont.closeConnection();
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
	
		String name="John Smith", num="0555 555 555",email="JSemail@gmail.com",
				add="123 Fake Street",city="Melbourne",state="Victoria",postc="3000";

		EmployeeController econt = new EmployeeController();

		try{
			econt.addEmployee(name, num, email, add, city, state, postc);
			
			assertTrue(econt.checkEmployee(email));
			assertEquals(econt.getEmployeeName(email), name);	

		}catch(Exception e){
			e.printStackTrace();
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
			dbcont.closeConnection();
			fail("SQLException Error.\nTest Failure.\nStack Trace: ");
			e.printStackTrace();
		}finally{
			dbcont.closeConnection();
		}
	}
	
	@Test
	public void testAddEmpNoFirstName(){
		EmployeeController econt = new EmployeeController();
		String name="", contactno="0555 555 555", email="nullemail@gmail.com", 
				streetadd="123 fake st", city="Melbourne", state="VIC", postcode="3000";
		
		try{
			econt.addEmployee(name, contactno, email, streetadd, city, state, postcode);
			
			/* Get the employee name assigned to email, should return empty, 
			 * 		and isEmpty() should return true. */
			assertTrue(econt.getEmployeeName(email).isEmpty());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNameGtrMaxSize(){
		/* Hard to test as max text field is 10^9 bits according to docs. 
			From https://sqlite.org/faq.html#q9, 
			"Sqlite will be happy to store a 500-million [varchar in TEXT]" */
		EmployeeController econt = new EmployeeController();
		String name="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa b", 
				contactno="0555 555 555", email="maxlengthemail@gmail.com", 
				streetadd="123 fake st", city="Melbourne", state="VIC", postcode="3000";
		
		try{
			econt.addEmployee(name, contactno, email, streetadd, city, state, postcode);
			
			/* Get the employee name assigned to email, should return empty, 
			 * 		as was too long to fit into database.
			 * This test will always evaluate as false as unable to actually break the max limit on TEXT */
			assertTrue(econt.getEmployeeName(email).length() == 0);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddEmpNoLastName(){
		EmployeeController econt = new EmployeeController();
		String fname="Jim", lname= " ", name = fname+" "+lname, contactno="0555 555 555", email="nolnameemail@gmail.com", 
				streetadd="123 fake st", city="Melbourne", state="VIC", postcode="3000";
		
		try{
			econt.addEmployee(name, contactno, email, streetadd, city, state, postcode);
			
			System.out.println(econt.getEmployeeName(email).length());
			/* Get the employee name assigned to email, and compare length with some known value. */
			assertEquals(econt.getEmployeeName(email).length(), name.length());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
