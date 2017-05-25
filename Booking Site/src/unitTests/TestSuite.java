package unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	AdminTest.class,
	AddEmployeeTest.class,
	BookingsTest.class,
	EditAvailabilityTest.class,
	LoginTest.class,
	RegistrationTest.class,
	RosterTest.class,
	BookingsTest.class,
	ViewBookingsTest.class
})

public class TestSuite {		
}
