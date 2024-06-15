package br.com.palutec.core.api.http.response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
public class RequestThreadContext {

	private static final ThreadLocal<RequestThreadContext> CURRENT_CONTEXT = new ThreadLocal<>();

	@Autowired(required = false)
	private VersionProperties version;
	
	private String user;
	private String requestId;
	private String callerIP;
	private String datasourceName;
	private Map<String, Object> sessionProperties = Collections.synchronizedMap(new HashMap<>());
	private HttpStatus responseHttpStatusCode;

	public static RequestThreadContext getContext() {
		RequestThreadContext context = CURRENT_CONTEXT.get();
		if(context == null) {
			context = new RequestThreadContext();
			CURRENT_CONTEXT.set(context);
		}
		return context;
	}

	public static void clear() {
		CURRENT_CONTEXT.remove();
	}

}
