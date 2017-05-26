package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColourController
{
   /**Ássigns account default colour to given
    * @param uname Username of account to set
    * @param col Colour to set to
    * @return Boolean success indicator
    */
	public static Boolean setAccountColour(String uname,Colour col){
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		String sql="";

		//Open connection
		dbcont.createConnection();
		//Delete old entry
		sql="DELETE FROM Colour WHERE Username=?;";
			dbcont.prepareStatement(sql);
			try {
				dbcont.getState().setString(1, uname);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			dbcont.runSQLUpdate();
			//Add new colour entry
		sql="INSERT INTO Colour(Colour, Username) Values(?,?);";
		try
		{
			dbcont.prepareStatement(sql);
			dbcont.getState().setString(1, col.toString());
			dbcont.getState().setString(2, uname);
			dbcont.runSQLUpdate();
		}
		//Set default blue on failure
		catch (Exception e)
		{
			e.printStackTrace();
			dbcont.closeConnection();
			AppData.colour=Colour.BLUE;
			return false;
		}
		//Sets active colour to reflect stored
		dbcont.closeConnection();
		getAccountColour(uname);
		return true;
	}

	/**Retrieves accounts set colour
	 * @param uname Username of account
	 * @return
	 */
	public static Boolean getAccountColour(String uname){
		DatabaseController dbcont = new DatabaseController(new DatabaseModel());
		String sql="";
		ResultSet res;

		//Open connection
		dbcont.createConnection();
		//Request colour
		sql="SELECT * FROM Colour WHERE Username=?;";
		try
		{
			dbcont.prepareStatement(sql);
			dbcont.getState().setString(1, uname);
			res=dbcont.runSQLRes();
			res.next();
			//Set colour
			AppData.colour=colFromString(res.getString("Colour"));
		}
		//Set default if fuilure occurs
		catch (Exception e)
		{
			//e.printStackTrace();
			dbcont.closeConnection();
			AppData.colour=Colour.BLUE;
			return false;
		}
		dbcont.closeConnection();
		return true;
	}

	/**Converts string to colour enum
	 * 
	 * @param col Colour string
	 * @return
	 */
	public static Colour colFromString(String col){
		switch(col){
		case "RED":
			return Colour.RED;
		case "WHITE":
			return Colour.WHITE;
		case "BLACK":
			return Colour.BLACK;
		case "YELLOW":
			return Colour.YELLOW;
		default:
			return Colour.BLUE;
		}
	}
}
