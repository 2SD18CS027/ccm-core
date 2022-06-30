package com.thefreshlystore.utilities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

@Singleton
public class ThreadPool {
	private static ExecutorService executor = null;
	
	private ThreadPool () {}
	
	public static synchronized ExecutorService get() {
		if(executor == null) { executor = Executors.newWorkStealingPool(); }
		return executor;
	}
}
