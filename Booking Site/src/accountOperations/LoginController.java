package accountOperations;



import accounts.AccountController;
import accounts.AccountFactory;
import accounts.AccountModel;
import accounts.AdminAccountModel;
import accounts.BusinessAccountModel;
import accounts.UserAccountModel;
import admin.*;
import booking.BookingController;
import booking.BookingsView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import utils.AppData;
import utils.ColourController;
import javafx.scene.control.TextField;


public class LoginController {
	private LoginView view;

	
	
	/**Constructor, sets associated view and assigns self to said view.
	 * @param view View to associate with.
	 */
	public LoginController(LoginView view){
		this.view=view;
		view.setController(this);
	}

	/** Calls view to update window.
	 */
	public void updateView(){
		view.updateView();
	}

	/**Checks if given fields are empty.
	 * @param username Value from username field.
	 * @param pword Value from password field.
	 * @return
	 */
	public Boolean isNotEmpty(String username, String pword){
		if(username.compareTo("")==0 || pword.compareTo("")==0){
			return false;
		}else{
			return true;
		}
	}

	/**Validate entries to login page
	 * @param username Username entered
	 * @param usernameBox Username box
	 * @param pword Password entered
	 * @param pwordBox Password box
	 * @param emptyerrortxt Error message for invalid input
	 * @param emptyerrorbox Error message box
	 */
	public void validateEntries (
			TextField username, HBox usernameBox, 
			TextField pword, HBox pwordBox,
			Text emptyerrortxt, HBox emptyerrorbox) {
		//checking if empty
		boolean hasEmpty = false;
		if(username.getText().trim().equals("")) {
			username.setId("incorrectForm");
			hasEmpty = true;
		} else {
			username.setId("form");
		}
		if(pword.getText().trim().equals("")) {
			pword.setId("incorrectForm");
			hasEmpty = true;
		} else {
			pword.setId("form");
		}
		//checking if any of the fields were empty and if they were add the "empty error text"
		if (hasEmpty) {
			if (!emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().add(emptyerrortxt);
			}
		} else {
			if (emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().remove(emptyerrortxt);
			}
		}
	}

	/**Logs program user in if credentials are valid.
	 * @param username Input username.
	 * @param pword Input password.
	 * @return True if login was successful, false upon invalid credentials.
	 */
	public boolean login(String username, String pword){
		AccountController cont = new AccountController();
		AccountModel acc;

		//Check if exists.
		if(cont.checkUsername(username)){
			//Check for password match
			if(cont.comparePassword(username, pword)){
				//Create account model.
				acc=AccountFactory.createAccountModel(
				                      cont.checkAccountType(username), username);
				//If admin account
				if(!(acc instanceof AdminAccountModel)){
				   //Init objects
					BookingController bcont = new BookingController();
		            bcont.setView(new BookingsView(view.stage));
		            bcont.getView().setController(bcont);
		            //Set caller
		            AppData.CALLER=acc;
		            ColourController.getAccountColour(acc.getUsername());
		            //Show stage
		            bcont.updateView(acc.getName());
				}
				//If user or business
				else{
				   //Init objects
					AdminController admcont = new AdminController();
					admcont.setView(new AdminView(view.stage));
					admcont.getView().setController(admcont);
					//Set caller
					AppData.CALLER=acc;
					//Show stage
					admcont.updateView();
				}
			}
		}
		return false;
	}
}
