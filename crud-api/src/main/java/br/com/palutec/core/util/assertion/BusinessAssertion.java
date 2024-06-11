package br.com.palutec.core.util.assertion;

import br.com.palutec.core.exception.BusinessException;
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
public class BusinessAssertion extends AbstractAssertion {

	public static final BusinessAssertion instance = new BusinessAssertion();

	private BusinessAssertion() {}
	
	/**
	 *
	 *
	 * @see br.com.acecon.core.business.AbstractAssertion#whenFail(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void whenFail(String errorCodeOrMessage, Object... messageArgs) {
		throw new BusinessException(errorCodeOrMessage, messageArgs);
	}

	

}
