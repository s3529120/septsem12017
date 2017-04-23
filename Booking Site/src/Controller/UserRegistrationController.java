package Controller;

import java.sql.ResultSet;
import java.util.regex.*;
import java.sql.SQLException;

import Model.DatabaseModel;
import Model.UserAccountModel;
import View.UserAccountMenuView;
import View.UserRegistrationView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class UserRegistrationController {
	private UserRegistrationView view;
	private UserAccountModel model;

	// For origin of this code see http://jregex.sourceforge.net/examples-email.html
	private static final String emailChars="[\\w.\\-]+"; //letters, dots, hyphens
	private static final String alpha="[a-zA-Z]+";
	private static final String digit="\\d+";
	private static final String space="\\s";
	private static final String hyphen="\\-";
	private static final String alphaNums="\\w+";
	private static final String dot="\\.";
	private static final String symbols="[\\W\\S]+"; //all non letter or number characters + non spaces

	// Create a new pattern to match email format: chars@(chars.)*chars
	// Create similar patterns for username, name, password, address etc.
	private static final Pattern emailPattern=
			Pattern.compile(emailChars + "@" + alphaNums + "(?:" + "\\." + alphaNums + ")++");
	private static final Pattern username = Pattern.compile(alpha + "\\d*"); // Just one or more letters and any numbers
	private static final Pattern fullname = Pattern.compile(alpha + "(?:" + space + alpha + ")*"); // Just letters, space and hyphen
	private static final Pattern password = Pattern.compile("(?:" + alphaNums + "||" + symbols + ")+"); // Should be letters + numbers + Symbols
	private static final Pattern addressPattern = Pattern.compile(digit + "(?:" + space + alpha + ")+");
	private static final Pattern phoneNo = Pattern.compile("\\d" + "(?:" + "\\d" + "||" + space + ")+");// atleast one digit or space


	/**Constructor, sets associated view and assigns self to view.
	 * @param view View to associate.
	 */
	public UserRegistrationController(UserRegistrationView view){
		this.view=view;
		view.setController(this);
	}

	/**Calls associated view to update window.
	 */
	public void updateView(){
		view.updateView();
	}

	public Boolean checkValues(TextField uname, TextField pname, PasswordField pword, PasswordField pwordcon,
			TextField address, TextField contactNo, TextField email){
		Boolean userExists;

		AccountController acon = new AccountController();
		userExists=acon.checkUsername(uname.getText());

		if(!(username.matcher(uname.getText().trim()).matches()) 
				|| !(fullname.matcher(pname.getText().trim()).matches()) 
				|| !(password.matcher(pword.getText().trim()).matches())
				|| !(password.matcher(pwordcon.getText().trim()).matches())
				|| !(addressPattern.matcher(address.getText().trim()).matches()) 
				|| !(phoneNo.matcher(contactNo.getText().trim()).matches())
				|| !(emailPattern.matcher(email.getText().trim()).matches())){
			System.out.println("Check Values returns false. Patterns don't match.");
			return false;
		}else if(userExists==true){
			System.out.println("Check Values returns false. User exists.");
			return false;
		}else if (!pword.getText().equals(pwordcon.getText())) {
			System.out.println("Check Values returns false. Passwords dont match.");
			return false;
		} else {
			System.out.println("Check Values returns true.");
			return true;
		}

	}

	public void validateEntries(
			TextField uname, HBox unamehbox,
			TextField pname, HBox pnamehbox,
			PasswordField pword, HBox pwordhbox,
			PasswordField pwordcon,HBox pwordhboxcon,
			TextField address, HBox addhbox,
			TextField contactNo, HBox numhbox,
			TextField email, HBox mailhbox,
			Text emptyerrortxt, HBox emptyerrorbox,
			Text passerrortxt, HBox passerrorbox,
			Text unameerrortxt, HBox unameerrorbox,
			Text pnameerrortxt, HBox pnameerrorbox,
			Text emailerrortxt, HBox emailerrorbox,
			Text phoneerrortxt, HBox phoneerrorbox,
			Text streeterrortxt, HBox streeterrorbox
			){

		// Create bool vars to store state of entered data matching
		boolean hasEmpty = false, numerror = false, unameerror = false, 
				pnameerror = false, passerror = false, passconerror = false, 
				streeterror = false, mailerror = false;

		// checking for empty as well as if the pattern is matched
		if(uname.getText().trim().equals("")) {
			unamehbox.setId("incorrectForm");
			hasEmpty = true;
			unameerror = true;
		} else if(!(username.matcher(uname.getText().trim()).matches())) { 
			unamehbox.setId("incorrectForm");
			unameerror = true;
		} else {
			unameerror = false;
			unamehbox.setId("form");
		}
		
		if(pname.getText().trim().equals("")) {
			pnamehbox.setId("incorrectForm");
			hasEmpty = true;
			pnameerror = true;
		} else if(!(fullname.matcher(pname.getText().trim()).matches())) {
			pnamehbox.setId("incorrectForm");
			pnameerror = true;
		} else {
			pnameerror = false;
			pnamehbox.setId("form");
		}
		if(pword.getText().trim().equals("")) {
			pwordhbox.setId("incorrectForm");
			hasEmpty = true;
			passerror = true;
		} else if(!(password.matcher(pword.getText().trim()).matches())) {
			pwordhbox.setId("incorrectForm");
			passerror = true;
		} else {
			passerror = false;
			pwordhbox.setId("form");
		}
		
		if(pwordcon.getText().trim().equals("")) {
			pwordhboxcon.setId("incorrectForm");
			hasEmpty = true;
			passconerror = true;
		} else if(!(password.matcher(pwordcon.getText().trim()).matches())) {
			pwordhboxcon.setId("incorrectForm");
			passconerror = true;
		} else {
			passconerror = false;
			pwordhboxcon.setId("form");
		}
		if(contactNo.getText().trim().equals("")){
			numhbox.setId("incorrectForm");
			hasEmpty = true;
			numerror = true;
		} else if(!(phoneNo.matcher(contactNo.getText().trim()).matches()) ) {
			numhbox.setId("incorrectForm");
			numerror = true;
			//Check for length of phone num
			//		} else if(contactNo.getText().replaceAll("\\s","").length() > 10){
			//			System.out.println(contactNo.getText().replaceAll("\\s","").length());
			//			System.out.println(contactNo.getText().replaceAll("\\s",""));
			//			numhbox.setId("incorrectForm");
			//			numerror = true;
		} else {
			numerror = false;
			numhbox.setId("form");
		}
		
		if(address.getText().trim().equals("")){
			addhbox.setId("incorrectForm");
			hasEmpty = true;
			streeterror = true;
		} else if(!(addressPattern.matcher(address.getText().trim()).matches())) {
			addhbox.setId("incorrectForm");
			streeterror = true;
		} else {
			streeterror = false;
			addhbox.setId("form");
		}
		
		if(email.getText().trim().equals("")){
			mailhbox.setId("incorrectForm");
			hasEmpty = true;
			mailerror = true;
		} else if(!(emailPattern.matcher(email.getText().trim()).matches())) {
			mailhbox.setId("incorrectForm");
			mailerror = true;
		} else {
			mailerror = false;
			mailhbox.setId("form");
		}
		
		System.out.println("Are any empty:   "+hasEmpty);
		System.out.println("Phone num error: "+numerror);
		System.out.println("Uname error:     "+unameerror); 
		System.out.println("Real name:       "+pnameerror);
		System.out.println("pass error:      "+passerror);
		System.out.println("pass con error:  "+passconerror);
		System.out.println("street error:    "+streeterror);
		System.out.println("mail error:      "+mailerror + "\n");

		//checking if the password fields are what cause the reject and if it is add the "pass error text"
		if (!pword.getText().equals(pwordcon.getText()) || passerror) {
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

		//check if number is wrong
		if(numerror){
			if(!phoneerrorbox.getChildren().contains(phoneerrortxt)){
				phoneerrorbox.getChildren().add(phoneerrortxt);
			}
		}else{
			if(phoneerrorbox.getChildren().contains(phoneerrortxt)){
				phoneerrorbox.getChildren().remove(phoneerrortxt);
			}
		}

		//check if street address is wrong
		if(streeterror){
			if(!streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().add(streeterrortxt);
			}
		}else{
			if(streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().remove(streeterrortxt);
			}
		}

		//check if mail is wrong
		if(mailerror){
			if(!emailerrorbox.getChildren().contains(emailerrortxt)){
				emailerrorbox.getChildren().add(emailerrortxt);
			}
		}else{
			if(emailerrorbox.getChildren().contains(emailerrortxt)){
				emailerrorbox.getChildren().remove(emailerrortxt);
			}
		}

		// Check if the the name is invalid
		if(pnameerror){
			if(!pnameerrorbox.getChildren().contains(pnameerrortxt)){
				pnameerrorbox.getChildren().add(pnameerrortxt);
			}
		}else{
			if(pnameerrorbox.getChildren().contains(pnameerrortxt)){
				pnameerrorbox.getChildren().remove(pnameerrortxt);
			}
		}

		//checking if the account already exists and if it does adding the "taken error text"
		AccountController acon = new AccountController();
		boolean accExists=acon.checkUsername(uname.getText());
		if (accExists) {
			if (!unameerrorbox.getChildren().contains(unameerrortxt)) {
				unameerrorbox.getChildren().add(unameerrortxt);
			}
		} else {
			if (unameerrorbox.getChildren().contains(unameerrortxt)){
				unameerrorbox.getChildren().remove(unameerrortxt);
			}
		}
	}

	public void register(String uname,String pname,String pword,String address,String contactNo,String email) {
		String sql;
		model = new UserAccountModel(uname,pname,address,contactNo,email);
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);

		dbcont.createConnection();
		sql="INSERT INTO Accounts(Username,Password,Name,Type,ContactNo,Email,Address) "
				+ "Values(?,?,?,?,?,?,?);";

		dbcont.prepareStatement(sql);
		try{
			dbcont.getState().setString(1, uname);
			dbcont.getState().setString(2, pword);
			dbcont.getState().setString(3, pname);
			dbcont.getState().setString(4, "User");
			dbcont.getState().setString(5, contactNo);
			dbcont.getState().setString(6, email);
			dbcont.getState().setString(7, address);
		}catch(SQLException e){
			dbcont.closeConnection();
			e.printStackTrace();
		}
		if(dbcont.runSQLUpdate()){

			UserAccountMenuView newview = new UserAccountMenuView(view.stage);
			UserAccountMenuController newcont = new UserAccountMenuController(model,newview);
			newcont.updateView();
			dbcont.closeConnection();
		}
	}
}
