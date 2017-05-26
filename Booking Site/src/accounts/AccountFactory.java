package accounts;

import admin.AdminController;

public class AccountFactory
{
   /**Account factory creates account model based on input type designation and username
    * @param atype Type of accounf model to create
    * @param name Username of desired account to retrieve
    * @return Model representation of desired account
    */
   public static AccountModel createAccountModel(String atype,String name){
      //Switch determining which model to create
      switch(atype){
      //Create user model
      case "User":
         return UserAccountController.createAccountModel(name);
      //Create business model
      case "Business":
         return BusinessAccountController.createAccountModel(name);
         //Create admin model
      case "Admin":
         return AdminController.createAccountModel(name);
      //Invalid input returns null
      default:
         return null;
      
      }
   }
}
