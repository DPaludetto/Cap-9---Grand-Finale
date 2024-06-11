package br.com.palutec.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p><b>Description: </b><br>
 * Utilitário de conversões de tipos numáricos.
 * </p>
 * <b>
 * Issues: <br>
 *
 * </b>
 * @author diego.amaral
 * @since 17/12/2010
 * @version
 */
public final class UtilNumber {

	private static final Log LOG = LogFactory.getLog(UtilNumber.class);
	private static final String MSG_TEMPLATE = "HttpApiResponseError ao fazer conversão de '%s' para %s. Retornando valor '%s' [%s].";

	//Utility class
	private UtilNumber(){}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em Integer. Se ocorrer erro na conversão, null é retornado.
	 * <p>
	 * @param Number a ser convertido.
	 * @return Numérico convertido.
	 * @since 17/12/2010
	 * @author diego.amaral
	 */
	public static Integer toInteger(final Number number){
		return toInteger(number, null);
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em Long. Se ocorrer erro na conversão, null é retornado.
	 * <p>
	 * @param Number a ser convertido.
	 * @return Numérico convertido.
	 * @since 17/12/2010
	 * @author diego.amaral
	 */

	public static Long toLong(final Number number){
		return toLong(number, null);
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em Long. Se ocorrer erro na conversão, um valor default é especificado.
	 * <p>
	 * @param Valor a ser convertido.
	 * @param Valor default se não for possível realizar a conversão.
	 * @return Numérico convertido.
	 * @since 14/06/2011
	 * @author diego.amaral
	 */
	public static Long toLong(final Object value, final Long defaultValue) {
		Long returnValue = defaultValue;
		try{
			String strValue = String.valueOf(value);
			strValue = UtilString.replaceAll(strValue, ",", ".");
			if(strValue.contains(".")){
				strValue = strValue.substring(0, strValue.indexOf('.'));
			}
			returnValue = Long.valueOf(UtilString.toString(strValue, null));
		}catch(final Exception e){
			if(LOG.isWarnEnabled()){
				LOG.warn(String.format(MSG_TEMPLATE, value, "Long", defaultValue, e.getMessage()));
			}
		}

		return returnValue;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em Float. Se ocorrer erro na conversão, null é retornado.
	 * <p>
	 * @param Number a ser convertido.
	 * @return Numérico convertido.
	 * @since 17/12/2010
	 * @author diego.amaral
	 */
	public static Float toFloat(final Number number){
		return toFloat(number, null);
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em BigDecimal. Se ocorrer erro na conversão, null é retornado.
	 * <p>
	 * @param Number a ser convertido.
	 * @return Numérico convertido.
	 * @since 17/12/2010
	 * @author diego.amaral
	 */
	public static BigDecimal toBigDecimal(final Number number){
		return toBigDecimal(number, null);
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Converte uma String em BigDecimal
	 * <p>
	 * @param Valor a ser convertido.
	 * @param Valor default se não for possível realizar a conversão.
	 * @return Numérico convertido.
	 * @since 04/05/2011
	 * @author diego.amaral
	 */
	public static BigDecimal toBigDecimal(final Object objBig, final BigDecimal defaultValue){
		BigDecimal big = defaultValue;
		try{

			final String stringNumber = UtilString.replaceAll(UtilString.toString(objBig, null), ",", ".");
			big = new BigDecimal(stringNumber);
		}catch(final Exception e){
			if(LOG.isWarnEnabled()){
				LOG.warn(String.format(MSG_TEMPLATE, objBig, "BigDecimal", defaultValue, e.getMessage()));
			}
		}
		return big;

	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em BigDecimal. Se ocorrer erro na conversão, null é retornado.
	 * <p>
	 * @param Number a ser convertido.
	 * @return Numérico convertido.
	 * @since 17/12/2010
	 * @author diego.amaral
	 */
	public static BigInteger toBigInteger(final Number number){
		return toBigInteger(number, null);
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Converte uma String em BigDecimal
	 * <p>
	 * @param Valor a ser convertido.
	 * @param Valor default se não for possível realizar a conversão.
	 * @return Numérico convertido.
	 * @since 04/05/2011
	 * @author diego.amaral
	 */
	public static BigInteger toBigInteger(final Object objBig, final BigInteger defaultValue){
		BigInteger big = defaultValue;
		try{

			final String stringNumber = UtilString.replaceAll(UtilString.toString(objBig, null), ",", ".");
			big = new BigInteger(stringNumber);
		}catch(final Exception e){
			if(LOG.isWarnEnabled()){
				LOG.warn(String.format(MSG_TEMPLATE, objBig, "BigDecimal", defaultValue, e.getMessage()));
			}
		}
		return big;

	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em Integer. Se ocorrer erro na conversão, um valor default é especificado.
	 * <p>
	 * @param Valor a ser convertido.
	 * @param Valor default se não for possível realizar a conversão.
	 * @return Numérico convertido.
	 * @since 14/06/2011
	 * @author diego.amaral
	 */
	public static Integer toInteger(final Object value, final Integer defaultValue) {
		Integer returnValue = defaultValue;
		try{
			String strValue = String.valueOf(value);
			strValue = UtilString.replaceAll(strValue, ",", ".");
			if(strValue.contains(".")){
				strValue = strValue.substring(0, strValue.indexOf('.'));
			}
			returnValue = Integer.valueOf(UtilString.toString(strValue, null));
		}catch(final Exception e){
			if(LOG.isDebugEnabled()){
				LOG.debug(String.format(MSG_TEMPLATE, value, "Integer", defaultValue, e.getMessage()));
			}
		}

		return returnValue;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em Integer. Se ocorrer erro na conversão, um valor default é especificado.
	 * <p>
	 * @param Valor a ser convertido.
	 * @param Valor default se não for possível realizar a conversão.
	 * @return Numérico convertido.
	 * @since 14/06/2011
	 * @author diego.amaral
	 */
	public static Float toFloat(final Object number, final Float defaultValue) {
		Float value = defaultValue;
		try{
			value = Float.valueOf(UtilString.toString(number, null));
		}catch(final Exception e){
			if(LOG.isDebugEnabled()){
				LOG.debug(String.format(MSG_TEMPLATE, number, "Float", defaultValue, e.getMessage()));
			}
		}
		return value;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em Double. Se ocorrer erro na conversão, null é retornado.
	 * <p>
	 * @param Number a ser convertido.
	 * @return Numérico convertido.
	 * @since 17/12/2010
	 * @author diego.amaral
	 */

	public static Double toDouble(final Number number){
		return toDouble(number, null);
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Conversão de um número em Integer. Se ocorrer erro na conversão, um valor default é especificado.
	 * <p>
	 * @param Valor a ser convertido.
	 * @param Valor default se não for possível realizar a conversão.
	 * @return Numérico convertido.
	 * @since 14/06/2011
	 * @author diego.amaral
	 */
	public static Double toDouble(final Object number, final Double defaultValue) {
		Double value = defaultValue;
		try{
			value = Double.valueOf(UtilString.toString(number, null));
		}catch(final Exception e){
			if(LOG.isDebugEnabled()){
				LOG.debug(String.format(MSG_TEMPLATE, number, "Double", defaultValue, e.getMessage()));
			}
		}
		return value;
	}

	/**
	 * Compara o valor numérico de dois Number.
	 *
	 * O valor é convertido para Double e realizada a comparação.
	 *
	 * Considerado 0 para valor null. null == 0.
	 *
	 * @param a
	 * @param b
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 3 de mar de 2021
	 */
	public static boolean isValueEquals(Number a, Number b) {

		if(a == b) {
			return true;
		}

		Double aDouble = UtilNumber.toDouble(a, 0D);
		Double bDouble = UtilNumber.toDouble(b, 0D);

		if(aDouble != null && bDouble != null) {
			return aDouble.equals(bDouble);
		}

		return a == b;

	}

}
