package org.async.tasks;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LoggerTask {
	
	@Async
	public void log() throws InterruptedException {
		Thread.sleep(2000);
		System.out.println("LoggerTask is invoked.");
	}
}
