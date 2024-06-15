/**
 * Project : NETGeral
 *
 * @author Diego C. Amaral
 * @since 30 de abr de 2019
 * Copyright © 2018 NET.
 * Brasil
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of NET.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Net Serviços.
 *
 */
package br.com.palutec.core.util;

import java.io.Closeable;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <P><B>Description :</B><BR>
 *
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 30 de abr de 2019
 */
public final class UtilIO {

	private static final Logger log = LoggerFactory.getLogger(UtilIO.class);

	private UtilIO() {}//Singleton
	
	/**
	 * Encerra um recurso.
	 *
	 * @param toClose
	 *
	 * @author Diego C. Amaral
	 * @since 30 de abr de 2019
	 */
	public static void closeSilently(Closeable ...toCloseArray) {
		if(toCloseArray == null) {
			return;
		}
    	for(Closeable toClose : toCloseArray) {
    		try {
	            if (toClose != null) {
	            	toClose.close();
	            }
            } catch (Exception e) {
            	log.debug("Error a closeable resource. {}", e.getMessage());
            }
    	}
	}

	/**
	 * Verifica se InputStreamReader esta pronto.
	 *
	 * @param is
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 14 de out de 2019
	 */
	public static boolean isReady(InputStream is) {
		boolean isReady = false;
		try {
			isReady = is.available() >= 0;
		}catch(Exception e) {
			isReady = false;
		}
		return isReady;
	}
}
