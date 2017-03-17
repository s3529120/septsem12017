package Test;

import static org.junit.Assert.*;
import Controller.UserRegistrationController;
import View.UserRegistrationView;
import javafx.stage.Stage;
import Model.UserAccountModel;

import org.junit.Test;

public class UserRegistrationControllerTest {
	
	Stage stage = new Stage();
	UserRegistrationView view = new UserRegistrationView(stage);
	UserRegistrationController cont = new UserRegistrationController(view);
	
	@Test
	public void registerInvalid() {
		int count = UserAccountModel.getNumOfInstances();
		cont.register("", "", "", "", "", "");
		int new_count = UserAccountModel.getNumOfInstances();
		assertEquals(count, new_count);
	}
	
	@Test
	public void registerValid() {
		int count = UserAccountModel.getNumOfInstances();
		cont.register("bus002", "foobar", "foobar", "123 Foobar St", "0455555555", "foo@example.com");
		int new_count = UserAccountModel.getNumOfInstances();
		assertNotEquals(count, new_count);
	}

}
