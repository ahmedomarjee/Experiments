package org.async.main;

import org.async.tasks.LoggerTask;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String... args) throws InterruptedException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("*-context.xml");
		LoggerTask task = context.getBean(LoggerTask.class);
		task.log();
		System.out.println("main thread is done");
		context.close();
	}
}
