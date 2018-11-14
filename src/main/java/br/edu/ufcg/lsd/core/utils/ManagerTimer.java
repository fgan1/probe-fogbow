package br.edu.ufcg.lsd.core.utils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagerTimer {

	private ScheduledExecutorService executor;
	private ScheduledFuture<?> future;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerTimer.class);

	public ManagerTimer(ScheduledExecutorService executor) {
		this.executor = executor;
	}

	public void scheduleAtFixedRate(final Runnable task, long delay, long period) {
		this.future = executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					task.run();
				} catch (Throwable e) {
					LOGGER.error("Failed while executing timer task", e);
				}
			}
		}, delay, period, TimeUnit.MILLISECONDS);
	}

	public void cancel() {
		if (future != null) {
			future.cancel(false);
		}
		future = null;
	}

	public boolean isScheduled() {
		return future != null && !future.isCancelled();
	}

}