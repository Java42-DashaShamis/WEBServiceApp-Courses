package telran.courses.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


public class Course {
	
	@Range(min = 1000000, max = 9999999)
	public Integer id = (int) (1000000 + Math.round(Math.random() * (9999999 - 1000000)));;
	@NotBlank(message = "Name of course is mandatory")
	@Pattern(message = "There is no such course",regexp = "Basic Programming|Java Core|Java Back-End|HTML, CSS, JS|React|QA Manual|QA Automation")
	public String course;
	@NotBlank(message = "Lecturer's name is mandatory")
	@Pattern(message = "There is no such lecturer",regexp = "Eduard|Elena|Grigory|Igor|Ivan|Yuri|Vladimir")
	public String lecturer;
	@NotNull(message = "Cost is mandatory")
	@Range(min = 5000, max = 18000)
	public Integer cost; 
	@NotNull(message = "Hours are mandatory")
	@Range(min = 80, max = 500)
	public Integer hours;
	@NotNull(message = "Date is mandatory")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy")
	public LocalDate openingDate;
	
	
	public Course(Integer id,String course,String lecturer,Integer cost,Integer hours,String openingDate) {
		super();
		this.id = id;
		this.course = course;
		this.lecturer = lecturer;
		this.cost = cost;
		this.hours = hours;
		this.openingDate = LocalDate.parse(openingDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")) ;
	}


	public Course() {
	}
}
