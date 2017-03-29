package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.DatabaseModel;
import Model.UserAccountModel;
import View.UserAccountMenuView;
import View.UserRegistrationView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
