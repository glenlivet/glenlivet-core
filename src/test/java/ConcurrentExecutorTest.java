import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.glenlivet.core.concurrency.ConcurrentExecution;
import org.glenlivet.core.concurrency.ConcurrentExecutor;
import org.junit.Test;

public class ConcurrentExecutorTest {

	@Test
	public void secondTest() {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		long start = new Date().getTime();
		for (int i = 0; i < 5; i++) {
			final int j = i;
			executorService.execute(new Runnable(){

				@Override
				public void run() {
					String msg = "execution" + j;
					String threadID = "[" + Thread.currentThread().getId() + "-"
							+ Thread.currentThread().getName() + "]";
					System.out.println(threadID + " " + msg);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
					}
					
				}
				
			});
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
		}
		System.out.println("execution finished.");
		long end = new Date().getTime();
		System.out.println("Time used: " + (end - start) / 1000);
	}

	//@Test
	public void firstTest() {
		List<ConcurrentExecution> cel = new ArrayList<ConcurrentExecution>();

		ConcurrentExecution ce1 = new SysoutExecution("execution1");
		ConcurrentExecution ce2 = new SysoutExecution("execution2");
		ConcurrentExecution ce3 = new SysoutExecution("execution3");
		ConcurrentExecution ce4 = new SysoutExecution("execution4");
		ConcurrentExecution ce5 = new SysoutExecution("execution5");
		ConcurrentExecution ce6 = new SysoutExecution("execution6");

		cel.add(ce1);
		cel.add(ce2);
		cel.add(ce3);
		cel.add(ce4);
		cel.add(ce5);
		cel.add(ce6);

		long start = new Date().getTime();

		ConcurrentExecutor cer = new ConcurrentExecutor(cel);
		cer.execute();
		System.out.println("execution finished.");
		long end = new Date().getTime();
		System.out.println("Time used: " + (end - start) / 1000);
	}

	class SysoutExecution implements ConcurrentExecution {

		String msg;

		SysoutExecution(String msg) {
			this.msg = msg;
		}

		@Override
		public void execute() {
			String threadID = "[" + Thread.currentThread().getId() + "-"
					+ Thread.currentThread().getName() + "]";
			System.out.println(threadID + " " + msg);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
		}

	}

}
