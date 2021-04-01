package quartz.api.sample;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import quartz.api.sample.jobs.Job1;

public class QuartzAPITestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Scheduler scheduler;
	// private String schedulerId = null;
	private String jndiURL = null;
	private String cronExpression = null;

	private String job1Name = "job1";
	private String filter = "(service.impl=" + job1Name + ")";
	private String trigger1Name = "trigger1";
	private String jobGroup1 = "job-group1";

	// private static Map<String, Scheduler> schedulersMap = new ConcurrentHashMap<String, Scheduler>();

	@Override
	public void init() throws ServletException {
		System.out.println("QUARTZ_API_TEST - QuartzAPITestServlet init successfully");
	}
	
	// @Override
	// protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// doPost(req, resp);
	// }
	//
	// @Override
	// protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// jndiURL = req.getParameter("jndiURL");
	// cronExpression = req.getParameter("cronExpression");
	// scheduleJobs();
	// }

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		jndiURL = req.getParameter("jndiURL");
		cronExpression = req.getParameter("cronExpression");
		String submit1 = req.getParameter("runButton");
		String submit2 = req.getParameter("deleteButton");

		if (submit1 != null) {
			scheduleJobs();
		} else if (submit2 != null) {
			unScheduleJobs();
		}
	}

	private void scheduleJobs() {
		try {
			Thread.currentThread().setContextClassLoader(QuartzAPITestServlet.class.getClassLoader());
			// scheduler = new StdSchedulerFactory().getScheduler();
			// schedulerId = scheduler.getSchedulerInstanceId();
			// Scheduler scheduler = schedulersMap.get(filter);

			if (scheduler == null) {
				System.out.println("QUARTZ_API_TEST - Getting scheduler instance");
				SchedulerFactory schedulerFactory = new StdSchedulerFactory(SchedulerProperties.initializeProperties(jndiURL, filter, 1, 1000));
				scheduler = schedulerFactory.getScheduler();
				System.out.println("QUARTZ_API_TEST - Scheduler: " + scheduler.getSchedulerName() + " created");
			}
			JobKey jobKey1 = JobKey.jobKey(job1Name, jobGroup1);
			System.out.println("QUARTZ_API_TEST - Building job: " + job1Name);
			JobDetail jobDetail = JobBuilder.newJob(Job1.class).withIdentity(jobKey1).build();
			TriggerKey trigerKey1 = TriggerKey.triggerKey(trigger1Name, jobGroup1);
			CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(trigerKey1).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
			System.out.println("QUARTZ_API_TEST - Creating trigger: " + trigger1Name + " for job: " + job1Name);
			scheduler.scheduleJob(jobDetail, cronTrigger);
			System.out.println("QUARTZ_API_TEST - Starting scheduler");
			scheduler.start();
			printJobsAndTriggers(scheduler);
		} catch (SchedulerException e) {
			System.out.println("QUARTZ_API_TEST - ERROR getting scheduler: " + e);
		}
	}

	private void printJobsAndTriggers(Scheduler scheduler) throws SchedulerException {
		System.out.println("QUARTZ_API_TEST - Quartz Scheduler: " + scheduler.getSchedulerName());
		for (String group : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey> groupEquals(group))) {
				System.out.println("QUARTZ_API_TEST - Found job identified by: " + jobKey);
			}
		}
		for (String group : scheduler.getTriggerGroupNames()) {
			for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.<TriggerKey> groupEquals(group))) {
				System.out.println("QUARTZ_API_TEST - Found trigger identified by: " + triggerKey);
			}
		}
	}

	private void unScheduleJobs() {
		try {
			// Scheduler scheduler = schedulersMap.get(filter);
			// schedulersMap.remove(filter);
			if (scheduler != null) {
//				TriggerKey trigerKey1 = TriggerKey.triggerKey(trigger1Name, jobGroup1);
//				System.out.println("QUARTZ_API_TEST - Deleting trigger: " + trigger1Name + " for job: " + job1Name);
//				scheduler.unscheduleJob(trigerKey1);
				JobKey jobKey1 = JobKey.jobKey(job1Name, jobGroup1);
				System.out.println("QUARTZ_API_TEST - Deleting job: " + job1Name);
				scheduler.deleteJob(jobKey1);
				System.out.println("QUARTZ_API_TEST - Shutting down scheduler: " + scheduler.getSchedulerName());
				scheduler.shutdown();
				scheduler = null;
			}
		} catch (SchedulerException e) {
			System.out.println("QUARTZ_API_TEST - ERROR shutting down scheduler: " + e);
		}
	}
}
