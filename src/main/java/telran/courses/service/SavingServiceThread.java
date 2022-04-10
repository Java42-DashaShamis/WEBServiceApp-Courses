package telran.courses.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SavingServiceThread extends Thread {
	
	private int interval;
	private CoursesService coursesService;
	private boolean isToBeStopped = false;
	private static Logger LOG = LoggerFactory.getLogger(SavingServiceThread.class);
	

	public SavingServiceThread(int interval, CoursesService coursesService) {
		this.interval = interval;
		this.coursesService = coursesService;
	}
	
	public void setStop() {
		isToBeStopped = true;
	}


	@Override
	public void run() {
		while(!isToBeStopped) {
			try {
				sleep(interval);
				LOG.debug("Saving");
				coursesService.save();
			} catch (InterruptedException e) {
				e.printStackTrace();
				LOG.debug("Saving is interrapted");
				break;
			}
			
		}
		LOG.debug("Saving is stopped");
	}
}
