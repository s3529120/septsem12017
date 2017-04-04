package unitTests;

import org.junit.Test;

import Controller.DatabaseController;
import Model.DatabaseModel;

public class EditAvailabilityTest {


	public void setUpBeforeClass(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql="";

		sql = "CREATE TABLE IF NOT EXISTS Availibility;";


	}

	public void tearDownAfterClass(){
		DatabaseModel mod = new DatabaseModel();
		DatabaseController cont = new DatabaseController(mod);
		String sql;
		try{
			cont.createConnection();
			sql="DROP TABLE IF EXISTS Employee; DROP TABLE IF EXISTS Availibility;";
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
	public void testAddAvail(){
		
	}
	
}


