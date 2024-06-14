package br.com.squada.core.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 *
 * <P><B>Description :</B><BR>
 * Exceções reservada para tratamento de negócio.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 11 de fev de 2021
 */
public class BusinessException extends SystemException{

	public static final long serialVersionUID = 6766489044452746072L;

	public BusinessException(String messageOrCode, Object ...args) {
		super(messageOrCode, args);
	}

	public BusinessException(String messageOrCode) {
		super(messageOrCode);
	}
	
	public static String getBusinessMessage(Throwable t) {
		BusinessException biz = ExceptionUtils.throwableOfType(t, BusinessException.class);
		return biz == null ? null : biz.getMessage();
	}

}
