package com.releaseday.consumer.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Configuration
@WebListener
public class ContextWebListener implements ServletContextListener {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		shutdownCleanupThread();
		System.out.println("contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed");
//		shutdownCleanupThread();
	}

	void shutdownCleanupThread() {
//		try {
//			AbandonedConnectionCleanupThread.shutdown();
//		} catch (InterruptedException e) {
//			logger.warn("SEVERE problem cleaning up: " + e.getMessage());
//			e.printStackTrace();
//		}
	}

}
