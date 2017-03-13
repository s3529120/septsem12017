package Controller;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.DatabaseModel;

public class DatabaseController {
	private DatabaseModel model;
	
	public DatabaseController(DatabaseModel model){
		this.model=model;
	}
	
	public PreparedStatement prepareStatement(String sql){
		try {
			return DriverManager.getConnection(
					model.getdburl())
					.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean runSQL(PreparedStatement state){
		try{
			state.execute();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean runSQLUpdate(PreparedStatement state){
		try{
			state.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet runSQLRes(PreparedStatement state){
		ResultSet res=null;
		try{
			res=state.executeQuery();
			return res;
		}catch(SQLException e){
			e.printStackTrace();
			return res;
		}
	}
}
