package br.edu.ufcg.lsd.core.plugins.databases;

public interface RASDatabase {
	
	int getCountOrder(OrderType orderType, OrderState orderState);

	enum OrderState {
		FULFILLED, FAILED		
	}
	
	enum OrderType {
		COMPUTE("compute_order_table"), 
		VOLUME("volume_order_table"), 
		NETWORK("network_order_table"),
		ATTACHMENT("attachment_order_table"),
		PUBLIC_ID("public_ip_order_table");
		
		private String tableName;
		
		private OrderType(String tableName) {
			this.tableName = tableName;
		}
		
		public String getTableName() {
			return tableName;
		}	
	}
	
}
