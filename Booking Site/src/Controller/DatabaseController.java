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
	
	public PreparedStatement getState(){
	   return model.getState();
	}
	
	public Boolean runSQL(){
		try{
		   PreparedStatement state=model.getState();
			state.execute();
			return true;
		}catch(SQLException e){
			return false;
		}
	}
	
	public Boolean runSQLUpdate(){
		try{
		   PreparedStatement state=model.getState();
			state.executeUpdate();
			return true;
		}catch(SQLException e){
			return false;
		}
	}
	
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
