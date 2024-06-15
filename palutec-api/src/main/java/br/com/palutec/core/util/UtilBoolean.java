package br.com.palutec.core.util;
/**
 *
 * <P><B>Description :</B><BR>
 * Utilitário de manipulação de boolean.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 * @author Diego C. Amaral - 11/05/2014
 *
 */
public final class UtilBoolean {


	private static final String[] STR_BOOLEAN = "S,SIM,Y,YES,OK,ON,TRUE,1".split(",");

	public static final String BR_BOOL_YES = "S";
	public static final String BR_BOOL_NO = "N";

	private UtilBoolean(){
		//Singleton use
	}
	/**
	 * Verifica se a string é um valor booleano positivo.
	 * @param value - Valor string a ser convertido
	 * @return Valor booleano.
	 * @author diego.amaral
	 */
	public static boolean isTrue(Object value){
		return UtilString.isAnyIgnoreCase(String.valueOf(value), STR_BOOLEAN);
	}
	/**
	 * Retorna S ou N para o booleano.
	 *
	 * @param isTrue
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 14/10/2017
	 */
	public static String toBrBoolean(boolean isTrue){
		return isTrue ? BR_BOOL_YES : BR_BOOL_NO;
	}
}
