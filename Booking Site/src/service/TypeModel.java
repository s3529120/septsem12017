package service;

public class TypeModel
{
   private String name;
   private int duration;
   private String business;
   
   public TypeModel(String name,int duration,String business){
      this.name=name;
      this.duration=duration;
      this.setBusiness(business);
   }
   
   public String getName()
   {
      return name;
   }
   public int getDuration()
   {
      return duration;
   }
   public void setDuration(int duration)
   {
      this.duration = duration;
   }
   public void setName(String name)
   {
      this.name = name;
   }

   public String getBusiness()
   {
      return business;
   }

   public void setBusiness(String business)
   {
      this.business = business;
   }
}
