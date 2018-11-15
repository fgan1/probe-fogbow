package br.edu.ufcg.lsd.core.plugins.components;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.ufcg.lsd.core.models.MessageComponent;
import br.edu.ufcg.lsd.core.plugins.databases.PrimaryRASDatabase;
import br.edu.ufcg.lsd.core.plugins.databases.RASDatabase;
import br.edu.ufcg.lsd.core.plugins.databases.RASDatabase.OrderType;
import br.edu.ufcg.lsd.core.plugins.databases.RASDatabase.OrderState;
import br.edu.ufcg.lsd.core.utils.DateUtils;
import eu.atmosphere.tmaf.monitor.message.Data;

public class RASComponent implements Component {

	private static final Logger LOGGER = LoggerFactory.getLogger(RASComponent.class);
	
	private static final int FIRST_QUERY_TIME = -1;
	
	private RASDatabase rasDatabase;
	
	@SuppressWarnings("unused")
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
		int computesFulfilled = getOrdersQuant(OrderType.COMPUTE, OrderState.FULFILLED);
		int computesFailed = getOrdersQuant(OrderType.COMPUTE, OrderState.FULFILLED);
		int volumesFulfilled = getOrdersQuant(OrderType.VOLUME, OrderState.FULFILLED);
		int volumesFailed = getOrdersQuant(OrderType.VOLUME, OrderState.FULFILLED);
		int networksFulfilled = getOrdersQuant(OrderType.NETWORK, OrderState.FULFILLED);
		int networksFailed = getOrdersQuant(OrderType.NETWORK, OrderState.FULFILLED);		
		
		long nowQueryTime = DateUtils.currentTimeMillis();
		
		List<MessageComponent> messagesComponent = new LinkedList<MessageComponent>();
		messagesComponent.add(new MessageComponent(
				MessageComponent.DescriptionMonitor.FULFILLED_COMPUTES, Data.Type.MEASUREMENT, nowQueryTime, computesFulfilled));
		messagesComponent.add(new MessageComponent(
				MessageComponent.DescriptionMonitor.FAILED_COMPUTES, Data.Type.MEASUREMENT, nowQueryTime, computesFailed));
		messagesComponent.add(new MessageComponent(
				MessageComponent.DescriptionMonitor.FULFILLED_VOLUMES, Data.Type.MEASUREMENT, nowQueryTime, volumesFulfilled));
		messagesComponent.add(new MessageComponent(
				MessageComponent.DescriptionMonitor.FAILED_VOLUMES, Data.Type.MEASUREMENT, nowQueryTime, volumesFailed));
		messagesComponent.add(new MessageComponent(
				MessageComponent.DescriptionMonitor.FULFILLED_NETWORKS, Data.Type.MEASUREMENT, nowQueryTime, networksFulfilled));
		messagesComponent.add(new MessageComponent(
				MessageComponent.DescriptionMonitor.FAILED_NETWORKS, Data.Type.MEASUREMENT, nowQueryTime, networksFailed));		
		
		// TODO will be used in the future
		this.lastQueryTime = nowQueryTime;		
		
		return messagesComponent;
	}
	
	protected int getOrdersQuant(RASDatabase.OrderType type, RASDatabase.OrderState state) {
		return this.rasDatabase.getCountOrder(type.toString(), state.toString());
	}
	

}
