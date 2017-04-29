package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import Model.AccountModel;
import Model.DatabaseModel;
import Model.UserAccountModel;
import View.BookingsView;
import View.UserAccountMenuView;
import View.UserRegistrationView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.AppData;
import utils.DataMatcher;

public class UserRegistrationController {
	private UserRegistrationView view;
	private UserAccountModel model;


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

		if(uname.getText().isEmpty() || pname.getText().isEmpty() || pword.getText().isEmpty() || pwordcon.getText().isEmpty() ||
				address.getText().isEmpty() || contactNo.getText().isEmpty() || email.getText().isEmpty()){
			return false;
		}else if(userExists==true){
			return false;
		}else if (!pword.getText().equals(pwordcon.getText())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**Validates the user registration entries, pass from the userRegistrationView.
	 * 
	*/
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
				adderror = false, emailerror = false;

		// checking for empty as well as if the pattern is matched
		if(uname.getText().trim().equals("")) {
			unamehbox.setId("incorrectForm");
			hasEmpty = true;
			unameerror = true;
		} else if(!DataMatcher.unameMatcher(uname.getText().trim())) { 
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
		} else if(!DataMatcher.nameMatcher(pname.getText().trim())) {
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
			passconerror = true;
		} else if(!DataMatcher.passMatcher(pword.getText().trim())) {
			pwordhbox.setId("incorrectForm");
			passerror = true;
			passconerror = true;
		} else {
			passerror = false;
			pwordhbox.setId("form");
		}
		
		if(pwordcon.getText().trim().equals("")) {
			pwordhboxcon.setId("incorrectForm");
			hasEmpty = true;
			passconerror = true;
			passerror = true;
		} else if(pword.getText().trim().compareTo(pwordcon.getText().trim()) != 0){
			passerror = true;
			passconerror = true;
			pwordhboxcon.setId("incorrectForm");
			pwordhbox.setId("incorrectForm");
		} else if(!DataMatcher.passMatcher(pwordcon.getText().trim())) {
			pwordhboxcon.setId("incorrectForm");
			pwordhbox.setId("incorrectForm");
			passerror = true;
			passconerror = true;
		} else {
			passconerror = false;
			pwordhboxcon.setId("form");
		}
		if(contactNo.getText().trim().equals("")){
			numhbox.setId("incorrectForm");
			hasEmpty = true;
			numerror = true;
		} else if(!DataMatcher.phoneMatcher(contactNo.getText().trim())) {
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
			adderror = true;
		} else if(!DataMatcher.addMatcher(address.getText().trim())) {
			addhbox.setId("incorrectForm");
			adderror = true;
		} else {
			adderror = false;
			addhbox.setId("form");
		}
		
		if(email.getText().trim().equals("")){
			mailhbox.setId("incorrectForm");
			hasEmpty = true;
			emailerror = true;
		} else if(!DataMatcher.emailMatcher(email.getText().trim())) {
			mailhbox.setId("incorrectForm");
			emailerror = true;
		} else {
			emailerror = false;
			mailhbox.setId("form");
		}
		
		//Basic debugging text
//		System.out.println("Are any empty:   "+hasEmpty);
//		System.out.println("Phone num error: "+numerror);
//		System.out.println("Uname error:     "+unameerror); 
//		System.out.println("Real name:       "+pnameerror);
//		System.out.println("pass error:      "+passerror);
//		System.out.println("pass con error:  "+passconerror);
//		System.out.println("street error:    "+streeterror);
//		System.out.println("mail error:      "+mailerror + "\n");

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
		if(adderror){
			if(!streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().add(streeterrortxt);
			}
		}else{
			if(streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().remove(streeterrortxt);
			}
		}

		//check if mail is wrong
		if(emailerror){
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

			AccountController acont=new AccountController();
			AccountModel acc=acont.createAccountModel(uname, "User");
			acc.setAddress(address);
			acc.setContactNo(contactNo);
			acc.setName(pname);
			dbcont.closeConnection();
			AppData.CALLER=acc;
			BookingController bcont = new BookingController();
            bcont.setView(new BookingsView(view.stage));
            bcont.getView().setController(bcont);
            bcont.updateView();
		}
	}
}
