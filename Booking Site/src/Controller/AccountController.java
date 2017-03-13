package Controller;

import java.sql.PreparedStatement;
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
		PreparedStatement state;
		sql="SELECT * "
				+ "FROM Accounts "
				+ "WHERE Username='"+name+"';";

		state=dbCont.prepareStatement(sql);
		
		res=dbCont.runSQLRes(state);
		
		if(res==null){
			System.out.print("null");
			return false;
		}else{
			return true;
		}
		
	}
	
	public Boolean comparePassword(String name, String pword){
		ResultSet res;
		String sql="";
		DatabaseModel dbMod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbMod);
		PreparedStatement state;
		
		sql="SELECT Password "
				+ "FROM Accounts "
				+ "WHERE Username='"+name+"';";
		state=dbCont.prepareStatement(sql);
		
		res=dbCont.runSQLRes(state);
		
		try {
			if(res.getString("Password").compareTo(pword)==0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	public String checkAccountType(String name){
		ResultSet res;
		String sql="";
		DatabaseModel dbMod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbMod);
		PreparedStatement state;
		
		sql="SELECT Type "
				+ "FROM Accounts "
				+ "WHERE UserName='"+name+"';";
		
		state=dbCont.prepareStatement(sql);
		
		res=dbCont.runSQLRes(state);
		
		try {
			return res.getString("Type");
		} catch (SQLException e) {
			return "Failed to determine";
		}
	}
	
	public AccountModel createAccountModel(String name, String type){
		String sql="";
		ResultSet res;
		PreparedStatement state;
		DatabaseModel dbmod = new DatabaseModel();
		DatabaseController dbCont = new DatabaseController(dbmod);
		if(type.compareToIgnoreCase("Business")==0){
			String contactno;
			String busname;
			String address;
			
			DatabaseModel dbMod = new DatabaseModel();
			DatabaseController cont = new DatabaseController(dbMod);
			
			sql="SELECT ContactNo, Name, Address "
					+ "FROM Accounts "
					+ "WHERE Username='"+name+"';";
			
			state=dbCont.prepareStatement(sql);
		
			
			res=cont.runSQLRes(state);
			
			try{
			contactno=res.getString("ContactNo");
			address=res.getString("Address");
			busname=res.getString("Name");
			}catch(SQLException e){
				return null;
			}
			
			BusinessAccountModel acc = new BusinessAccountModel(name,
					busname,contactno,address);
			return acc;
		}else if(type.compareToIgnoreCase("User")==0){
			String personname;
			
			DatabaseModel dbMod = new DatabaseModel();
			DatabaseController cont = new DatabaseController(dbMod);
			
			sql="SELECT ContactNo, Name, Address "
					+ "FROM Accounts "
					+ "WHERE Username='"+name+"';";
			
			state=dbCont.prepareStatement(sql);
			
			res=cont.runSQLRes(state);
			
			try{
			personname=res.getString("Name");
			}catch(SQLException e){
				return null;
			}
			
			UserAccountModel acc = new UserAccountModel(name,personname);
			return acc;
		}else{
			return null;
		}
	}
}
