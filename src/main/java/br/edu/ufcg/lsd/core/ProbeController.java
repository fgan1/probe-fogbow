package br.edu.ufcg.lsd.core;

import java.util.List;
import java.util.Properties;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.ufcg.lsd.core.models.MessageComponent;
import br.edu.ufcg.lsd.core.plugins.components.Component;
import br.edu.ufcg.lsd.core.plugins.components.RASComponent;
import br.edu.ufcg.lsd.core.utils.ManagerTimer;
import br.edu.ufcg.lsd.core.utils.ProbeConstants;

public class ProbeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProbeController.class);
	
	private static final int SCHEDULER_TIME_DEFAULT = 5000;
	
	private Properties properties;
	private Component component;
	private SubmissionController submissionController;
	private final ManagerTimer schedulerTimer;
	
	public ProbeController(Properties properties) throws Exception {
		this.properties = properties;
		this.schedulerTimer = new ManagerTimer(Executors.newScheduledThreadPool(1));
		
		configureComponent(properties);
	}
	
	public void start() {
		long schedulerPeriod = getSchedulerPeriod();
		
		this.schedulerTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					sendToTMA();
				} catch (Throwable e) {
					LOGGER.error("Error while sending message to TMA", e);
				}
			}

		}, 0, schedulerPeriod);
	}

	private long getSchedulerPeriod() {
		String schedulerPeriodStr = properties.getProperty(ProbeConstants.Properties.SCHEDULER_PERIOD);
		
		long schedulerPeriod = SCHEDULER_TIME_DEFAULT;
		try {
			schedulerPeriod = schedulerPeriodStr.isBlank() ? Long.parseLong(schedulerPeriodStr) : SCHEDULER_TIME_DEFAULT;			
		} catch (NumberFormatException e) {
			LOGGER.warn("The " + ProbeConstants.Properties.SCHEDULER_PERIOD + " is wrong" , e);
		}
		
		return schedulerPeriod;
	}
	
	protected void sendToTMA() {
		List<MessageComponent> messages = this.component.getMessages();
		this.submissionController.sendToTMA(messages);
	}
	
	// TODO implement support to other components
	protected void configureComponent(Properties properties) throws Exception {
		try {
			this.component = new RASComponent(properties);
		} catch (Exception e) {
			String errorMsg = "Is not possible initialize the component";
			LOGGER.error(errorMsg, e);
			throw new Exception(errorMsg);
		}
	}	
	
}
