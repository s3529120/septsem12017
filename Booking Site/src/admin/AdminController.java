package admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import accounts.AccountController;
import accounts.AccountFactory;
import accounts.AccountModel;
import accounts.AdminAccountModel;
import accounts.BusinessAccountModel;
import accounts.UserAccountModel;
import admin.AdminView;
import utils.DatabaseController;
import utils.DatabaseModel;
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
	
	/**Creates model from account data.
    * @param name Username to assign account.
    * @param type Account type, "Business" or "User" or "Admin".
    * @return AccountModel representing account just created.
    */
   public static AccountModel createAccountModel(String name){
      String sql="";
      ResultSet res;
      DatabaseModel dbmod = new DatabaseModel();
      DatabaseController dbCont = new DatabaseController(dbmod);
         String adminname;
         String email;
   
         //Open database connection
         dbCont.createConnection();

         //Prepare and run sql
         sql="SELECT Name, Email "
               + "FROM Accounts "
               + "WHERE Username=?;";
         dbCont.prepareStatement(sql);
         try
         {
            dbCont.getState().setString(1, name);
         }
         catch (SQLException e2)
         {
            e2.printStackTrace();
         }

         res=dbCont.runSQLRes();
         
         try{
            adminname=res.getString("Name");
            email=res.getString("Email");
         }catch(SQLException e){
            e.printStackTrace();
            dbCont.closeConnection();
            return null;
         }
         
         // Create and return the acc model, close connection
         AdminAccountModel acc = new AdminAccountModel(adminname, email);
         dbCont.closeConnection();
         return acc;
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
		AccountController acont = new AccountController();
		String sql="";
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbcont = new DatabaseController(dbmod);
		// If acc exists return false,
		try{
			dbcont.createConnection();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		if(!acont.checkUsername(Busname)) {
			sql = "INSERT INTO Accounts(Username,Password,Name,Type,ContactNo,Email,Address) " 
				+ "Values(?,?,?,?,?,?,?,?);";

			dbcont.prepareStatement(sql);
			try {
				dbcont.getState().setString(1, Busname);
				dbcont.getState().setString(2, Password);
				dbcont.getState().setString(3, Name);
				dbcont.getState().setString(4, "Business");
				dbcont.getState().setString(5, ContactNo);
				dbcont.getState().setString(6, Email);
				dbcont.getState().setString(7, Address);
				dbcont.runSQLUpdate();
			} catch(Exception e1) {
				e1.printStackTrace();
				return false;
			}
			return true;
		} else {
			// If acc doesn't exist, add new account and return true, else return false
			return false;
		}
	}

	/**
	 * Delete a business and all associated employees & roster if any exist
	 * @param Busname of the business to be removed from database
	 * @return True if successful
	 */
	public static boolean delBusiness(String Busname){
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		String sql="";
		
		//Create a new conn and set the sql query to busname.
		dbcont.createConnection();
		sql="DELETE * FROM accounts WHERE username=?;";
		dbcont.prepareStatement(sql);
		try
		{
			// Run sql and close conn
			dbcont.getState().setString(1, Busname);
			dbcont.runSQLUpdate();
			dbcont.closeConnection();
			return true;
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		// Run the sql query and remove bus and close conn
		dbcont.closeConnection();
		return false;
	}

	/** Method to retrieve the list of current business objects in the database
	 *  @return List of businesses if successful
	 */
	public List<BusinessAccountModel> getBusinesses() {
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
				bus=(BusinessAccountModel) AccountFactory.createAccountModel("Business",res.getString("Username"));
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
}
