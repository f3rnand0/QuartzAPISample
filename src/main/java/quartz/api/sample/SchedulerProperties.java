package quartz.api.sample;

import java.util.Properties;

/**
 * Initialize the standard properties for the scheduler.
 *
 * @author Esteban Capello, schancay
 *
 */
public class SchedulerProperties {

	/**
	 * Static method that sets the standard values for the scheduler using the configuration file.
	 * @param dataSource
	 * @param schedulerName
	 * @param threadCount
	 * @param misfireThreshold
	 * @return
	 */
	public static Properties initializeProperties(String dataSource, String schedulerName, Integer threadCount, Integer misfireThreshold) {
		Properties properties = new Properties();

		// Configure main Scheduler Properties.
		properties.setProperty("org.quartz.scheduler.instanceName", "ClusteredScheduler-" + schedulerName);
		properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");

		// Configure ThreadPool.
		properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
		properties.setProperty("org.quartz.threadPool.threadPriority", "5");
		properties.setProperty("org.quartz.threadPool.threadCount", String.valueOf(threadCount));

		// Configure JobStore.
		properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.MSSQLDelegate");
		properties.setProperty("org.quartz.jobStore.useProperties", "false");
		properties.setProperty("org.quartz.jobStore.dataSource", "DS");
		properties.setProperty("org.quartz.jobStore.lockHandler.class", "org.quartz.impl.jdbcjobstore.UpdateLockRowSemaphore");
		properties.setProperty("org.quartz.jobStore.tablePrefix", "sampledb..QRTZ_");
		properties.setProperty("org.quartz.jobStore.isClustered", "true");
		properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", "20000");
		properties.setProperty("org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer", "true");
		properties.setProperty("org.quartz.jobStore.misfireThreshold", String.valueOf(misfireThreshold));
		properties.setProperty("org.quartz.scheduler.skipUpdateCheck", "true");

		// Configure Datasources.
		properties.setProperty("org.quartz.dataSource.DS.jndiURL", dataSource);
		properties.setProperty("org.quartz.dataSource.DS.java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");

		return properties;
	}
}
