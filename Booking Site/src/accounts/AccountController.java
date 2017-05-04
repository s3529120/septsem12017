package accounts;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.database.DatabaseController;
import utils.database.DatabaseModel;

public class AccountController {

   /**Returns a list of all users in database
    * @return List of all customers as UserAccountModels
    */
   public List<UserAccountModel> getAllCustomers(){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      ResultSet res;
      List<UserAccountModel> accs=new ArrayList<UserAccountModel>();
      UserAccountModel cus;
      
      //Retrieve users from database
      dbcont.createConnection();
      sql="SELECT Username, Name FROM Accounts WHERE Type='User';";
      dbcont.prepareStatement(sql);
      res=dbcont.runSQLRes();
      try
      {
         while(res.next()){
            cus=(UserAccountModel) createAccountModel(res.getString("Username"),"User");
            cus.setName(res.getString("Name"));
            if(cus!=null){
               accs.add(cus);
            }
            cus=null;
         }
      }
      catch (SQLException e)
      {
      }
      
      dbcont.closeConnection();
      return accs;
   }
   
   /** Checks if user exists in database returns true if yes false if no.
    * @param name Username to check if currently exists in database.
    * @return True if already exists in database, false if not.
    */
	public Boolean checkUsername(String name){

		ResultSet res;
		String sql="";
		DatabaseModel dbMod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbMod);
		int comp;

		//Prepare and run sql
		sql="SELECT * "
				+ "FROM Accounts "
				+ "WHERE Username='"+name+"';";

		dbCont.createConnection();
		dbCont.prepareStatement(sql);
		res=dbCont.runSQLRes();

		//Compare usernames
		try
		{
			comp=res.getString("Username").compareTo(name);
		}
		catch (SQLException e)
		{
			comp=-1;
		}

		//Return true if match false if not
		if(comp!=0){
			dbCont.closeConnection();
			return false;
		}else{
			dbCont.closeConnection();
			return true;
		}



	}


	/** Compares given password to that stored for that user returns true on match.
	 * @param name Username whose password to check.
	 * @param pword Entered password to check against stored value
	 * @return True if match, false if not.
	 */
	public Boolean comparePassword(String name, String pword){
		ResultSet res;
		String sql="";
		DatabaseModel dbMod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbMod);

		//Open database connection
		dbCont.createConnection();

		//Prepare and run sql
		sql="SELECT Password "
				+ "FROM Accounts "
				+ "WHERE Username='"+name+"';";
		dbCont.prepareStatement(sql);
		res=dbCont.runSQLRes();

		//Compare passwords
		try {
			if(res.getString("Password").compareTo(pword)==0){
				dbCont.closeConnection();
				return true;
			}else{
				dbCont.closeConnection();
				return false;
			}
		} catch (SQLException e) {
			dbCont.closeConnection();
			return false;
		}

	}

	/** Checks the type of account returns either "Business" or "User".
	 * @param name Username to check account type of.
	 * @return string "Business" or "User" for customers.
	 */
	public String checkAccountType(String name){
		ResultSet res;
		String sql="",type;
		DatabaseModel dbMod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbMod);

		//Open connection
		dbCont.createConnection();

		//Prepare and run sql
		sql="SELECT Type "
				+ "FROM Accounts "
				+ "WHERE UserName='"+name+"';";
		dbCont.prepareStatement(sql);
		res=dbCont.runSQLRes();

		//Retrieve account type
		try {
			type=res.getString("Type");
			dbCont.closeConnection();
			return type;
		} catch (SQLException e) {
			dbCont.closeConnection();
			return "Failed to determine";
		}
	}

	/**Creates model from account data.
	 * @param name Username to assign account.
	 * @param type Account type, "Business" or "User".
	 * @return AccountModel representing account just vreated.
	 */
	public AccountModel createAccountModel(String name, String type){
		String sql="";
		ResultSet res;
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbmod);

		//Check if Business account
		if(type.compareToIgnoreCase("Business")==0){
			String contactno;
			String busname;
			String address;
			String email;

			//Open database connection

			dbCont.createConnection();

			//Prepare and run sql
			sql="SELECT ContactNo, Name, Address, Email "
					+ "FROM Accounts "
					+ "WHERE Username=?;";

			dbCont.prepareStatement(sql);
			try
			{
				dbCont.getState().setString(1, name);
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}

			res=dbCont.runSQLRes();

			//Set model values to database results
			try{
				contactno=res.getString("ContactNo");
				address=res.getString("Address");
				busname=res.getString("Name");
				email=res.getString("Email");
			}catch(SQLException e){
				dbCont.closeConnection();
				return null;
			}

			//Create and return model
			BusinessAccountModel acc = new BusinessAccountModel(name,
					busname,contactno,address,email);
			dbCont.closeConnection();
			return acc;

			//Check if User account
		}else if(type.compareToIgnoreCase("User")==0){
			String contactno;
			String personname;
			String address;
			String email;

			//Open database connection
			dbCont.createConnection();

			//Prepare and run sql
			sql="SELECT ContactNo, Name, Address, Email "
					+ "FROM Accounts "
					+ "WHERE Username=?;";
			dbCont.prepareStatement(sql);
			try

			{
				dbCont.getState().setString(1, name);
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}

			res=dbCont.runSQLRes();

			//Set model values to database results
			try{
				contactno=res.getString("ContactNo");
				address=res.getString("Address");
				personname=res.getString("Name");
				email=res.getString("Email");
			}catch(SQLException e){
				dbCont.closeConnection();
				return null;
			}

			//Create and return account model
			UserAccountModel acc = new UserAccountModel(name,personname,contactno,address,email);
			dbCont.closeConnection();
			return acc;
		}else{
			dbCont.closeConnection();
			return null;
		}
	}
}
