package unitTests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.ResultSet;
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
			sql = "DROP TABLE IF EXISTS Availibility;";
			dbcont.prepareStatement(sql);
			dbcont.runSQLUpdate();

			sql="CREATE TABLE Availability("
					+ "Date TEXT NOT NULL, "
					+ "StartTime TEXT NOT NULL, "
					+ "FinishTime TEXT NOT NULL, "
					+ "Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Email,Date,StartTime));";
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
	public void testAddValidAvail(){
		AvailabilitiesController avcont = new AvailabilitiesController();
		LocalDate today = LocalDate.now();
		String email = "newemail@gmail.com",start="00:00",end="00:45";
		boolean validate;

		try{	
			avcont.addAvailability(email, today, start, end);
			/* This throws java.lang.NullPointerException as the errortexts and what not are null*/
			validate = avcont.validateEntries(
					email, null,
					today, null,
					"00:00", null,
					"00:45", null,
					null, null,
					null, null,
					null, null);

			assertEquals(avcont.getEmail(email), email);
			assertTrue(validate);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void testaAddInvalidAvail(){
		AvailabilitiesController avcont = new AvailabilitiesController();
		LocalDate today = LocalDate.now();
		String email = "newemail@gmail.com",start="13:00",end="12:45";
		boolean validate;

		try{
			/* This throws java.lang.NullPointerException as the errortexts and what not are null*/
			validate = avcont.validateEntries(
					email, null,
					today, null,
					start, null,
					end, null,
					null, null,
					null, null,
					null, null);
			
			assertFalse(validate);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddEmptyFields(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql="";
		ResultSet res;

		
		AvailabilitiesController avcont = new AvailabilitiesController();
		LocalDate date = LocalDate.now();
		String email = null,startstring="00:00",finishstring="12:45";
		try{
			avcont.addAvailability(email, date, startstring, finishstring);
			
			sql = "SELECT * FROM AVAILABILTY WHERE EMAIL='newemail@gmail.com';";
			dbcont.prepareStatement(sql);
			res = dbcont.runSQLRes();
			

			
			assertThat(res.getString(email), not(email));
			assertThat(res.getString(startstring), is(startstring));
			assertThat(res.getString(finishstring), is(finishstring));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}


