package br.edu.ufcg.lsd.core;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.ufcg.lsd.core.models.MessageComponent;
import br.edu.ufcg.lsd.core.utils.ProbeConstants;
import eu.atmosphere.tmaf.monitor.client.BackgroundClient;
import eu.atmosphere.tmaf.monitor.message.Data;
import eu.atmosphere.tmaf.monitor.message.Data.Type;
import eu.atmosphere.tmaf.monitor.message.Message;
import eu.atmosphere.tmaf.monitor.message.Observation;

// TODO implement. This is the default example of (probe-demo)
// TODO implement test too
public class SubmissionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionController.class);
	
	private String monitorUrl;
	private Integer probeId;
	private String probePassword;
	
	public SubmissionController(Properties properties) {
		this.monitorUrl = properties.getProperty(ProbeConstants.Properties.MONITOR_URL);
		// TODO check if is an integer
		this.probeId = Integer.valueOf(properties.getProperty(ProbeConstants.Properties.PROBE_ID));
		this.probePassword = properties.getProperty(ProbeConstants.Properties.PROBE_PASSWORD);
	}
	
	public void sendToTMA(List<MessageComponent> messagesComponent) throws Exception {

		BackgroundClient client = new BackgroundClient(this.monitorUrl);

		boolean authenticated = client.authenticate(this.probeId, this.probePassword.getBytes());
		if (!authenticated) {
			// TODO
			throw new Exception("");
		}
		
		boolean start = client.start();
		if (!start) {
			// TODO
			throw new Exception("");			
		}

		for (MessageComponent messageComponent : messagesComponent) {
			Message message = client.createMessage();
			
			message.setResourceId(messageComponent.getResourceId().getValue());
			
			Type type = messageComponent.getType();
			long time = messageComponent.getTimestamp();
			double value = messageComponent.getValue();
			int descriptionId = 0; // TODO check
			
			message.addData(new Data(type, descriptionId, new Observation(time, value)));
			
			client.send(message);			
		}

		try {
			Thread.sleep(100000); // TODO check the reason
			boolean stop = client.stop();
			if (!stop) {
				// TODO check ! Try again ?
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		} 

		client.shutdown();
	}

}
