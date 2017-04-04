package unitTests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import Controller.AvailabilitiesController;
import Controller.DatabaseController;
import Model.DatabaseModel;

public class EditAvailabilityTest {


	public void setUpBeforeClass(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql="";
		
		try{
			sql = "CREATE TABLE IF NOT EXISTS Availibility;";
			dbcont.prepareStatement(sql);
			dbcont.runSQLUpdate();
			
			
			
		}catch(Exception e){
			dbcont.closeConnection();
			e.printStackTrace();
		}finally{
			dbcont.closeConnection();
		}
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
		AvailabilitiesController avcont = new AvailabilitiesController();
		LocalDate today = LocalDate.now();
		try{	
			avcont.addAvailability("newemail@gmail.com", today, "00:00", "00:30");
			
			assertEquals(,);
			assertEquals(,);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}


