package Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.DatabaseModel;
import Model.UserAccountModel;
import View.UserAccountMenuView;
import View.UserRegistrationView;

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

	public void register(String uname,String pname,String pword) {
		String sql;
		PreparedStatement state;
		model = new UserAccountModel(uname,pname);
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		
		sql="INSERT INTO Accounts(Username,Password,Name,Type) "
				+ "Values(?,?,?,?);";
		
		state=dbcont.prepareStatement(sql);
		try{
			state.setString(1, uname);
			state.setString(2, pword);
			state.setString(3, pname);
			state.setString(4, "User");
		}catch(SQLException e){
			e.printStackTrace();
		}
		dbcont.runSQLUpdate(state);
		
		UserAccountMenuView newview = new UserAccountMenuView(view.stage);
		UserAccountMenuController newcont = new UserAccountMenuController(model,newview);
		newcont.updateView();
	}
}
