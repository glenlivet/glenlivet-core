package org.glenlivet.core.concurrency;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentExecutor {

	public static final int NORMAL_SIZE = 10;
	
	private int threadPoolSize; 
	
	private List<Runnable> tasks;
	
	public ConcurrentExecutor(List<Runnable> tasks){
		this(NORMAL_SIZE, tasks);
	}
	
	public ConcurrentExecutor(int size, List<Runnable> tasks){
		threadPoolSize = size;
		this.tasks = tasks;
	}
	
	public void execute(){
		ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
		for(Runnable r : tasks){
			executorService.execute(r);
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
