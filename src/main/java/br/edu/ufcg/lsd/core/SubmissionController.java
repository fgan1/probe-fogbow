package br.edu.ufcg.lsd.core;

import java.util.List;

import br.edu.ufcg.lsd.core.models.MessageComponent;
//import eu.atmosphere.tmaf.monitor.client.BackgroundClient;

public class SubmissionController {

	public void sendToTMA(List<MessageComponent> messages) {
		
//      LOGGER.info("Trust me! This is ATMOSPHERE!");
////    BackgroundClient client = new BackgroundClient("http://127.0.0.1:5000/monitor");
//    BackgroundClient client = new BackgroundClient();
//
//    client.authenticate(1098, "pass".getBytes());
//
//    Message message;
//
//    boolean start = client.start();
//    LOGGER.info("start {}!", start);
//
//    for (int i = 0; i < 10; i++) {
//        LOGGER.debug("i = " + i);
//        message = client.createMessage();
//        message.setResourceId(101098);
//
//        message.addData(new Data(Data.Type.EVENT, i, new Observation(100000 + i, 10000.00001 + i)));
//
//        message.addData(new Data(Data.Type.MEASUREMENT, i, new Observation(10000 + i, 10000.00001 + i),
//                new Observation(20000 + i, 20000.00001 + i),
//                new Observation(30000 + i, 30000.00001 + i),
//                new Observation(40000 + i, 40000.00001 + i),
//                new Observation(50000 + i, 50000.00001 + i),
//                new Observation(60000 + i, 60000.00001 + i),
//                new Observation(60000 + i, 60000.00001 + i),
//                new Observation(60000 + i, 60000.00001 + i),
//                new Observation(60000 + i, 60000.00001 + i)
//        ));
//
//        client.send(message);
//    }
//
//    try {
//        Thread.sleep(100000);
//        boolean stop = client.stop();
//        LOGGER.info("stop {}!", stop);
//    } catch (InterruptedException ex) {
//        ex.printStackTrace();
//    } catch (ExecutionException ex) {
//        ex.printStackTrace();
//    }
//
//    //        int result = client.send(message);
//    client.shutdown();
//    LOGGER.info("Trust me! This is ATMOSPHERE!");
		
		
	}
	
}
