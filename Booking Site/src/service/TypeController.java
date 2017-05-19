package service;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.AppData;
import utils.DatabaseController;
import utils.DatabaseModel;

public class TypeController
{
   private TypeModel model;
   private TypeView view;
   private String empemail;
   private String empname;
   
   //Accessors
   /**Returns Employee email
    * @return Employee Email
    */
   public String getEmployee(){
      return empemail;
   }
   /**Returns Service Name
    * @return Service Name
    */
   public String getName()
   {
      return model.getName();
   }
   /**Returns Service duration
    * @return Service duration
    */
   public int getDuration()
   {
      return model.getDuration();
   }
   /**Returns TypeModel representation of service
    * @return TypeModel of service
    */
   public TypeModel getModel(){
      return this.model;
   }
   /**Returns Employee's name
    * @return Employee's name
    */
   public String getEmpname()
   {
      return empname;
   }
   //Mutators
   /**Sets employee's name
    * @param empname Name of employee
    */
   public void setEmpname(String empname)
   {
      this.empname = empname;
   }
   /**Sets Employee's email
    * @param email Email of employee
    */
   public void setEmp(String email){
      this.empemail=email;
   }
   /**Sets duration of service
    * @param duration Service duratrion
    */
   public void setDuration(int duration)
   {
      model.setDuration(duration);
   }
   /**Set name of service
    * @param name
    */
   public void setName(String name)
   {
      model.setName(name);
   }
   /**Sets accessible TypeModel
    * @param mod TypeModel to assign
    */
   public void setModel(TypeModel mod){
      this.model=mod;
   }
   /**Sets associated view
    * @param view View to associate
    */
   public void setView(TypeView view){
      this.view=view;
   }
   //Methods
   /**Calls view to update itself
    */
   public void updateView(){
      view.updateView();
   }
   
   //Methods
   /**Adds a new Service to the database
    * @param typename Name of service
    * @param duration Duration of service
    * @return Boolean success indicator
    */
   public Boolean addType(String typename,int duration){
	   DatabaseController dbcont = new DatabaseController(new DatabaseModel());
	   String sql="";
	   
	   //Open database
	   dbcont.createConnection();
	   //Prepare statement
	   sql="INSERT INTO Type(Type, Duration, Business) Values(?,?,?);";
	   dbcont.prepareStatement(sql);
	   try {
		dbcont.getState().setString(1, typename);
		dbcont.getState().setInt(2, duration);
		dbcont.getState().setString(3, AppData.CALLER.getUsername());
		//Run query
		dbcont.runSQLUpdate();
		//Return false on error
	} catch (SQLException e) {
		dbcont.closeConnection();
		return false;
	}
	   //Return true on success
	   dbcont.closeConnection();
	   return true;
   }
   
   /**Remove stored service
    * @param typename Name of service to remove
    * @return Boolean success indicator
    */
   public Boolean removeType(String typename){
	   DatabaseController dbcont = new DatabaseController(new DatabaseModel());
	   String sql="";
	   
	   //Open database connection
	   dbcont.createConnection();
	   //Prepare statement
	   sql="DELETE FROM Type WHERE Type=?;";
	   dbcont.prepareStatement(sql);
	   try {
		dbcont.getState().setString(1, typename);
		//Run statement
		dbcont.runSQLUpdate();
		//Return false on error
	} catch (SQLException e) {
		dbcont.closeConnection();
		return false;
	}
	   //Return true on success
	   dbcont.closeConnection();
	   return true;
   }
   
