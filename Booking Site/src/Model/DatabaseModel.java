package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseModel {

	private static final String dburl="jdbc:sqlite:data.db";
	private Connection con;
	private PreparedStatement state;
	
	public String getdburl(){
		return dburl;
	}
	public PreparedStatement getState(){
      return state;
   }
	public Connection getConnection(){
      return con;
   }
	
	public Boolean setState(PreparedStatement state){
	   this.state=state;
      return true;
   }
   public Boolean setConnection(Connection con){
      this.con=con;
      return true;
   }
	
}
