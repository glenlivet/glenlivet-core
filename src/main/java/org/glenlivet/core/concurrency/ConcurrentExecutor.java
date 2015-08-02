package org.glenlivet.core.concurrency;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.util.Assert;

public class ConcurrentExecutor {

	private List<ConcurrentExecution> crs;

	public ConcurrentExecutor(List<ConcurrentExecution> runnables) {
		Assert.isTrue(runnables != null, "The param cannot be null!");
		crs = runnables;
	}

	public void execute() {

		ExecutorService executorService = Executors.newFixedThreadPool(crs
				.size());
		final CyclicBarrier cb = new CyclicBarrier(crs.size() + 1);
		try {
			for (final ConcurrentExecution ce : crs) {
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						try {
							ce.execute();
						} finally {
							try {
								cb.await();
							} catch (InterruptedException
									| BrokenBarrierException e) {
								e.printStackTrace();
							}
						}
					}

				});
			}
			cb.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}finally{
			executorService.shutdown();
		}

	}

}
