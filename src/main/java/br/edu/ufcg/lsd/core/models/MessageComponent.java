package br.edu.ufcg.lsd.core.models;

// FIXME this will change. The probe message probably is not a String
public class MessageComponent {

	private String content;

	public MessageComponent(String content) {
		super();
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

}
