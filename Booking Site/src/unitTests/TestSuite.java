package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


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
