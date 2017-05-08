package unitTests;

import static org.junit.Assert.assertTrue;
import java.sql.ResultSet;
import org.junit.Test;

import admin.AdminController;
import utils.database.*;

public class AdminTest {
	
	/*
	 * Add a business via method and check for existence with sql statement
	 * */
	@Test
	public void addBusiness(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dcont = new DatabaseController(dbmod);
		AdminController admcont = new AdminController();
		
		dcont.createConnection();
		String sql;
		ResultSet res;
		
		admcont.addBusiness(busname, Password, Name, ContactNo, Type, Address, Email);
		
		dcont.prepareStatement(sql);
		res = dcont.runSQLRes();
		assertTrue();
		
		dcont.closeConnection();
	}
	/*
	 * Remove a business via method and check for existence with sql statement
	 * */
	@Test
	public void delBusiness(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dcont = new DatabaseController(dbmod);
		dcont.createConnection();
		
		assertTrue();
		
		dcont.closeConnection();
	}
}
