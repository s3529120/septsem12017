package Controller;

import Model.AccountModel;
import Model.BusinessAccountModel;
import Model.UserAccountModel;
import View.BusinessAccountMenuView;
import View.LoginView;
import View.UserAccountMenuView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

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
	
	public void validateEntries (
			TextField username, HBox usernameBox, 
			TextField pword, HBox pwordBox,
			Text emptyerrortxt, HBox emptyerrorbox) {
		//checking if empty
		boolean hasEmpty = false;
		if(username.getText().trim().equals("")) {
			usernameBox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			usernameBox.setId("form");
		}
		if(pword.getText().trim().equals("")) {
			pwordBox.setId("incorrectForm");
			hasEmpty = true;
		} else {
			pwordBox.setId("form");
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
