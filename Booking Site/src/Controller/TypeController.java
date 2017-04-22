package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.DatabaseModel;
import Model.TypeModel;
import View.TypeView;

public class TypeController
{
   private TypeModel model;
   private TypeView view;
   private String empemail;
   private String empname;
   
   //Accessors
   public String getEmployee(){
      return empemail;
   }
   public String getName()
   {
      return model.getName();
   }
   public int getDuration()
   {
      return model.getDuration();
   }
   public TypeModel getModel(){
      return this.model;
   }
   public String getEmpname()
   {
      return empname;
   }
   public void setEmpname(String empname)
   {
      this.empname = empname;
   }
   public void setEmp(String email){
      this.empemail=email;
   }
   //Mutators
   public void setDuration(int duration)
   {
      model.setDuration(duration);
   }
   public void setName(String name)
   {
      model.getName();
   }
   public void setModel(TypeModel mod){
      this.model=mod;
   }
   public void setView(TypeView view){
      this.view=view;
   }
   //Methods
   public void updateView(){
      view.updateView();
   }
   
   //Methods
   public void removeSpec(String type){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      
      dbcont.createConnection();
      sql="INSERT INTO SPEC(Type,EmployeeEmail) " +
            "VALUES(?,?);";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, type);
         dbcont.getState().setString(2, empemail);
      }
      catch (SQLException e)
      {
      }
      dbcont.runSQLUpdate();
      dbcont.closeConnection();
      updateView();
      
   }
   
   public void addSpec(String type){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      
      dbcont.createConnection();
      sql="DELETE FROM SPEC WHERE TYPE=?;";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, type);
      }
      catch (SQLException e)
      {
      }
      dbcont.runSQLUpdate();
      dbcont.closeConnection();
      updateView();
      
   }
   
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
            list.add(new TypeModel(res.getString("Type"),res.getInt("Duration")));
         }
      }
      catch (SQLException e)
      {
      }
      dbcont.closeConnection();
      return list;
      
   }
   
   public List<TypeModel> getSetTypes(){
      DatabaseController dbcont = new DatabaseController(new DatabaseModel());
      String sql="";
      ResultSet res;
      List<TypeModel> list=new ArrayList<TypeModel>();
      
      //Retrieve all types from database
      dbcont.createConnection();
      sql="SELECT Type FROM Spec WHERE EmployeeEmail=?;";
      dbcont.prepareStatement(sql);
      try
      {
         dbcont.getState().setString(1, this.empemail);
      }
      catch (SQLException e1)
      {
         dbcont.closeConnection();
         list.add(new TypeModel("None",15));
         return list;
      }
      res=dbcont.runSQLRes();
      
      //Loop through results creating typemodels
      try
      {
         while(res.next()){
            list.add(new TypeModel(res.getString("Type"),res.getInt("Duration")));
         }
      }
      catch (SQLException e)
      {
      }
      dbcont.closeConnection();
      return list;
      
   }
   
   public List<TypeModel> getUnknownTypes(){
      List<TypeModel> all = getAllTypes();
      List<TypeModel> known=getSetTypes();
      known.forEach(x->{if(all.contains(x)){all.remove(x);}});
      return all;
   }
}
