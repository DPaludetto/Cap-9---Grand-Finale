package br.com.palutec.core.util.assertion;

import br.com.palutec.core.exception.SystemException;
/**
 *
 * <P><B>Description :</B><BR>
 * Asserções com emissão de uma exceção de negócio quando falhar.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 18 de fev de 2021
 */
public class SystemAssertion extends AbstractAssertion {

	public static final SystemAssertion instance = new SystemAssertion();
	
	private SystemAssertion() {}

	/**
	 *
	 *
	 * @see br.com.acecon.core.business.AbstractAssertion#whenFail(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void whenFail(String errorCodeOrMessage, Object... messageArgs) {
		throw new SystemException(errorCodeOrMessage, messageArgs);
	}


}
