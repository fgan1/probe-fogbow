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

public class RASComponent implements Component {

	private static final Logger LOGGER = LoggerFactory.getLogger(RASComponent.class);
	
	private static final int FIRST_QUERY_TIME = -1;
	private static final String COMMAN = ",";
	
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

		String lastQueryTimeStr = this.lastQueryTime == FIRST_QUERY_TIME ? "-" :  String.valueOf(this.lastQueryTime);
		long nowQueryTime = DateUtils.currentTimeMillis();
		String nowQueryTimeStr = String.valueOf(nowQueryTime);
		
		List<MessageComponent> messages = new LinkedList<MessageComponent>();
		StringBuffer sb = new StringBuffer();
		sb.append(lastQueryTimeStr); 
		sb.append(COMMAN); 
		sb.append(nowQueryTimeStr);
		sb.append(COMMAN);
		sb.append(String.valueOf(ordersFulfilled));
		sb.append(COMMAN);
		sb.append(String.valueOf(ordersFailed));
		
		messages.add(new MessageComponent(sb.toString()));
				
		this.lastQueryTime = nowQueryTime;
		
		return messages;
	}
	
	protected int getOrdersFulfilled() {
		return this.rasDatabase.getCountOrder(RASDatabase.OrderState.FULFILLED.toString());
	}
	
	protected int getOrdersFailed() {
		return this.rasDatabase.getCountOrder(RASDatabase.OrderState.FAILED.toString());
	}	

}
