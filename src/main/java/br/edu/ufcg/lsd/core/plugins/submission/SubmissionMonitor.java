package br.edu.ufcg.lsd.core.plugins.submission;

import java.util.List;

import br.edu.ufcg.lsd.core.models.MessageComponent;

public interface SubmissionMonitor {
	
	void sendToTMA(List<MessageComponent> messagesComponent) throws Exception;

}
