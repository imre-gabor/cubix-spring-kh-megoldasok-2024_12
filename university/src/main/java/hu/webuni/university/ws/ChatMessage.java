package hu.webuni.university.ws;

import lombok.Data;

@Data
public class ChatMessage {
	
	private String sender;
	private long courseId;
	private String text;
}
