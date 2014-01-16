package org.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MyRunnable implements Runnable {
	public static Logger logger = LoggerFactory.getLogger(MyRunnable.class);
	
	public void run() {
		//MDC.put("key1", "key1value1");
		logger.debug("Hi, I am in MyRunnable class");	
	}
}
