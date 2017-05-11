package accounts;

import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DatabaseController;
import utils.DatabaseModel;

public class BusinessAccountController extends AccountController
{

   public BusinessAccountController()
   {
      super();
   }

   public static BusinessAccountModel createAccountModel(String name){
         String sql="";
         ResultSet res;
         DatabaseModel dbmod = new DatabaseModel();
         DatabaseController dbCont = new DatabaseController(dbmod);

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
               e.printStackTrace();
               dbCont.closeConnection();
               return null;
            }

            //Create and return model
            BusinessAccountModel acc = new BusinessAccountModel(name,
                  busname,contactno,address,email);
            dbCont.closeConnection();
            return acc;
   }
}
