package accounts;

import admin.AdminController;

public class AccountFactory
{
   public static AccountModel createAccountModel(String atype,String name){
      
      switch(atype){
      case "User":
         return UserAccountController.createAccountModel(name);
      
      case "Business":
         return BusinessAccountController.createAccountModel(name);
      
      default:
         return null;
      
      }
   }
}
