package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Controller.AccountController;

public class AccountControllerTest {

	AccountController cont = new AccountController();

	@Test
	public void checkUserNameInvalid() {
		assertFalse(cont.checkUsername("I don't exist :("));
	}
	
	@Test
	public void checkUserNameValid() {
		assertTrue(cont.checkUsername("bus001"));
	}
	
	@Test
	public void comparePasswordInvalid() {
		assertFalse(cont.comparePassword("bus001", "nope"));
	}
	
	@Test
	public void comparePasswordValid() {
		assertTrue(cont.comparePassword("bus001", "abc123"));
	}
	
	@Test
	public void checkAccountTypeBusiness() {
		assertEquals(cont.checkAccountType("bus001"), "Business");
	}
	
	@Test
	public void checkAccountTypeCustomer() {
		assertEquals(cont.checkAccountType("cus001"), "Customer");
	}
	
	@Test
	public void checkAccountTypeFail() {
		assertEquals(cont.checkAccountType("nah"), "Failed to determine");
	}
	
	@Test
	public void createAccountModelNotNull() {
		assertNotNull(cont.createAccountModel("bus001", "Business"));
	}
	
	@Test
	public void createAccountModelNull() {
		assertNull(cont.createAccountModel("ayyy", "lmao"));
	}

}
