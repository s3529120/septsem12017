
import java.sql.PreparedStatement;

import Controller.DatabaseController;
import Model.DatabaseModel;

public class Seed {
	public static void initialize(){
		String sql="";
		DatabaseModel dataMod = new DatabaseModel();
		DatabaseController dataCont = new DatabaseController(dataMod);
		
		PreparedStatement state;
		
		sql="DROP TABLE IF EXISTS Accounts;";
		try {
			
			dataCont.runSQLUpdate(dataCont.prepareStatement(sql));
			sql="CREATE TABLE Accounts("
					+ "Username TEXT NOT NULL, "
					+ "Password TEXT NOT NULL, "
					+ "Name TEXT NOT NULL, "
					+ "ContactNo TEXT NOT NULL, "
					+ "Type TEXT NOT NULL, "
					+ "Address TEXT NOT NULL, " 
					+"Email TEXT NOT NULL, "
					+ "PRIMARY KEY (Username));";
			state=dataCont.prepareStatement(sql);
			dataCont.runSQLUpdate(state);
			sql="INSERT INTO Accounts(Username, Password, Name, ContactNo, Type, Address, Email) "
					+ "VALUES(?,?,?,?,?,?,?);";
			state=dataCont.prepareStatement(sql);
			state.setString(1, "bus001");
			state.setString(2, "abc123");
			state.setString(3, "Johns Wares");
			state.setString(4, "1300655506");
			state.setString(5, "Business");
			state.setString(6, "1 SQL Avenue");
			state.setString(7, "jwares@gmail.com");
			dataCont.runSQLUpdate(state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
