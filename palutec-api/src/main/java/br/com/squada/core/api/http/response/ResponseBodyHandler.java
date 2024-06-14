package br.com.squada.core.api.http.response;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import br.com.squada.core.util.UtilObject;
import br.com.squada.core.util.web.RequestIdentifierFilter;

/**
 * 
 * <P>
 * <B>Description :</B><BR>
 * Executa o tratamento de mensagens de response.
 * </P>
 * <P>
 * <B> Issues : <BR>
 * None </B>
 *
 * @author Diego C. Amaral
 * @since 21 de jun de 2021
 */
//@RestControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseBodyHandler.class);

	private final String version;

	@Inject
	public ResponseBodyHandler(@Qualifier("compactVersion") final String version) {
		this.version = version;
	}

	@Override
	public boolean supports(final MethodParameter returnType,
			final Class<? extends HttpMessageConverter<?>> converterType) {
		return !returnType.getGenericParameterType().equals(String.class);
	}

	@Override
	public Object beforeBodyWrite(final Object object, final MethodParameter returnType,
			final MediaType selectedContentType, final Class<? extends HttpMessageConverter<?>> selectedConverterType,
			final ServerHttpRequest request, final ServerHttpResponse response) {

		String transactionId = "undefined";
		if (request instanceof ServletServerHttpRequest) {
			final String headerTransactionId = ((ServletServerHttpRequest) request).getServletRequest()
					.getHeader(RequestIdentifierFilter.REQUEST_ID_HEADER);
			transactionId = headerTransactionId == null ? transactionId : headerTransactionId;
		}
		
		if(object instanceof byte[]) {
			return object;
		}

		final ApiRequestResponseWrapper wrapper = new ApiRequestResponseWrapper(version, transactionId);

		LOGGER.debug("no change to the response object");

		wrapper.setData(object);

		HttpStatus status = RequestThreadContext.getContext().getResponseHttpStatusCode();
		ResponseErrorBody errorBody = UtilObject.cast(object, ResponseErrorBody.class);
		if (errorBody != null) {
			Integer intCode = errorBody.getStatus();
			if (intCode != null) {
				status = HttpStatus.valueOf(intCode);
			}
		}
		response.setStatusCode(status == null ? HttpStatus.OK : status);

		return wrapper;
	}

}
