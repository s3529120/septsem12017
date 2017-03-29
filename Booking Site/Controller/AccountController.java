package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.AccountModel;
import Model.BusinessAccountModel;
import Model.DatabaseModel;
import Model.UserAccountModel;

public class AccountController {

	public Boolean checkUsername(String name){
			ResultSet res;
			String sql="";
			DatabaseModel dbMod = new DatabaseModel();
			DatabaseController dbCont = new DatabaseController(dbMod);
			int comp;
			sql="SELECT * "
					+ "FROM Accounts "
					+ "WHERE Username='"+name+"';";
			
			dbCont.createConnection();

			dbCont.prepareStatement(sql);

			res=dbCont.runSQLRes();
			
			try
         {
            comp=res.getString("Username").compareTo(name);
         }
         catch (SQLException e)
         {
            comp=-1;
         }
			
			if(comp!=0){
				dbCont.closeConnection();
				return false;
			}else{
			   dbCont.closeConnection();
				return true;
			}
			
			
	}


	public Boolean comparePassword(String name, String pword){
		ResultSet res;
		String sql="";
		DatabaseModel dbMod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbMod);

		dbCont.createConnection();
		sql="SELECT Password "
				+ "FROM Accounts "
				+ "WHERE Username='"+name+"';";
		dbCont.prepareStatement(sql);

		res=dbCont.runSQLRes();

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

	public String checkAccountType(String name){
		ResultSet res;
		String sql="",type;
		DatabaseModel dbMod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbMod);

		dbCont.createConnection();
		sql="SELECT Type "
				+ "FROM Accounts "
				+ "WHERE UserName='"+name+"';";

		dbCont.prepareStatement(sql);

		res=dbCont.runSQLRes();

		try {
         type=res.getString("Type");
		   dbCont.closeConnection();
			return type;
		} catch (SQLException e) {
		   dbCont.closeConnection();
			return "Failed to determine";
		}
	}

	public AccountModel createAccountModel(String name, String type){
		String sql="";
		ResultSet res;
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbmod);
		if(type.compareToIgnoreCase("Business")==0){
			String contactno;
			String busname;
			String address;
			String email;

			
			dbCont.createConnection();

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
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }

			res=dbCont.runSQLRes();

			try{
				contactno=res.getString("ContactNo");
				address=res.getString("Address");
				busname=res.getString("Name");
				email=res.getString("Email");
			}catch(SQLException e){
			   dbCont.closeConnection();
				return null;
			}

			BusinessAccountModel acc = new BusinessAccountModel(name,
					busname,contactno,address,email);
			dbCont.closeConnection();
			return acc;
		}else if(type.compareToIgnoreCase("User")==0){
			String contactno;
			String personname;
			String address;
			String email;

			dbCont.createConnection();
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
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }

			res=dbCont.runSQLRes();

			try{
				contactno=res.getString("ContactNo");
				address=res.getString("Address");
				personname=res.getString("Name");
				email=res.getString("Email");
			}catch(SQLException e){
			   dbCont.closeConnection();
				return null;
			}

			UserAccountModel acc = new UserAccountModel(name,personname,contactno,address,email);
			dbCont.closeConnection();
			return acc;
		}else{
		   dbCont.closeConnection();
			return null;
		}
	}
}
