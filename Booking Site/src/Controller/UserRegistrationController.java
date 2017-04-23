package Controller;

import java.sql.ResultSet;
import jregex.*;
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
import jregex.Pattern;

public class UserRegistrationController {
	private UserRegistrationView view;
	private UserAccountModel model;
	
	// For origin of this code see http://jregex.sourceforge.net/examples-email.html
	private static final String emailChars="[\\w.\\-]+"; //letters, dots, hyphens
	private static final String alpha="\\w";
	private static final String digit="\\d";
	private static final String space="\\s";
	private static final String hyphen="\\-";
	private static final String alphaNums="\\w+";
	private static final String dot="\\.";
	private static final String symbols="[\\W\\S]"; //all non word characters + non spaces
	
	// Create a new pattern to match email format: chars@(chars.)*chars
	// Create similar patterns for username, name, password, address etc.
	private static final Pattern emailPattern=
			new Pattern(emailChars + "@" + "(?:"+ alphaNums + dot + ")*" + alphaNums);
	private static final Pattern username = new Pattern("(?:"+ alphaNums + ")*"); // Just letters+numbers
	private static final Pattern fullname = new Pattern("(?:"+ alpha + space + ")*"); // Just letters
	private static final Pattern password = new Pattern("(?:"+ alphaNums + symbols + ")*"); // Should be letters + numbers + Symbols
	private static final Pattern addressPattern = new Pattern("(?:" + digit + ")*" + "(?:" + digit + alpha + ")*");
	private static final Pattern phoneNo = new Pattern("(?:" + digit + space + ")*");// + space + parenthesis);
	
	
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
	      return false;
	   }else if(userExists==true){
	      return false;
	   }else if (!pword.getText().equals(pwordcon.getText())) {
		   return false;
	   } else {
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
			Text takenerrortxt, HBox takenerrorbox) {

		//checking for empty as well as if the pattern is matched
		boolean hasEmpty = false;
		if(uname.getText().trim().equals("") 
				|| !(username.matcher(uname.getText().trim()).matches())) {
			unamehbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			unamehbox.setId("form");
		}
		if(pname.getText().trim().equals("")
				|| !(fullname.matcher(pname.getText().trim()).matches())) {
			pnamehbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			pnamehbox.setId("form");
		}
		if(pword.getText().trim().equals("")
				|| !(password.matcher(pword.getText().trim()).matches())) {
			pwordhbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			pwordhbox.setId("form");
		}
		if(pwordcon.getText().trim().equals("")
				|| !(password.matcher(pwordcon.getText().trim()).matches())) {
			pwordhboxcon.setId("incorrectForm");
			hasEmpty = true;
		} else {
			pwordhboxcon.setId("form");
		}
		if(contactNo.getText().trim().equals("")
				|| !(phoneNo.matcher(contactNo.getText().trim()).matches())
				|| contactNo.getText().replaceAll("\\s","").length() > 10
				) {
			numhbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			numhbox.setId("form");
		}
		if(address.getText().trim().equals("")
				|| !(addressPattern.matcher(address.getText().trim()).matches())) {
			addhbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			addhbox.setId("form");
		}
		if(email.getText().trim().equals("")
				|| !(emailPattern.matcher(email.getText().trim()).matches())) {
			mailhbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			mailhbox.setId("form");
		}

		//checking if the password fields are what cause the reject and if it is add the "pass error text"
		if (!pword.getText().equals(pwordcon.getText())) {
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
		//checking if the account already exists and if it does adding the "taken error text"
		AccountController acon = new AccountController();
		boolean accExists=acon.checkUsername(uname.getText());
		if (accExists) {
			if (!takenerrorbox.getChildren().contains(takenerrortxt)) {
				takenerrorbox.getChildren().add(takenerrortxt);
			}
		} else {
			if (takenerrorbox.getChildren().contains(takenerrortxt)) {
				takenerrorbox.getChildren().remove(takenerrortxt);
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
