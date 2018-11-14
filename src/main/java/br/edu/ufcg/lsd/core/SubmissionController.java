package br.edu.ufcg.lsd.core;

import java.util.List;

import br.edu.ufcg.lsd.core.models.MessageComponent;
import br.edu.ufcg.lsd.core.utils.ProbeConstants.Properties;
import eu.atmosphere.tmaf.monitor.client.BackgroundClient;
import eu.atmosphere.tmaf.monitor.message.Data;
import eu.atmosphere.tmaf.monitor.message.Message;
import eu.atmosphere.tmaf.monitor.message.Observation;

// TODO implements. This is the default example (probe-demo)
public class SubmissionController {

	@SuppressWarnings("unused")
	private Properties properties;
	
	public SubmissionController(Properties properties) {
		this.properties = properties;
	}
	
	public void sendToTMA(List<MessageComponent> messages) {

		BackgroundClient client = new BackgroundClient("http://127.0.0.1:5000/monitor");

		int probeId = 0; // get in properties
		String password = ""; // get in properties
		client.authenticate(probeId, password.getBytes());

		Message message;

		boolean start = client.start();

		for (int i = 0; i < 10; i++) {
			message = client.createMessage();
			message.setResourceId(101098);

			message.addData(new Data(Data.Type.EVENT, i, new Observation(100000 + i, 10000.00001 + i)));

			client.send(message);
		}

		try {
			Thread.sleep(100000);
			boolean stop = client.stop();
		} catch (Exception ex) {} 

		client.shutdown();
	}

}
