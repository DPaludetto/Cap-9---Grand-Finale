package br.com.squada.core.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.squada.core.util.text.I18nRepository;



/**
 *
 * <P><B>Description :</B><BR>
 * Classe base de tratamento de exceções.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 14 de out de 2019
 */
@JsonIgnoreProperties({ "stackTrace", "localizedMessage", "suppressed", "cause" })
public class SystemException extends RuntimeException{


	public static final long serialVersionUID = 4680583042186076042L;

	private static final I18nRepository messages = I18nRepository.getInstance();

	private final String errorCode;

	static {
		I18nRepository.registerMessageBundle("resources/coreMessages");
	}

	public SystemException(Throwable t) {
		super(t);
		errorCode = null;
	}

	public SystemException(String messageOrCode, Object ...args) {
		super(getFormattedMessage(messageOrCode, args));
		errorCode = findErrorCode(messageOrCode);
	}

	public SystemException(Throwable cause, String messageOrCode, Object ...args) {
		super(getFormattedMessage(messageOrCode, args), cause);
		errorCode = findErrorCode(messageOrCode);
	}

    public SystemException(final Throwable cause, final String message) {
        super(message, cause);
        errorCode = findErrorCode(message);
    }

	/**
	 * Oobtém uma mensagem formatada.
	 *
	 * @param messageOrCode
	 * @param args
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 14 de out de 2019
	 */
	protected static String getFormattedMessage(String messageOrCode, Object... args) {
		return messages.getFormattedMessage(messageOrCode, args);
	}

	private String findErrorCode(String message) {
		return I18nRepository.getInstance().existsKey(message) ? message : null;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

}
