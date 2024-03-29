package org.mbean;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class Main {

	public static void main(String... args) throws MalformedObjectNameException, 
		InstanceAlreadyExistsException, MBeanRegistrationException, 
		NotCompliantMBeanException, InterruptedException {
		
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("com.example:type=Hello");
		Hello mbean = new Hello();
		mbs.registerMBean(mbean, name);

		/*System.out.println("Waiting forever...");
		Thread.sleep(Long.MAX_VALUE);*/
	}
}
