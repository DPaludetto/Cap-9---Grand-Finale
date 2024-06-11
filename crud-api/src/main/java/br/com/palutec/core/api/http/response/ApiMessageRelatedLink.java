package br.com.palutec.core.api.http.response;

import lombok.Getter;

@Getter
public class ApiMessageRelatedLink {

	private String href;
	private String rel;

	public ApiMessageRelatedLink(String href, String rel) {
		this.href = href;
		this.rel = rel;
	}
	public ApiMessageRelatedLink(String href) {
		this(href, "related");
	}


}
