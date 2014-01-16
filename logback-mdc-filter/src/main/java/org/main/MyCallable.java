package org.main;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MyCallable implements Callable<String> {
	private static Logger logger = LoggerFactory.getLogger(MyCallable.class);
	
	public String call() throws Exception {
		//MDC.put("key1", "key1value1");
		logger.debug("Hi, I am in MyCallable class");
		return "Hi, from MyCallable";
	}
}