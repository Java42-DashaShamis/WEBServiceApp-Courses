package telran.courses.services;

import java.util.List;

import telran.courses.dto.Course;
import telran.courses.exceptions.BadRequestException;

public interface CoursesService {
	void addCourse(Course course);
	List<Course> getCourses();
	
}
