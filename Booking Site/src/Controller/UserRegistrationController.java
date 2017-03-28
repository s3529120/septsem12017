package Controller;

import java.sql.ResultSet;
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
	
	public UserRegistrationController(UserRegistrationView view){
		this.view=view;
		view.setController(this);
	}
	
	public void updateView(){
		view.updateView();
	}

	public Boolean checkValues(TextField uname, TextField pname, PasswordField pword, PasswordField pwordcon,
	                           TextField address, TextField contactNo, TextField email){
	   Boolean bool;
	   
	   AccountController acon = new AccountController();

	   bool=acon.checkUsername(uname.getText());
	   
	   if(uname.getText().isEmpty() || pname.getText().isEmpty() || pword.getText().isEmpty() || pwordcon.getText().isEmpty() ||
	         address.getText().isEmpty() || contactNo.getText().isEmpty() || email.getText().isEmpty()){
	      return false;
	   }else if(bool==true){
	      return false;
	   }else if (!pword.getText().equals(pwordcon.getText())) {
		   return false;
	   } else {
	      return true;
	   }
	   
	}
	
	public void validateEntires(
			TextField uname, HBox unamehbox,
			TextField pname, HBox pnamehbox,
			PasswordField pword, HBox pwordhbox,
			PasswordField pwordcon,HBox pwordhboxcon,
			TextField address, HBox addhbox,
			TextField contactNo, HBox numhbox,
			TextField email, HBox mailhbox,
			Text emptyerrortxt, HBox emptyerrorbox,
			Text passerrortxt, HBox passerrorbox) {

		//checking for empty
		boolean hasEmpty = false;
		if(uname.getText().trim().equals("")) {
			unamehbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			unamehbox.setId("form");
		}
		if(pname.getText().trim().equals("")) {
			pnamehbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			pnamehbox.setId("form");
		}
		if(pword.getText().trim().equals("")) {
			pwordhbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			pwordhbox.setId("form");
		}
		if(pwordcon.getText().trim().equals("")) {
			pwordhboxcon.setId("incorrectForm");
			hasEmpty = true;
		} else {
			pwordhboxcon.setId("form");
		}
		if(contactNo.getText().trim().equals("")) {
			numhbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			numhbox.setId("form");
		}
		if(address.getText().trim().equals("")) {
			addhbox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			addhbox.setId("form");
		}
		if(email.getText().trim().equals("")) {
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
