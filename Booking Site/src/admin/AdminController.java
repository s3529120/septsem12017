package admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import accounts.AccountController;
import accounts.BusinessAccountModel;
import accounts.UserAccountModel;
import admin.AdminView;
import utils.database.DatabaseController;
import utils.database.DatabaseModel;
/**
 * Update the view of the admin account landing page, and handle business functions
 */
public class AdminController {

	private AdminView view;

	// Returns associated view
	public AdminView getView(){
		return this.view;
	}

	// Sets associated view
	public Boolean setView(AdminView view) {
		this.view=view;
		return true;
	}

	// Calls associated view to update window
	public void updateView(){
		view.updateView();
	}

	// TODO: Add methods for adding and removing a business.

	/**
	 * Add a new business into the database
	 * @param busname New business name to login with
	 * @param Password New Bus Pass
	 * @param Name New human readable name
	 * @param ContactNo new phone
	 * @param Type Always 'Business'
	 * @param Address of business
	 * @param Email of business
	 * @return True if business added.
	 */
	public boolean addBusiness(String Busname,String Password,String Name,String ContactNo,
			String Type,String Address, String Email){
		// TODO Add business method
		return false;
	}

	/**
	 * Delete a business and all associated employees & roster if any exist
	 * @param Busname of the business to be removed from database
	 * @return True if successful
	 */
	public boolean delBusiness(String Busname){
		// TODO Delete business method
		return false;
	}

	/**
	 * Method to retrieve the list of current business objects in the database
	 * @return List of businesses if succesful
	 */
	public List<BusinessAccountModel> getBusinesses() {
		//	TODO Get business method

		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		String sql="";
		ResultSet res;
		List<BusinessAccountModel> bussls=new ArrayList<BusinessAccountModel>();
		BusinessAccountModel bus;

		//Retrieve users from database
		dbcont.createConnection();
		sql="SELECT Username, Name FROM Accounts WHERE Type='Business';";
		dbcont.prepareStatement(sql);
		res=dbcont.runSQLRes();
		try
		{
			while(res.next()){
				bus=(BusinessAccountModel) AccountController.createAccountModel(res.getString("Username"),"Business");
				bus.setName(res.getString("Name"));
				if(bus!=null){
					bussls.add(bus);
				}
				bus=null;
			}
		}
		catch (SQLException e)
		{
		}

		dbcont.closeConnection();
		return bussls;
	}

	private BusinessAccountModel createBusinessModel(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}
}
