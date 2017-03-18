package Test;

import static org.junit.Assert.*;
import Controller.LoginController;
import View.LoginView;
import javafx.stage.Stage;

import org.junit.Test;

public class LoginControllerTest {
	
	
	Stage stage1 = new Stage();
	LoginView view = new LoginView(stage1);
	LoginController cont = new LoginController(view);

	@Test
	public void isNotEmptyFalse() {
		assertFalse(cont.isNotEmpty("bus001", ""));
		assertFalse(cont.isNotEmpty("", "abc123"));
	}
	
	@Test
	public void isNotEmptyTrue() {
		assertTrue(cont.isNotEmpty("bus001", "abc123"));
	}
	
	@Test
	public void loginInvalidTest() {
		cont.login("1", "2");
	}
	
	@Test
	public void loginValidTest() {
		cont.login("bus001", "abc123");
	}

}
