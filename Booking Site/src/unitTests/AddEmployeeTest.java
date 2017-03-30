package unitTests;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import Controller.DatabaseController;
import Controller.EmployeeController;
import Model.DatabaseModel;

public class AddEmployeeTest {

	@BeforeClass
	public static void setUpBeforeClass(){
		
		//Employee fields
		//String name,String contactno,String email,
		//String streetadd,String city,String state,String postcode
		
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);
		String sql;
		try{
			cont.createConnection();
			sql="DROP TABLE IF EXISTS Employee;";
			cont.prepareStatement(sql);
			cont.runSQLUpdate();

			sql="CREATE TABLE Employee("
					+ "Name TEXT NOT NULL, "
					+ "ContactNo TEXT NOT NULL, "
					+ "Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Email));";
			cont.prepareStatement(sql);
			cont.runSQLUpdate();

			sql="INSERT INTO Employee(Name,ContactNo,Email) " +
					"Values(?,?,?);";
			cont.prepareStatement(sql);
			cont.getState().setString(1, "Jacob Genericson");
			cont.getState().setString(2, "0412345678");
			cont.getState().setString(3, "jgsons@gmail.com");
			
			cont.runSQLUpdate();
			cont.closeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass(){}

	@Test
	public void testAddEmp(){


		try{

		}catch(Exception e){
			fail("SQLException Error.\nTest Failure.\nStack Trace: ");
			e.printStackTrace();
		}
	}




}
