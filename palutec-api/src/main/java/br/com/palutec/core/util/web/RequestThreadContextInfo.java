package br.com.palutec.core.util.web;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
/**
 *
 * <P><B>Description :</B><BR>
 * Atributos de contexto da "sessão" usuário.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 28 de abr de 2021
 */
@Getter
@Setter
public class RequestThreadContextInfo {

	private static final ThreadLocal<RequestThreadContextInfo> CURRENT_CONTEXT = new ThreadLocal<>();

	private String user;
	private String requestId;
	private String callerIP;
	private Map<String, Object> sessionProperties = Collections.synchronizedMap(new HashMap<>());
	private HttpStatus responseHttpStatusCode;
	private URL requestedUrl;

	public static RequestThreadContextInfo getContext() {
		RequestThreadContextInfo context = CURRENT_CONTEXT.get();
		if(context == null) {
			context = new RequestThreadContextInfo();
			CURRENT_CONTEXT.set(context);
		}
		return context;
	}

	public static void clear() {
		CURRENT_CONTEXT.remove();
	}

}
