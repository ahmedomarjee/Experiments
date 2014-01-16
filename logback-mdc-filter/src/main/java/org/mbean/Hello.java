package org.mbean;

import org.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextAwareBase;
import ch.qos.logback.classic.turbo.MDCFilter;
import ch.qos.logback.classic.turbo.TurboFilter;

public class Hello extends LoggerContextAwareBase implements HelloMBean {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	public void sayHello() {
		System.out.println("hello, world!");
		logger.debug("hello, world!");
	}

	public int add(int x, int y) {
		return x + y;
	}

	public String getName() {
		return this.name;
	}

	public int getCacheSize() {
		return this.cacheSize;
	}

	public synchronized void setCacheSize(int size) {
		this.cacheSize = size;
		System.out.println("Cache size now " + this.cacheSize);
	}
	
	public void setFilter(String filterName, String filterValue) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		for(TurboFilter filter:lc.getTurboFilterList()) {
			if(filter instanceof MDCFilter) {
				MDCFilter mdcFilter = (MDCFilter)filter;
				if(mdcFilter.getName().equalsIgnoreCase(filterName)) {
					mdcFilter.setValue(filterValue);
					System.out.print("Filter value is modified");
				}
			}
		}
	}

	private final String name = "Reginald";
	private int cacheSize = DEFAULT_CACHE_SIZE;
	private static final int DEFAULT_CACHE_SIZE = 200;
}
