package Model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class DayModel {
	//Attributes
	DayOfWeek day;
	LocalTime startTime;
	LocalTime finishTime;
	Duration duration;
	
	//Constructor
	public DayModel(DayOfWeek day, LocalTime start, LocalTime finish,
			Duration duration){
		this.day=day;
		this.startTime=start;
		this.finishTime=finish;
	}
	
	//Accessors
	public DayOfWeek getDay(){
		return day;
	}
	public LocalTime getStartTime(){
		return startTime;
	}
	public LocalTime getFinishTime(){
		return finishTime;
	}
	public Duration getDuration(){
		return duration;
	}
	//Mutators
	public Boolean setDay(DayOfWeek day){
		this.day=day;
		return true;
	}
	public Boolean setStartTime(LocalTime start){
		this.startTime=start;
		return true;
	}
	public Boolean setFinishTime(LocalTime finish){
		this.finishTime=finish;
		return true;
	}
	public Boolean setDuration(Duration duration){
		this.duration=duration;
		return true;
	}
	//Methods

}
