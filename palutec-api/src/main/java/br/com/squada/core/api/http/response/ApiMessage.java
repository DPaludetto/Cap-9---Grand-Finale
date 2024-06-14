package br.com.squada.core.api.http.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMessage {

	private String message;
	private String apiCode;
	private String messageType;
	private ApiMessageRelatedLink link;

	public ApiMessage(String message, String apiCode, String messageType, String link) {
		this.message = message;
		this.apiCode = apiCode;
		this.messageType = messageType;
		this.link = new ApiMessageRelatedLink(link);
	}



}
