package br.com.palutec.core.util;

/*
 * Created on 04/05/2011
 * Project : NetGeral
 *
 * Copyright © 2011 NET.
 * Brasil
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of NET.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 */


import java.util.Locale;

/**
 * <p><b>Description: </b><br>
 *
 * </p>
 * <b>
 * Issues: <br>
 *
 * </b>
 * @author diego.amaral
 * @since 04/05/2011
 * @version
 */
public final class UtilConstants {

	private UtilConstants(){}

	/**
	 * Encoding default do sistema.
	 */
	public static final String DEFAULT_ENCODING = "ISO-8859-1";
	/**
	 * Língua do sistema.
	 */
	public static final String LANG = "pt";
	/**
	 * Região do sistema.
	 */
	public static final String COUNTRY = "BR";

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Locale default do sistema.
	 * <p>
	 * @return
	 * @since 28/06/2011
	 * @author diego.amaral
	 */
	public static Locale getDefaultLocale(){
		return new Locale(UtilConstants.LANG, UtilConstants.COUNTRY);
	}

}
