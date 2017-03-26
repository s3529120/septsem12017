package Controller;

import Model.AccountModel;
import Model.BusinessAccountModel;
import Model.UserAccountModel;
import View.BusinessAccountMenuView;
import View.LoginView;
import View.UserAccountMenuView;

public class LoginController {
	private LoginView view;
	
	public LoginController(LoginView view){
		this.view=view;
		view.setController(this);
	}
	
	public void updateView(){
		view.updateView();
	}
	
	public Boolean isNotEmpty(String username, String pword){
	   if(username.compareTo("")==0 || pword.compareTo("")==0){
	      return false;
	   }else{
	      return true;
	   }
	}
	
	public boolean login(String username, String pword){
		AccountController cont = new AccountController();
		AccountModel acc;
		
		if(cont.checkUsername(username)){
			if(cont.comparePassword(username, pword)){
				acc=cont.createAccountModel(username, 
						cont.checkAccountType(username));
				if(acc instanceof BusinessAccountModel){
					BusinessAccountMenuView newview = new BusinessAccountMenuView(
							view.stage);
					BusinessAccountMenuController newcont = new BusinessAccountMenuController(
							(BusinessAccountModel) acc,newview);
					newcont.updateView();
				}if(acc instanceof UserAccountModel){
					UserAccountMenuView newview = new UserAccountMenuView(
							view.stage);
					UserAccountMenuController newcont = new UserAccountMenuController(
							(UserAccountModel) acc,newview);
					newcont.updateView();
				}
			}
		}
		return false;
	}
}
