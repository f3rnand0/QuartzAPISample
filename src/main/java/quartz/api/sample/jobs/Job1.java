package quartz.api.sample.jobs;

import java.text.SimpleDateFormat;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/***
 * Wrapper class for Job class registered in the scheduler.
 *
 * @author jgonzalez, schancay
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class Job1 implements Job {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			System.out.printf("QUARTZ_API_TEST - Trigger: %s, fired at: %s, instance: %s%n", context.getTrigger().getKey(), sdf.format(context.getFireTime()), context.getScheduler().getSchedulerInstanceId());
		} catch (Exception e) {
			System.out.println("QUARTZ_API_TEST - ERROR executing job " + e);
		}
	}
}
