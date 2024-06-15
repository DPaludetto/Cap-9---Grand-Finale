package br.com.palutec.core.util.assertion;

import jakarta.validation.ValidationException;

public class BusinessValidation extends AbstractAssertion {

	public static final BusinessValidation instance = new BusinessValidation();
	
	private BusinessValidation() {}
	
	@Override
	public void whenFail(String errorCodeOrMessage, Object... messageArgs) {
		throw new ValidationException(String.format(errorCodeOrMessage, messageArgs));
	}

}
