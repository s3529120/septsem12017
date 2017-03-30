package unitTests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import Controller.DatabaseController;
import Controller.EmployeeController;
import Model.DatabaseModel;
import Model.EmployeeModel;

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

	//	@AfterClass
	//	public static void tearDownAfterClass(){
	//		DatabaseModel mod = new DatabaseModel();
	//		DatabaseController cont = new DatabaseController(mod);
	//		String sql;
	//		try{
	//			cont.createConnection();
	//			sql="DROP TABLE IF EXISTS Employee; DROP TABLE IF EXISTS Address;";
	//			cont.prepareStatement(sql);
	//			cont.runSQLUpdate();
	//			cont.closeConnection();
	//		}catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	//
	@Test
	public void testAddEmp(){
		//Can we double check this?
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql,name="",num="",email="",add="",city="",state="",postc="";
		ResultSet res;

		EmployeeController econt = new EmployeeController();

		try{
			econt.addEmployee("John Smith", "0555 555 555","myemail@gmail.com","123 Fake Street", 
					"Melbourne", "Victoria", "3000");
			sql="SELECT * FROM EMPLOYEE WHERE name='John Smith';";
			dbcont.prepareStatement(sql);
			res = dbcont.runSQLRes();

			name = res.getString("Name");
			num = res.getString("ContactNo");
			email = res.getString("Email");
			add = res.getString("StreetAddress");
			city = res.getString("City");
			state = res.getString("State");
			postc = res.getString("PostCode");

			assertEquals(name,"John Smith");
			assertEquals(num,"0555 555 555");
			assertEquals(email,"myemail@gmail.com");
			assertEquals(add,"123 Fake Street");
			assertEquals(city,"Melbourne");
			assertEquals(state,"Victoria");
			assertEquals(postc,"3000");

		}catch(Exception e){
			fail("SQLException Error.\nTest Failure.\nStack Trace: ");
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckEmp(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql,name="",num="",email="",add="",city="",state="",postc="";
		ResultSet res;

		try{	

			sql="SELECT * FROM EMPLOYEE WHERE name='Jacob Genericson';";

			dbcont.prepareStatement(sql);
			res = dbcont.runSQLRes();

			name = res.getString("Name");
			num = res.getString("ContactNo");
			email = res.getString("Email");
			add = res.getString("StreetAddress");
			city = res.getString("City");
			state = res.getString("State");
			postc = res.getString("PostCode");

			assertEquals(name,"Jacob Genericson");
			assertEquals(num,"0555 555 555");
			assertEquals(email,"myemail@gmail.com");
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
