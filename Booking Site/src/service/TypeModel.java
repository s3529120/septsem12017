package service;

public class TypeModel
{
   private String name;
   private int duration;
   
   public TypeModel(String name,int duration){
      this.name=name;
      this.duration=duration;
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
}
