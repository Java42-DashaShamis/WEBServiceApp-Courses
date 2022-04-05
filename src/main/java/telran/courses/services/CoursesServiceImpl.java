package telran.courses.services;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import telran.courses.dto.Course;
import telran.courses.exceptions.BadRequestException;

@Service
public class CoursesServiceImpl implements CoursesService {
	
	HashMap <Integer, Course> mapCourses;
	public CoursesServiceImpl() {
		mapCourses = new HashMap<Integer, Course>();
	}
	
	static Logger LOG = LoggerFactory.getLogger(CoursesServiceImpl.class);

	@Override
	public void addCourse(Course course) {
		if(mapCourses.containsKey(course.id)) {
			throw new BadRequestException("Course with such ID is already in the list");
		}
		mapCourses.putIfAbsent(course.id, course);
	}

	@Override
	public List<Course> getCourses() {
		if(mapCourses.isEmpty()) {
			LOG.info("There are no courses");
		}
		return mapCourses.values().stream().toList();
	}


}
