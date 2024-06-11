package br.com.palutec.core.util;

import java.security.MessageDigest;

import br.com.palutec.core.exception.SystemException;
import jakarta.xml.bind.DatatypeConverter;

public final class UtilCrypto {
	
	private UtilCrypto(){}
	
	public static String md5(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(bytes);
		    byte[] digest = md.digest();
		    return DatatypeConverter.printHexBinary(digest).toUpperCase();
		    
		}catch(Exception e) {
			throw new SystemException(e);
		}
	}

}
