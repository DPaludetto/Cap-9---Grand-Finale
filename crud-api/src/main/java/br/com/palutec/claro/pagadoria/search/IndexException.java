package br.com.palutec.claro.pagadoria.search;

import br.com.palutec.core.exception.SystemException;

public class IndexException extends SystemException {

	public IndexException(String messageOrCode, Object[] args) {
		super(messageOrCode, args);
	}

	public IndexException(Throwable e, String string) {
		super(e, string);
	}

}
