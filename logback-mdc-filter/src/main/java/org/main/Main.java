package org.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.bson.NewBSONDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
/**
 * MDC uses {@link InheritableThreadLocal}
 * @author devasiaa
 *
 */
public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String... args) {
		try {
			org.mbean.Main.main(new String[]{""});
		} catch (Exception ex) {
			logger.error("error", ex);
		}
		//testThread();
		
		try {
			testExecuters();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			logger.error("Error", e);
		}
		
		//testPerformance();
		logger.debug("Finished !...");
	}
	
	public static void testPerformance() {
		MDC.put("key1", "value1");		
		long start = System.currentTimeMillis();
		for(int i=0; i < 10000000; i++) {
			logger.info("logging {}", i);
		}
		long end = System.currentTimeMillis();
		logger.trace("Time taken is {}", (end-start));
		System.out.println((end-start));	
	}
	
	public static void testThread() {		
		logger.debug("Main Thread");
		MDC.put("key1", "value1");
		MDC.put("key2", "value2");
			
		ThreadGroup group = new ThreadGroup("anish");

		Thread thread = new Thread(group, new MyRunnable());
		
		thread.start();
		
		Thread thread2 = new Thread(group, new MyRunnable());
		try {
			thread.join();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
		}
		logger.debug("Main Thread");
		MDC.remove("key1");
		thread2.start();
		try {
			thread2.join();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
		}
		MDC.remove("key1");
		MDC.remove("key2");
		logger.debug("Main Thread");
	}
	
	public static void testExecuters() throws InterruptedException, ExecutionException {	
		MDC.put("key1", "value1");
		MDC.put("key2", "value2");
		
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		//ExecutorService executorService = Executors.newCachedThreadPool();
		//ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<String> result = executorService.submit(new MyCallable());
		logger.debug("Result is : {}", result.get());
		MDC.remove("key1");
		result = executorService.submit(new MyCallable());
		logger.debug("Result is : {}", result.get());
		MDC.remove("key2");
		result = executorService.submit(new MyCallable());
		logger.debug("Result is : {}", result.get());
		executorService.shutdown();
		anish:
			System.out.println();
	}
}
