package utils;

import java.sql.ResultSet;

public class ColourController
{
   public static Boolean setAccountColour(String uname,Colour col){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      
      dbcont.createConnection();
      sql="UPDATE Colour SET Colour=? WHERE Username=?;";
      try
      {
         dbcont.prepareStatement(sql);
         dbcont.getState().setString(1, col.toString());
         dbcont.getState().setString(2, uname);
         dbcont.runSQLUpdate();
      }
      catch (Exception e)
      {
         dbcont.closeConnection();
         AppData.colour=Colour.BLUE;
         return false;
      }
      dbcont.closeConnection();
      getAccountColour(uname);
      return true;
   }
   
   public static Boolean getAccountColour(String uname){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      ResultSet res;
      
      dbcont.createConnection();
      sql="SELECT * FROM Colour WHERE Username=?;";
      try
      {
         dbcont.prepareStatement(sql);
         dbcont.getState().setString(1, uname);
         res=dbcont.runSQLRes();
         AppData.colour=colFromString(res.getString("Colour"));
      }
      catch (Exception e)
      {
         dbcont.closeConnection();
         AppData.colour=Colour.BLUE;
         return false;
      }
      dbcont.closeConnection();
      return true;
   }
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
