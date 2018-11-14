package br.edu.ufcg.lsd.core.plugins.databases;

public interface RASDatabase {
	
	int getCountOrder(String state);

	enum OrderState {
		FULFILLED, FAILED		
	}
	
}
