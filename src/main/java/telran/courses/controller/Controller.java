package telran.courses.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import telran.courses.dto.Course;
import telran.courses.services.CoursesServiceImpl;

@RestController
@RequestMapping("/courses")
@Validated
public class Controller {
	static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	CoursesServiceImpl coursesService;
	// V.R. coursesService has to be CoursesService, not CoursesServiceImpl
	
	@PostMapping
	String addCourse(@Valid @RequestBody Course course) {
		// V.R. The method addCourse has to return Course, not String
		LOGGER.debug("Course data: ID - %d, name - %s, lecturer - %s, cost - %d, hours - %d, date of start - %s", course.id, course.course, course.lecturer, course.cost, course.hours, course.openingDate);
		coursesService.addCourse(course);
		return "Course is added";
	}
	
	@GetMapping("/list")
	List<Course> getCourses(){
		LOGGER.debug("Getting all courses");
		return coursesService.getCourses();
	}
	
}
