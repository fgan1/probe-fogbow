package br.edu.ufcg.lsd.core.models;

import eu.atmosphere.tmaf.monitor.message.Data;
import eu.atmosphere.tmaf.monitor.message.Data.Type;

public class MessageComponent {

	private DescriptionMonitor descriptionId;
	private Data.Type type;
	private long timestamp;
	private double value;

	public MessageComponent(DescriptionMonitor descriptionMonitorId, Type type, long timestamp, double value) {
		super();
		this.descriptionId = descriptionMonitorId;
		this.type = type;
		this.timestamp = timestamp;
		this.value = value;
	}
	
	public DescriptionMonitor getDescriptionId() {
		return descriptionId;
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

	public enum DescriptionMonitor {
		FULFILLED_COMPUTES(0000001), 
		FAILED_COMPUTES(0000002),
		FULFILLED_VOLUMES(0000003),
		FAILED_VOLUMES(0000004),
		FULFILLED_NETWORKS(0000005),
		FAILED_NETWORKS(0000005),
		LAST_MEASUREMENT(0000101); // TODO will be used in the future

		private int value;

		private DescriptionMonitor(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
