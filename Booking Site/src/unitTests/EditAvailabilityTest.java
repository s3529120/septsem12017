package unitTests;

import Controller.DatabaseController;
import Model.DatabaseModel;

public class EditAvailabilityTest {

	
	public void setUpBeforeClass(){
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		String sql="";
		
		sql = "CREATE TABLE IF NOT EXIST Availibility;";
	}
	
	public void tearDownAfterClass(){
		
	}
}
