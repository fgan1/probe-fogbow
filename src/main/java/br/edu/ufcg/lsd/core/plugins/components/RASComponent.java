package br.edu.ufcg.lsd.core.plugins.components;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.ufcg.lsd.core.models.MessageComponent;
import br.edu.ufcg.lsd.core.plugins.databases.PrimaryRASDatabase;
import br.edu.ufcg.lsd.core.plugins.databases.RASDatabase;
import br.edu.ufcg.lsd.core.utils.DateUtils;
import eu.atmosphere.tmaf.monitor.message.Data;

public class RASComponent implements Component {

	private static final Logger LOGGER = LoggerFactory.getLogger(RASComponent.class);
	
	private static final int FIRST_QUERY_TIME = -1;
	
	private RASDatabase rasDatabase;
	private long lastQueryTime = FIRST_QUERY_TIME;
	
	public RASComponent(Properties properties) throws Exception {
		try {
			this.rasDatabase = new PrimaryRASDatabase(properties);
		} catch (Exception e) {
			String errorMsg = "Is not possible initialize the database";
			LOGGER.error(errorMsg, e);
			throw new Exception(errorMsg);
		}
	}
	
	@Override
	public List<MessageComponent> getMessages() {
		int ordersFulfilled = getOrdersFulfilled();
		int ordersFailed = getOrdersFailed();
		long nowQueryTime = DateUtils.currentTimeMillis();
		
		List<MessageComponent> messagesComponent = new LinkedList<MessageComponent>();
		messagesComponent.add(new MessageComponent(
				MessageComponent.ResourceMonitor.FULFILLED, Data.Type.MEASUREMENT, nowQueryTime, ordersFulfilled));
		messagesComponent.add(new MessageComponent(
				MessageComponent.ResourceMonitor.FAILED, Data.Type.MEASUREMENT, nowQueryTime, ordersFailed));
		messagesComponent.add(new MessageComponent(
				MessageComponent.ResourceMonitor.LAST_MEASUREMENT, Data.Type.EVENT, nowQueryTime, this.lastQueryTime));	
		this.lastQueryTime = nowQueryTime;				
		
		return messagesComponent;
	}
	
	protected int getOrdersFulfilled() {
		return this.rasDatabase.getCountOrder(RASDatabase.OrderState.FULFILLED.toString());
	}
	
	protected int getOrdersFailed() {
		return this.rasDatabase.getCountOrder(RASDatabase.OrderState.FAILED.toString());
	}	

}
