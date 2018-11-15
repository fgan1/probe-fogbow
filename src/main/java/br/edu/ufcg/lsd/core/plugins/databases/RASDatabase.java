package br.edu.ufcg.lsd.core.plugins.databases;

public interface RASDatabase {
	
	int getCountOrder(String orderTypeTableName, String state);

	enum OrderState {
		FULFILLED, FAILED		
	}
	
	enum OrderType {
		COMPUTE, VOLUME, NETWORK		
	}	
	
}
