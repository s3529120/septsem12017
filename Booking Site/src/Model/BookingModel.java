package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingModel {
	//Attributes
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime finishTime;
	private EmployeeModel employee;
	
	//Constructor
	public BookingModel(LocalDate date, LocalTime start, LocalTime finish){
		this.date=date;
		this.startTime=start;
		this.finishTime=finish;
	}
}
