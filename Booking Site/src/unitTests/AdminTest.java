package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import admin.AdminController;
import utils.*;

public class AdminTest {
	
	/*
	 * Add a business via method and check for existence with sql statement
	 * */
	@Test
	public void addBusiness() throws SQLException{
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dcont = new DatabaseController(dbmod);
		AdminController admcont = new AdminController();
		
		dcont.createConnection();
		String sql;
		ResultSet res;
		String Busname="bus002",Password="abc123",Name="John's Garage",
				ContactNo="0123456789",Address="123 Fake St",
				Email="bus002@gmail.com";
		
		admcont.addBusiness(Busname, Password, Name, ContactNo, Address, Email);
		sql = "SELECT * FROM ACCOUNTS WHERE NAME = 'bus002';";
		
		dcont.prepareStatement(sql);
		res = dcont.runSQLRes();
		try {
			assertTrue(res.first());
			assertEquals(res.getString(0), "bus002");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		res.close();
		dcont.closeConnection();
	}
	/*
	 * Remove a business via method and check for existence with sql statement
	 * */
	@Test
	public void delBusiness() throws SQLException{
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dcont = new DatabaseController(dbmod);
		AdminController admcont = new AdminController();
		
		dcont.createConnection();
		String sql;
		ResultSet res;
		String Busname="bus002";
		
		admcont.delBusiness(Busname);
		sql = "SELECT * FROM ACCOUNTS WHERE NAME = 'bus002';";
		
		dcont.prepareStatement(sql);
		res = dcont.runSQLRes();
		try {
			assertTrue(res.getString(0).isEmpty());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res.close();
		dcont.closeConnection();
	}
}
