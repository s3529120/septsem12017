package unitTests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import Controller.DatabaseController;
import Model.DatabaseModel;


@RunWith(Suite.class)

@Suite.SuiteClasses({
	   AddEmployeeTest.class,
	   LoginTest.class,
	   RegistrationTest.class,
	   RosterTest.class,
	   ViewBookingsTest.class
	})

public class TestSuite {
		
}