   /**Add specialization to employee
    * @param type Service to specialize in
    */
   public void addSpec(String type){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      
      //Open connection
      dbcont.createConnection();
      //Prepare statement
      sql="INSERT INTO SPEC(Type,EmployeeEmail) " +
            "VALUES(?,?);";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, type);
         dbcont.getState().setString(2, empemail);
         //Run statement
         dbcont.runSQLUpdate();
      }
      //Error catch
      catch (SQLException e)
      {
         e.printStackTrace();
      }
      //Close connection and update view
      dbcont.closeConnection();
      updateView();
      
   }
   
   /**Remove specialization
    * @param type Service to remove as specialization
    */
   public void removeSpec(String type){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      
      //Open connection
      dbcont.createConnection();
      //Prepare statement
      sql="DELETE FROM SPEC WHERE TYPE=?;";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, type);
         dbcont.runSQLUpdate();
      }
      //Error catch
      catch (SQLException e)
      {
         e.printStackTrace();
      }
      //Close connection and update view
      dbcont.closeConnection();
      updateView();
      
   }
   
   /**Retrieves a list of all Services that have been set
    * @return List of all services
    */
   public static List<TypeModel> getAllTypes(){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      ResultSet res;
      List<TypeModel> list=new ArrayList<TypeModel>();
      
      //Retrieve all types from database
      dbcont.createConnection();
      sql="SELECT * FROM Type;";
      dbcont.prepareStatement(sql);
      res=dbcont.runSQLRes();
      
      //Loop through results creating typemodels
      try
      {
         while(res.next()){
            list.add(new TypeModel(res.getString("Type"),
                                   res.getInt("Duration"),res.getString("Business")));
         }
      }
      //Catch errors
      catch (SQLException e)
      {
         e.printStackTrace();
      }
      //Close connection return list
      dbcont.closeConnection();
      return list;
      
   }
   
   /**Returns TypeModel of services by name given
    * @param list List of services to search
    * @param name Name of service to return
    * @return Matched service
    */
   public static TypeModel getModelByName(List<TypeModel> list,String name){
      //Convert to array for checking
      TypeModel[] checkArr = new TypeModel[list.size()];
      list.toArray(checkArr);
      //Loop through array looking for match
      for(int i=0;i<list.size();i++){
         if(checkArr[i].getName().compareToIgnoreCase(name)==0){
            //Return match if found
            return checkArr[i];
         }
      }
      //Return null if not present
      return null;
   }
   
   /**Get list of services employee specializes in
    * @return
    */
   public List<TypeModel> getSetTypes(){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      ResultSet res;
      List<TypeModel> list=new ArrayList<TypeModel>();
      
      //Retrieve all types from database
      dbcont.createConnection();
      sql="SELECT Spec.Type AS Type, Type.Duration as Duration FROM Spec INNER JOIN Type ON Spec.Type=Type.Type WHERE EmployeeEmail=?;";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, this.empemail);
      }
      //If error occurs return default "None" type
      catch (SQLException e1)
      {
         dbcont.closeConnection();
         list.add(new TypeModel("None",15,AppData.CALLER.getUsername()));
         e1.printStackTrace();
      }
      res=dbcont.runSQLRes();
      
      //Loop through results creating typemodels
      try
      {
         while(res.next()){
            list.add(new TypeModel(res.getString("Type"),res.getInt("Duration"),res.getString("Business")));
         }
      }
    //If error occurs return default "None" type
      catch (SQLException e)
      {
         dbcont.closeConnection();
         list.add(new TypeModel("None",15,AppData.CALLER.getUsername()));
         e.printStackTrace();
      }
      
      //Close connection return results
      dbcont.closeConnection();
      return list;
      
   }
   
   /**Compares all types and known types returning services employee does not specialize in
    * @return
    */
   public List<TypeModel> getUnknownTypes(){
      List<TypeModel> all = getAllTypes();
      List<TypeModel> known=getSetTypes();
      List<TypeModel> list=new ArrayList<TypeModel>();
      Boolean match;
      
      //Convert to arrays for comparison
      TypeModel[] allArr = new TypeModel[all.size()];
      TypeModel[] knownArr = new TypeModel[known.size()];
      allArr=all.toArray(allArr);
      knownArr=known.toArray(knownArr);
      //Loop through looking for matches
      for(int i=0;i<all.size();i++){
         match=false;
         for(int j=0;j<known.size();j++){
            if(allArr[i].getName().compareToIgnoreCase(knownArr[j].getName())==0){
               match=true;
            }
         }
         //Add to return list if no match
         if(!match){
            list.add(allArr[i]);
         }
      }
      //Return unknow types
      return list;
   }
}
