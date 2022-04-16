package telran.courses.service;
import static telran.courses.api.ApiConstants.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import telran.courses.dto.Course;
import telran.courses.exceptions.ResourceNotFoundException;

@Service
public class CoursesServiceImpl implements CoursesService{
	
	private static final long serialVersionUID = 1L;

	private static final int MILLIS_IN_MINUTES = 5000;

	@Value("${app.file.name}")
	private transient String fileName; 
	
	@Value("${app.interval.minutes: 1}")
	private transient int interval;
	
	//private transient SavingServiceThread savingThread;
	
	static Logger LOG = LoggerFactory.getLogger(CoursesService.class);
		
	private Map<Integer, Course> courses = new HashMap<>();
	
	@Override
	public  Course addCourse(Course course) {
	    course.id = generateId();
	    Course res = add(course);
	   
	    return res;
	}

	private Course add(Course course) {
		courses.put(course.id, course);
		return course;
	}

	private Integer generateId() {
	    ThreadLocalRandom random = ThreadLocalRandom.current();
	    int randomId;

	    do {
	        randomId = random.nextInt(MIN_ID, MAX_ID);
	    } while (exists(randomId));
	    return randomId;
	}

	private boolean exists(int id) {
		return courses.containsKey(id);
	}

	@Override
	public List<Course> getAllCourses() {
	    return new ArrayList<>(courses.values());
	}

	@Override
	public Course removeCourse(int id) {
		Course course = courses.remove(id);
		if(course == null) {
			throw new ResourceNotFoundException(String.format("course with id %d not found", id));
		}
		return course;
	}

	@Override
	public Course updateCourse(int id, Course course) {
		Course courseUpdated = courses.replace(id,course);
		if(courseUpdated == null) {
			throw new ResourceNotFoundException(String.format("course with id %d not found", id));
		}
		return courseUpdated;
	}
	
	
	
	private void restore() {
		File inputFile = new File(fileName);
		if (inputFile.exists()) {
			try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(inputFile))) {
				CoursesServiceImpl coursesFromFile =  (CoursesServiceImpl) input.readObject();
				this.courses = coursesFromFile.courses;
				LOG.debug("service has been restored from file {}", fileName);
			} catch (FileNotFoundException e) {
				LOG.warn("service has not been restored - no file {} found", fileName);;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			} 
		}
		
	}
	
	private void save() {
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
			output.writeObject(this);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	@PostConstruct
	void restoreInvocation() {
		LOG.debug("interval: {}", interval);
		restore();
		Thread thread = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(interval*MILLIS_IN_MINUTES);
					save();
					LOG.debug("Saving");
				} catch (InterruptedException e) {
					e.printStackTrace();
					LOG.debug("Saving is interrapted");
				}
				LOG.debug("Courses data saved into file {}", fileName);
			}
			
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	@PreDestroy
	void saveInvocation() {
		save();
		LOG.debug("Courses data saved into file {}", fileName);
		
	}
	

}
