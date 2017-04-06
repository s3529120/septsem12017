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
	
	//Creates a connection to database
	public Boolean createConnection(){
	   try
      {
         model.setConnection(DriverManager.getConnection(model.getdburl()));
         return true;
      }
      catch (SQLException e)
      {
         return false;
      }
	}
	
	//Closes connection to database
	public Boolean closeConnection(){
	   if(model.getConnection()!=null){
	      try
         {
            model.getConnection().close();
            return true;
         }
         catch (SQLException e)
         {
            return false;
         }
	   }else{
	      return false;
	   }
	}
	
	//Prepares an sql statement to be run
	public Boolean prepareStatement(String sql){
		try {
			model.setState(model.getConnection()
					.prepareStatement(sql));
			return true;
		} catch (SQLException e) {
         e.printStackTrace();
			return false;
		}
	}
	
	//Returns prepared statement from model
	public PreparedStatement getState(){
	   return model.getState();
	}
	
	//Runs prepared statement updating database
	public Boolean runSQLUpdate(){
		try{
		   PreparedStatement state=model.getState();
			state.executeUpdate();
			return true;
		}catch(SQLException e){
			return false;
		}
	}
	
	//Runs prepared statement returning result set from database
	public ResultSet runSQLRes(){
		ResultSet res;
		try{
			res=model.getState().executeQuery();
			return res;
		}catch(SQLException e){
			return null;
		}
	}

}
