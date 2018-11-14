package br.edu.ufcg.lsd.core.models;

import eu.atmosphere.tmaf.monitor.message.Data;
import eu.atmosphere.tmaf.monitor.message.Data.Type;

// FIXME this will change. The probe message probably is not a String
public class MessageComponent {

	private ResourceMonitor resourceId;
	private Data.Type type;
	private long timestamp;
	private double value;

	public MessageComponent(ResourceMonitor resourceId, Type type, long timestamp, double value) {
		super();
		this.resourceId = resourceId;
		this.type = type;
		this.timestamp = timestamp;
		this.value = value;
	}
	
	public ResourceMonitor getResourceId() {
		return resourceId;
	}
	
	public Data.Type getType() {
		return type;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public double getValue() {
		return value;
	}

	public enum ResourceMonitor {
		FULFILLED(0000001), FAILED(0000002), LAST_MEASUREMENT(0000003);

		private int value;

		private ResourceMonitor(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
