package br.com.palutec.core.api.http.response;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultErrorResponse {

	private int httpCode = 400;
	private List<ApiMessage> messages;

	public List<ApiMessage> getMessages(){
		if(messages == null) {
			messages = new LinkedList<>();
		}
		return Collections.unmodifiableList(this.messages);
	}

	public void addMessage(String message, String apiCode, String messageType, String link) {
		getMessages().add(new ApiMessage(message, apiCode, messageType, link));
	}
}
