package com.zixueku.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 线程池类
 * @author huangweidong
 *
 */
public class ThreadPoolManager {
	private ExecutorService service;
	private static final ThreadPoolManager manager = new ThreadPoolManager();

	private ThreadPoolManager() {
		int num = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(num * 4);
	}

	public static ThreadPoolManager getInstance() {
		return manager;
	}

	public void addTask(Runnable runnable) {
		service.execute(runnable);
	}

}
