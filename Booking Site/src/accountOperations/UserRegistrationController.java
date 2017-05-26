package accountOperations;



import java.sql.SQLException;

import accounts.AccountController;
import accounts.AccountFactory;
import accounts.AccountModel;
import accounts.UserAccountModel;
import booking.BookingController;
import booking.BookingsView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import utils.AppData;
import utils.DataMatcher;
import utils.DatabaseController;
import utils.DatabaseModel;

public class UserRegistrationController {
	private UserRegistrationView view;
	private UserAccountModel model;

	/**
	 * Constructor, sets associated view and assigns self to view.
	 * @param view View to associate.
	 */
	public UserRegistrationController(UserRegistrationView view) {
		this.view = view;
		view.setController(this);
	}

	/**
	 * Calls associated view to update window.
	 */
	public void updateView() {
		view.updateView();
	}

	/**Check registration entries
	 * @param uname Entered username
	 * @param pname Entered name
	 * @param pword Entered password 
	 * @param pwordcon Entered comparison password
	 * @param address Entered address
	 * @param contactNo Entered contact number
	 * @param email Entered email
	 * @return True if valid entries, false if not.
	 */
	public Boolean checkValues(TextField uname, TextField pname, PasswordField pword, PasswordField pwordcon,
			TextField address, TextField contactNo, TextField email) {
		Boolean userExists;

		AccountController acon = new AccountController();
		userExists = acon.checkUsername(uname.getText());

		//Check if empty
		if (uname.getText().isEmpty() || pname.getText().isEmpty() || pword.getText().isEmpty()
				|| pwordcon.getText().isEmpty() || address.getText().isEmpty() || contactNo.getText().isEmpty()
				|| email.getText().isEmpty()) {
			return false;
		} 
		//Check if user exists
		else if (userExists == true) {
			return false;
		} 
		//Check if passwords match
		else if (!pword.getText().equals(pwordcon.getText())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validates the user registration entries, pass from the
	 * userRegistrationView.
	 * 
	 */
	public void validateEntries(TextField uname, HBox unamehbox, TextField pname, HBox pnamehbox, PasswordField pword,
			HBox pwordhbox, PasswordField pwordcon, HBox pwordhboxcon, TextField address, HBox addhbox,
			TextField contactNo, HBox numhbox, TextField email, HBox mailhbox, Text emptyerrortxt, HBox emptyerrorbox,
			Text passerrortxt, HBox passerrorbox, Text unameerrortxt, HBox unameerrorbox, Text pnameerrortxt,
			HBox pnameerrorbox, Text emailerrortxt, HBox emailerrorbox, Text phoneerrortxt, HBox phoneerrorbox,
			Text streeterrortxt, HBox streeterrorbox) {

		// Create bool vars to store state of entered data matching
		boolean hasEmpty = false, numerror = false, unameerror = false, pnameerror = false, passerror = false,
				passconerror = false, adderror = false, emailerror = false;

		// checking for empty as well as if the pattern is matched
		if (uname.getText().trim().equals("")) {
			uname.setId("incorrectForm");
			hasEmpty = true;
			unameerror = true;
		} else if (!DataMatcher.unameMatcher(uname.getText().trim())) {
			uname.setId("incorrectForm");
			unameerror = true;
		} else {
			unameerror = false;
			uname.setId("form");
		}

		if (pname.getText().trim().equals("")) {
			pname.setId("incorrectForm");
			hasEmpty = true;
			pnameerror = true;
		} else if (!DataMatcher.nameMatcher(pname.getText().trim())) {
			pname.setId("incorrectForm");
			pnameerror = true;
		} else {
			pnameerror = false;
			pname.setId("form");
		}

		if (pword.getText().trim().equals("")) {
			pword.setId("incorrectForm");
			hasEmpty = true;
			passerror = true;
			passconerror = true;
		} else if (!DataMatcher.passMatcher(pword.getText().trim())) {
			pword.setId("incorrectForm");
			passerror = true;
			passconerror = true;
		} else {
			passerror = false;
			pword.setId("form");
		}

		if (pwordcon.getText().trim().equals("")) {
			pwordcon.setId("incorrectForm");
			hasEmpty = true;
			passconerror = true;
			passerror = true;
		} else if (pword.getText().trim().compareTo(pwordcon.getText().trim()) != 0) {
			passerror = true;
			passconerror = true;
			pwordcon.setId("incorrectForm");
			pword.setId("incorrectForm");
		} else if (!DataMatcher.passMatcher(pwordcon.getText().trim())) {
			pwordcon.setId("incorrectForm");
			pword.setId("incorrectForm");
			passerror = true;
			passconerror = true;
		} else {
			passconerror = false;
			pword.setId("form");
			pwordcon.setId("form");
		}
		if (contactNo.getText().trim().equals("")) {
			contactNo.setId("incorrectForm");
			hasEmpty = true;
			numerror = true;
		} else if (!DataMatcher.phoneMatcher(contactNo.getText().trim())) {
			contactNo.setId("incorrectForm");
			numerror = true;
		} else {
			numerror = false;
			contactNo.setId("form");
		}

		if (address.getText().trim().equals("")) {
			address.setId("incorrectForm");
			hasEmpty = true;
			adderror = true;
		} else if (!DataMatcher.addMatcher(address.getText().trim())) {
			address.setId("incorrectForm");
			adderror = true;
		} else {
			adderror = false;
			address.setId("form");
		}

		if (email.getText().trim().equals("")) {
			email.setId("incorrectForm");
			hasEmpty = true;
			emailerror = true;
		} else if (!DataMatcher.emailMatcher(email.getText().trim())) {
			email.setId("incorrectForm");
			emailerror = true;
		} else {
			emailerror = false;
			email.setId("form");
		}

		// checking if the password fields are what cause the reject and if it
		// is add the "pass error text"
		if (!pword.getText().equals(pwordcon.getText())) {
			if (!passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().add(passerrortxt);
			}
		} else {
			if (passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().remove(passerrortxt);
			}
		}

		if (passerror) {
			if (!passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().add(passerrortxt);
			}
		} else {
			if (passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().remove(passerrortxt);
			}
		}

		// Check if the confirmation password matches the pattern
		if (passconerror) {
			if (!passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().add(passerrortxt);
			}
		} else {
			if (passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().remove(passerrortxt);
			}
		}

		// checking if any of the fields were empty and if they were add the
		// "empty error text"
		if (hasEmpty) {
			if (!emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().add(emptyerrortxt);
			}
		} else {
			if (emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().remove(emptyerrortxt);
			}
		}

		// check if number is wrong
		if (numerror) {
			if (!phoneerrorbox.getChildren().contains(phoneerrortxt)) {
				phoneerrorbox.getChildren().add(phoneerrortxt);
			}
		} else {
			if (phoneerrorbox.getChildren().contains(phoneerrortxt)) {
				phoneerrorbox.getChildren().remove(phoneerrortxt);
			}
		}

		// check if street address is wrong
		if (adderror) {
			if (!streeterrorbox.getChildren().contains(streeterrortxt)) {
				streeterrorbox.getChildren().add(streeterrortxt);
			}
		} else {
			if (streeterrorbox.getChildren().contains(streeterrortxt)) {
				streeterrorbox.getChildren().remove(streeterrortxt);
			}
		}

		// check if mail is wrong
		if (emailerror) {
			if (!emailerrorbox.getChildren().contains(emailerrortxt)) {
				emailerrorbox.getChildren().add(emailerrortxt);
			}
		} else {
			if (emailerrorbox.getChildren().contains(emailerrortxt)) {
				emailerrorbox.getChildren().remove(emailerrortxt);
			}
		}

		// Check if the the name is invalid
		if (pnameerror) {
			if (!pnameerrorbox.getChildren().contains(pnameerrortxt)) {
				pnameerrorbox.getChildren().add(pnameerrortxt);
			}
		} else {
			if (pnameerrorbox.getChildren().contains(pnameerrortxt)) {
				pnameerrorbox.getChildren().remove(pnameerrortxt);
			}
		}

		// checking if the account already exists and if it does adding the
		// "taken error text"
		AccountController acon = new AccountController();
		boolean accExists = acon.checkUsername(uname.getText());
		if (accExists) {
			if (!unameerrorbox.getChildren().contains(unameerrortxt)) {
				unameerrorbox.getChildren().add(unameerrortxt);
			}
		} else {
			if (unameerrorbox.getChildren().contains(unameerrortxt)) {
				unameerrorbox.getChildren().remove(unameerrortxt);
			}
		}
	}

	/**Registers user, storing information in database
	 * @param uname Username
	 * @param pname Person's Name
	 * @param pword Password
	 * @param address Address
	 * @param contactNo Phone number
	 * @param email Email address
	 */
	public void register(String uname, String pname, String pword, String address, String contactNo, String email) {
		String sql;
		model = new UserAccountModel(uname, pname, address, contactNo, email);
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);

		//Open connection
		dbcont.createConnection();
		//Prepare statement
		sql = "INSERT INTO Accounts(Username,Password,Name,Type,ContactNo,Email,Address) " + "Values(?,?,?,?,?,?,?);";

		dbcont.prepareStatement(sql);
		try {
			dbcont.getState().setString(1, uname);
			dbcont.getState().setString(2, pword);
			dbcont.getState().setString(3, pname);
			dbcont.getState().setString(4, "User");
			dbcont.getState().setString(5, contactNo);
			dbcont.getState().setString(6, email);
			dbcont.getState().setString(7, address);
			//Run sql statement to insert new user
			dbcont.runSQLUpdate();
		} catch (SQLException e) {
		   //Abort on error
			dbcont.closeConnection();
			e.printStackTrace();
			return;
		}
		//Close connection to database
		   dbcont.closeConnection();
		   
		   //Log new user in
			AccountModel acc = AccountFactory.createAccountModel("User",uname);
			AppData.CALLER = acc;
			BookingController bcont = new BookingController();
			bcont.setView(new BookingsView(view.stage));
			bcont.getView().setController(bcont);
			bcont.updateView();
	}
}
