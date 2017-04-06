package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingModel {
	//Attributes
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime finishTime;
	private String employee;
	private String user;
	private String type;
	
	//Constructor
	public BookingModel(LocalDate date, LocalTime start, LocalTime finish){
		this.date=date;
		this.startTime=start;
		this.finishTime=finish;
	}
	
	//Mutators
	public Boolean setDate(LocalDate date){
	   this.date=date;
	   return true;
	}
	public Boolean setStartTime(LocalTime time){
	   this.startTime=time;
	   return true;
   }
	public Boolean setFinishTime(LocalTime time){
	   this.finishTime=time;
	   return true;
	}
	public Boolean setEmployee(String email){
	   this.employee=email;
	   return true;
	}
	public Boolean setUser(String uname){
	   this.user=uname;
	   return true;
	}
	public Boolean setType(String type){
	   this.type=type;
	   return true;
	}
	
	//Accessors
	public LocalDate getDate(){
      return date;
   }
   public LocalTime getStartTime(){
      return startTime;
   }
   public LocalTime getFinishTime(){
      return finishTime;
   }
   public String getEmployee(){
      return employee;
   }
   public String getUser(){
      return user;
   }
   public String getType(){
      return type;
   }
}
