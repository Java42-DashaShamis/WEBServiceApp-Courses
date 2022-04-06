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
		/* V.R.
		 *  If id already exists, then it is possible to generate
		 *  not existing id, to put into object course and to write
		 *  the object to map.
		 */
		if(mapCourses.containsKey(course.id)) {
			throw new BadRequestException("Course with such ID is already in the list");
		}
		/* V.R. It is known that id is absent.
		 *  Why putIfAbsent? May be simply put? 
		 */
		mapCourses.putIfAbsent(course.id, course);
	}

	@Override
	public List<Course> getCourses() {
		if(mapCourses.isEmpty()) {
			/* V.R. It is the good place to throw 
			 * exception of type Resource not found
			 */
			LOG.info("There are no courses");
		}
		return mapCourses.values().stream().toList();
	}


}
