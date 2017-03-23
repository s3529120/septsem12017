package Controller;

import java.sql.PreparedStatement;
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

	public Boolean checkValues(TextField uname, TextField pname, TextField pword, TextField pwordcon, 
	                           TextField address, TextField contactNo, TextField email){
	   PreparedStatement state;
	   String sql;
	   DatabaseModel dbmod = new DatabaseModel();
	   DatabaseController dbcont = new DatabaseController(dbmod);
	   ResultSet res;
	   Boolean bool;
	   
	   sql="SELECT * FROM Accounts WHERE Username=?;";
	   state=dbcont.prepareStatement(sql);
	   try
      {
         state.setString(1, uname.getText());
      }
      catch (SQLException e)
      {
		 System.out.println("Returning false because an sql exception occured");
         return false;
      }
	   res=dbcont.runSQLRes(state);
	   try{
         bool=res.wasNull();
      }catch(SQLException e){
		   System.out.println("Returning false because an sql exception occured");
         return false;
      }
	   
	   
	   
	   if(uname.getText()=="" || pname.getText()=="" || pword.getText()=="" || pwordcon.getText()=="" || 
	         address.getText()=="" || contactNo.getText()=="" || email.getText()==""){
		   System.out.println("Returning false because a field was empty");
	      return false;
	   }else if(bool==true){
		   System.out.println("Returning false because the username already exists");
	      return false;
	   } else if (pword.getText().equals(pwordcon.getText()) == false) {
		   System.out.println("Returning false because passwords did not match");
		   return false;
	   }else{
	      return true;
	   }
	   
	}
	
	public void register(String uname,String pname,String pword,String address,String contactNo,String email) {
		String sql;
		PreparedStatement state;
		model = new UserAccountModel(uname,pname,address,contactNo,email);
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		
		sql="INSERT INTO Accounts(Username,Password,Name,Type,ContactNo,Email,Address) "
				+ "Values(?,?,?,?,?,?,?);";
		
		state=dbcont.prepareStatement(sql);
		try{
			state.setString(1, uname);
			state.setString(2, pword);
			state.setString(3, pname);
			state.setString(4, "User");
			state.setString(5, contactNo);
			state.setString(6, email);
			state.setString(7, address);
		}catch(SQLException e){
			e.printStackTrace();
		}
		dbcont.runSQLUpdate(state);
		
		UserAccountMenuView newview = new UserAccountMenuView(view.stage);
		UserAccountMenuController newcont = new UserAccountMenuController(model,newview);
		newcont.updateView();
	}
}
