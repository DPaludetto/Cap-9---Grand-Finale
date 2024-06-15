package br.com.palutec.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.WordUtils;

import br.com.palutec.core.exception.SystemException;


/**
 * <p>
 * <b>Description: </b><br>
 * Classe Utilitária para Strings.
 * </p>
 * <b> Issues: <br>
 * </b>
 * @author Diego Amaral
 * @since 04/11/2009
 * @version 1.0
 */
public final class UtilString {


	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	public static final String EMPTY = "";
	public static final String SPACE = " ";

	public static final String LS = "\n";

	public static final String ASPA = "'";
	public static final String COMMA = ",";
	public static final String COLON = ":";
	public static final String SEMICOLON = ";";
	public static final String DASH = "-";
	public static final String SCAPE_R = "\r";
	public static final String PARENTESE_DIR = ")";
	public static final String SLASH = "/";
	public static final String PUNCT = ".";
	public static final String LEFT_BRACE = "{";

	/**
	 * Língua do sistema.
	 */
	public static final String LANG_PT = "pt";
	/**
	 * Região do sistema.
	 */
	public static final String COUNTRY_BR = "BR";


	/**
	 *
	 */
	private UtilString() {
		super();
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Retorna uma lista do tipo Integer.
	 * </p>
	 * @author mauricio.araujo
	 * @since 03/11/2009
	 * @param String lista.
	 * @param Separador da lista.
	 * @return List<Integer>
	 */
	public static List<Integer> buildIntegerList(final String value, boolean addEmptyValues, final String ...delimiters) {
		return buildList(value, e -> Integer.parseInt(e.trim()), addEmptyValues, delimiters);
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Cria uma lista do tipo Long.
	 * </p>
	 * @author mauricio.araujo
	 * @since 03/11/2009
	 * @param String com tokens.
	 * @param Token.
	 * @return List<Long>
	 */
	public static List<Long> buildLongList(final String value, boolean addEmptyValues, final String ...delimiters) {
		return buildList(value, e -> Long.parseLong(e.trim()), addEmptyValues, delimiters);
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Cria uma lista do tipo String.
	 * </p>
	 * @author mauricio.araujo
	 * @since 03/11/2009
	 * @param String com tokens.
	 * @param Token.
	 * @return List<Long>
	 */
	public static List<String> buildStringList(final String value, boolean addEmptyValues, final String ...delimiters) {
		return buildList(value, e -> e, addEmptyValues, delimiters);
	}

	/**
	 * Constrói uma lista com o separador padrão ",".
	 *
	 * @param value
	 * @param addEmptyValues
	 * @param delimiters
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 22/06/2016
	 */
	public static List<String> buildDefaultStringList(final String value, boolean addEmptyValues) {
		return UtilString.buildStringList(value, addEmptyValues, ",", "\n", ";");
	}

	/**
	 * Constrói uma lista com qualquer um dos delimitadores
	 *
	 * @param tokenizedString
	 * @param converterClazz
	 * @param delimiters
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 07/04/2016
	 */
	public static <T> List<T> buildList(String tokenizedString, BuilderListConverter<T> converter, boolean addEmptyValues, String ...delimiters){
		List<T> list = new ArrayList<>();

		if(isEmpty(tokenizedString)){
			return list;
		}

		StringBuilder delimiterRegex = new StringBuilder("[");
		for(String delimiter : delimiters){
			delimiterRegex.append(delimiter).append("|");
		}
		delimiterRegex.deleteCharAt(delimiterRegex.length()-1);
		delimiterRegex.append("]");

		String[] splitted = tokenizedString.split(trim(delimiterRegex.toString()));

		for(String value : splitted){
			if(addEmptyValues || !isBlank(value)){
				list.add(converter.convert(value));
			}
		}

		return list;
	}
	/**
	 *
	 * <P><B>Description :</B><BR>
	 *  Comportamento de conversão para montagem de Collection<String>.
	 * </P>
	 * <P>
	 * <B>
	 * Issues : <BR>
	 * None
	 * </B>
	 *
	 * @author Diego C. Amaral
	 * @since 03/01/2018
	 * @param <T>
	 */
	public interface BuilderListConverter<T> {
		T convert(String value);
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Verifica se o objeto String é vazio ou nulo.
	 * </p>
	 * @author mauricio.araujo
	 * @since 03/11/2009
	 * @param string
	 * @return é vazia?
	 */
	public static boolean isEmpty(final String string) {
		return string == null || string.length() == 0;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se a string é vazia ou apenas preenchida por espaços em branco.
	 * <p>
	 * @param String.
	 * @return é vazia ou possui somente espaços?
	 * @since 08/12/2010
	 * @author diego.amaral
	 */
	public static boolean isBlank(final String str){
		boolean value = true;
		if (!isEmpty(str)){
			for (int i = 0; i < str.length(); ++i){
				if (!Character.isWhitespace(str.charAt(i))){
					value = false;
				}
			}
		}
		return value;
	}

	/**
	 * Retira todos os caracteres da string. Utilizado para retirar máscaras
	 * para persistância no banco.
	 *
	 * @param string
	 * @return Nova String com caracteres não numáricos removidos.
	 * @autor diego.amaral
	 */
	public static String removeChars(final String string) {
		String ret = null;
		if (!isEmpty(string)) {
			final StringBuilder buff = new StringBuilder();
			for (final char c : string.toCharArray()) {
				if (Character.isDigit(c)) {
					buff.append(c);
				}
			}
			ret = buff.toString();
		}
		return ret;
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Método que recebe um valor String e retorna somente caracteres letras.
	 * <p>
	 * @param string
	 *        .
	 * @return String
	 * @since 18/06/2010
	 * @author regis.rocha
	 */
	public static String apenasLetras(final String string) {
		String letter = null;
		if (!isEmpty(string)) {
			final StringBuilder buff = new StringBuilder();
			for (final char c : string.toCharArray()) {
				if (Character.isLetter(c)) {
					buff.append(c);
				}
			}
			letter = buff.toString();
		}
		return letter;
	}

	/**
	 * Método que recebe um valor String e retorna somente os scaracteres numáricos.
	 *
	 * @param string
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 30/11/2017
	 */
	public static String apenasNumeros(final String string) {
		String letter = null;
		if (!isEmpty(string)) {
			final StringBuilder buff = new StringBuilder();
			for (final char c : string.toCharArray()) {
				if (Character.isDigit(c)) {
					buff.append(c);
				}
			}
			letter = buff.toString();
		}
		return letter;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 *
	 * <p>
	 * @param value
	 * @return
	 * @since 12/05/2011
	 * @author diego.amaral
	 */
	public static String toProperCase(final String value){
		String retValue = null;
		if(value != null){
			final String[] words = value.split("\\s");
			final StringBuilder buff = new StringBuilder();
			for(final String word : words){
				buff.append(WordUtils.capitalizeFully(word));
				buff.append(' ');
			}
			retValue = buff.toString();
		}
		return retValue;
	}

	/**
	 * <p>
	 * <b>Description:</b><br/>
	 * Transforma uma string no padrão camelCase removendo os espaços e caracteres '_'.
	 * <p>
	 * @param value String a ser tranformada.
	 * @return String em camelCase (sem espaço e '_').
	 * @since 16/07/2010
	 */
	public static String toCamelCase(final String value) {
		String result =
			WordUtils.capitalizeFully(value, '_', ' ').replaceAll("[ _]", UtilString.EMPTY);
		result = toLower(result.substring(0, 1));
		return result == null ? null : result.concat(result.substring(1));
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Transforma toda a string em upper case tratando a internacionalização
	 * <p>
	 * @param toUpper
	 * @return
	 * @since 07/08/2010
	 * @author diego.amaral
	 */
	public static String toUpper(final String toUpper){
		return toUpper == null ? null : toUpper.toUpperCase(getDefaultLocale());
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Retorna um array lowercase de todas as strings do array.
	 * <p>
	 * @param toUpper
	 * @since 25/06/2011
	 * @author diego.amaral
	 */
	public static String[] toUpper(final String[] toUpper){
		String[] tmpValue = null;
		if(toUpper != null){
			tmpValue = new String[toUpper.length];
			for(int i=0; i<toUpper.length; ++i){
				tmpValue[i] = toUpper(toUpper[i]);
			}
		}
		return tmpValue;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Transforma toda a string em lower case tratando a internacionalização
	 * <p>
	 * @param toUpper
	 * @return
	 * @since 07/08/2010
	 * @author diego.amaral
	 */
	public static String toLower(final String toLower){
		return toLower == null ? null : toLower.toLowerCase(getDefaultLocale());
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Retorna um array lowercase de todas as strings do array.
	 * <p>
	 * @param toUpper
	 * @since 25/06/2011
	 * @author diego.amaral
	 */
	public static String[] toLower(final String[] toLower){
		String[] tmpValue = null;
		if(toLower != null){
			tmpValue = new String[toLower.length];
			for(int i=0; i<toLower.length; ++i){
				tmpValue[i] = toLower(toLower[i]);
			}
		}
		return tmpValue;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Os args são convertidos em string (String.valueOf()). Se todos puderem ser convertidos para
	 * números, é retornado true.
	 * <p>
	 * @param str
	 * @return
	 * @since 18/08/2010
	 * @author diego.amaral
	 */
	public static boolean isAllDigits(final Object... str){
		boolean value = true;
		for(final Object objStr : str){
			if(!NumberUtils.isDigits(String.valueOf(objStr))){
				value = false;
			}
		}
		return value;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Limita o tamanho da String. Se um numero negativo de tamanho for informado,
	 * é retornado a mesma referência de String.
	 * <p>
	 * @param str
	 * @param max
	 * @return
	 * @since 27/08/2010
	 * @author diego.amaral
	 */
	public static String limit(final String str, final int max){
		String limitedString = str;
		if(str != null && max >= 0 && str.length() > max) {
			limitedString = str.substring(0, max);
		}
		return limitedString;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Retorna se as formas String de dois objetos são iguais.
	 * <p>
	 * @param strA
	 * @param strB
	 * @return
	 * @since 01/09/2010
	 * @author diego.amaral
	 */
	private static boolean isEquals(final Object strA, final Object strB){
		boolean value = false;
		if(strA == null && strB == null){
			value = true;
		}else{
			value = String.valueOf(strA).equals(String.valueOf(strB));
		}
		return value;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se todos as strings são iguais ignorando o case de cada string.
	 * <p>
	 * @param strings
	 * @return
	 * @since 08/12/2010
	 * @author diego.amaral
	 */
	public static boolean isEqualsIgnoreCase(final String ...strings){

		boolean value = true;

		final String tst = strings[0];
		for(final String str : strings){
			if(tst != null && !tst.equalsIgnoreCase(str)){
				value = false;
			}
			if(tst == null && str != null){
				value = false;
			}
		}
		return value;

	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Retorna se as formas String de vários objetos são iguais.
	 * <p>
	 * @param strA
	 * @param strB
	 * @return
	 * @since 01/09/2010
	 * @author diego.amaral
	 */
	public static boolean isEquals(final Object ...strs){
		boolean value = true;
		final String str = String.valueOf(strs[0]);
		for(final Object obj : strs){
			if(!isEquals(str, obj)){
				value = false;
			}
		}
		return value;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica o tamanho da String. Se a string for null, -1 é retornado.
	 * <p>
	 * @param string
	 * @return
	 * @since 27/11/2010
	 * @author diego.amaral
	 */
	public static long size(final String string){
		long size = -1;
		if(string != null){
			size = string.length();
		}
		return size;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Concatena a forma String dos objetos.
	 * <p>
	 * @param obj
	 * @return
	 * @since 07/12/2010
	 * @author diego.amaral
	 */
	public static String concat(final Object...obj){
		final StringBuilder buff = new StringBuilder();
		for(final Object element : obj){
			if(element instanceof char[]){
				buff.append((char[])element);
				continue;
			}

			buff.append(element);
		}
		return buff.toString();
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se a string contém todos os valores de um parâmetro ignorando o case.
	 * <p>
	 * @param value
	 * @param contains
	 * @return
	 * @since 25/06/2011
	 * @author diego.amaral
	 */
	public static boolean containsAllIgnoreCase(final String value, final String ...contains){
		return containsAll(value, toLower(contains));
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se a string contém todos os valores de um parâmetro.
	 * <p>
	 * @param value
	 * @param contains
	 * @return
	 * @since 25/06/2011
	 * @author diego.amaral
	 */
	public static boolean containsAll(final String value, final String ...contains){
		boolean contain = true;
		for(final String test : contains){
			if(!value.contains(toUpper(test))){
				contain = false;
				break;
			}
		}

		return contain;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se a string contém alguns dos valores ignorando o case.
	 * <p>
	 * @param value
	 * @param contains
	 * @return
	 * @since 25/06/2011
	 * @author diego.amaral
	 */
	public static boolean containsAnyIgnoreCase(final String value, final String ...contains){
		return containsAny(toUpper(value), toUpper(contains));
	}
	private static boolean containsAnyIgnoreCase(String str, List<String> anyList) {
		if(UtilCollection.isEmpty(anyList)) {
			return false;
		}
		return containsAnyIgnoreCase(str, anyList.toArray(new String[anyList.size()]));
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se a string contém algum dos valores.
	 * <p>
	 * @param value
	 * @param contains
	 * @return
	 * @since 25/06/2011
	 * @author diego.amaral
	 */
	public static boolean containsAny(final String value, final String ...contains){
		boolean contain = false;

		if(value == null) {
			return false;
		}
		if(contains == null) {
			return false;
		}
		for(final String test : contains){
			if(!isEmpty(test) && value.contains(test)){
				contain = true;
				break;
			}
		}

		return contain;
	}
	
	public static boolean containsAny(final String value, final List<String> list){
		return containsAny(value, list.toArray(new String[list.size()]));
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Oobtém o índice da primeira ocorrência do parâmetro ignorando o case na string.
	 * <p>
	 * @param string - String a ser verificada
	 * @param match - Ocorrência procurada
	 * @return índice da primeira ocorrência. Se não encontrado, retorna -1.
	 * @since 15/04/2011
	 * @author diego.amaral
	 */
	public static int indexOfIgnoreCase(final String string, final String match) {
		int index = -1;
		if(!isEmpty(string) && !isEmpty(match)){
			index = toUpper(string).indexOf(toUpper(match));
		}
		return index;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Remove quebra de linha e tabulaões de string.
	 * <p>
	 * @param str
	 * @return
	 * @since 10/05/2011
	 * @author diego.amaral
	 */
	public static String plain(final String str){
		String value = str;
		if(!isEmpty(str)){
			value = str.replaceAll("\\s+", " ").trim();
		}
		return value;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se a string em teste é qualquer uma das strings.
	 * <p>
	 * @param string
	 * @param inStrings
	 * @return
	 * @since 10/05/2011
	 * @author diego.amaral
	 */
	public static boolean isAny(final String string, final String ...inStrings) {
		boolean test = false;

		if(inStrings == null && string ==null){
			test = true;
		}else{
			if(inStrings != null){
				for(final String strTest : inStrings){
					if(isEquals(strTest, string)){
						test = true;
						break;
					}
				}
			}
		}
		return test;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se a string em teste é qualquer uma das strings ignorando o case.
	 * <p>
	 * @param string
	 * @param inStrings
	 * @return
	 * @since 10/05/2011
	 * @author diego.amaral
	 */
	public static boolean isAnyIgnoreCase(final String string, final String ...inStrings) {
		boolean test = false;

		if(inStrings == null && string ==null){
			test = true;
		}else{
			if(inStrings != null){
				for(final String strTest : inStrings){
					if(isEqualsIgnoreCase(strTest, string)){
						test = true;
						break;
					}
				}
			}
		}
		return test;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se a string em teste não é qualquer uma das strings.
	 * <p>
	 * @param string
	 * @param inStrings
	 * @return
	 * @since 10/05/2011
	 * @author diego.amaral
	 */
	public static boolean isNotAny(final String string, final String ...inStrings) {
		boolean test = true;

		if(inStrings == null && string ==null){
			test = false;
		}else{
			if(inStrings != null){
				for(final String strTest : inStrings){
					if(isEquals(strTest, string)){
						test = false;
						break;
					}
				}
			}
		}
		return test;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Substitui os caracteres &lt; e &gt; gerados por parsers html para < e > respectivamente.
	 * <p>
	 * @param string
	 * @return
	 * @since 12/05/2011
	 * @author diego.amaral
	 */
	public static String restoreHtmlTagsDelimiter(String value){
		if(!isEmpty(value)){
			value = UtilString.replaceAll(value, "&lt;", "<");
			value = UtilString.replaceAll(value, "&gt;", ">");
		}
		return value;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Faz o compare entre duas string testando se a nulidade destas.
	 * <p>
	 * @param strA
	 * @param strB
	 * @return
	 * @since 18/05/2011
	 * @author diego.amaral
	 */
	public static int compareTo(final String strA, final String strB){
		int value = 0;
		if(UtilObject.allNotNull(strA,strB)){
			value = strA.compareTo(strB);
		}
		if(strA == null && strB != null){
			value = -1;
		}
		if(strA != null && strB == null){
			value = 1;
		}
		return value;
	}

	/**
	 * Retorna a string caso o valor do obj é branco, null...
	 *
	 * @param value
	 * @param ifBlank
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 04/10/2017
	 */
	public static String ifBlank(Object value, String ifBlank){
		String returnValue = toString(value, ifBlank);
		return isBlank(returnValue) ? ifBlank : returnValue;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Invoca o toString() do objeto. Se este for null, o valor de parametro é retornado.
	 * <p>
	 * @param value
	 * @param ifNullValue
	 * @return
	 * @since 06/06/2011
	 * @author diego.amaral
	 */
	public static String toString(final Object value, final String ifNullValue){
		String returnValue = ifNullValue;
		if(value != null){
			returnValue = value.toString();
		}
		return returnValue;
	}
	/**
	 * Invoca o toString() do objeto. Se este for null, o valor "" é retornado.
	 * @param value
	 * @return
	 */
	public static String toStringEmptyIfNull(Object value) {
		return toString(value, EMPTY);
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Caractere (string) de uma posição.
	 * <p>
	 * @param value
	 * @param pos
	 * @return
	 * @since 08/06/2011
	 * @author diego.amaral
	 */
	public static String charAt(final String value, final int pos){
		String returnChar = null;
		if(value != null){
			returnChar = String.valueOf(value.charAt(pos));
		}
		return returnChar;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se existe pelo menos uma string blank.
	 * <p>
	 * @param itens
	 * @return
	 * @since 08/06/2011
	 * @author diego.amaral
	 */
	public static boolean anyBlank(final String ...itens){
		boolean any = false;
		for(final String item : itens ){
			if(isBlank(item)){
				any = true;
				break;
			}
		}
		return any;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se todas as strings são blank.
	 * <p>
	 * @param itens
	 * @return
	 * @since 08/06/2011
	 * @author diego.amaral
	 */
	public static boolean allBlank(final String ...itens){
		boolean any = true;
		for(final String item : itens ){
			if(!isBlank(item)){
				any = false;
				break;
			}
		}
		return any;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Oobtém um array de string a partir de uma list.
	 * É invocado o toString() de cada item da lista e armazenado no array de String novo.
	 * <p>
	 * @param list
	 * @return
	 * @since 11/06/2011
	 * @author diego.amaral
	 */
	public static String[] toStringArray(final List<?> list){
		String [] array = null;
		if(list != null){
			final int listSize = list.size();
			array = new String[listSize];
			final Object []objArgs = new Object[listSize];

			list.toArray(objArgs);

			for(int i=0; i<listSize; ++i){
				final Object item = objArgs[i];
				array[i] = item != null ? item.toString() : null;
			}

		}
		return array;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Remove todos os caracteres de uma string.
	 * <p>
	 * @param str
	 * @param sequence
	 * @return
	 * @since 07/07/2011
	 * @author diego.amaral
	 */
	public static String removeAll(final String str, final String sequence){
		String value = null;
		if(str != null){
			value = str.replaceAll(sequence, EMPTY);
		}
		return value;
	}
	/**
	 * Invoca o String.remove() testando null para str.
	 *
	 * @param str
	 * @param sequence
	 * @return
	 *
	 * @author diego.amaral
	 * @since 9 de abr de 2021
	 */
	public static String remove(String str, String sequence) {
		return str != null ? str.replace(sequence, EMPTY) : str;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Remove a última ocorrência na string.
	 * <p>
	 * @param str
	 * @param sequence
	 * @return
	 * @since 07/07/2011
	 * @author diego.amaral
	 */
	public static String removeLast(final String str, final String sequence){

		String value = null;
		if(str != null){
			final int pos = str.lastIndexOf(sequence);
			final StringBuilder buff =  new StringBuilder(str);
			buff.delete(pos, pos+sequence.length());
			value = buff.toString();
		}
		return value;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Remove da string todos os caracteres que não fazem parte de [a-Z]|[0-9]
	 * <p>
	 * @param value
	 * @return
	 * @since 16/08/2011
	 * @author diego.amaral
	 */
	public static String onlyAlphaNumeric(final String value){
		if(value == null) {
			return null;
		}
		String ret = value;
		if(!UtilString.isBlank(ret)){
			final StringBuilder alphaNumeric = new StringBuilder();
			for(final char ch : value.toCharArray()){
				if(CharUtils.isAsciiAlphanumeric(ch)){
					alphaNumeric.append(ch);
				}
			}
			ret = alphaNumeric.toString();
		}
		return ret;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Substitui a primeira ocorrência de uma string ignorando o case.
	 * <p>
	 * @param buff - String.
	 * @param value - Valor a ser susbtituído.
	 * @param pattern - Novo valor.
	 * @return
	 * @since 23/05/2011
	 * @author diego.amaral
	 */
	public static String relpaceIfMatchIgnoreCase(final String str, final String value, final String pattern){
		final StringBuilder buff = new StringBuilder(str);
		final int index = UtilString.indexOfIgnoreCase(buff.toString(), pattern);
		if(index != -1){
			buff.replace(index, index+pattern.length(), value);
		}
		return buff.toString();
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Remove caracteres especiais
	 * <p>
	 * @param toReplace
	 * @return
	 * @since 17/09/2011
	 * @author diego.amaral
	 */
	public static String replaceAccents(final String toReplace){
		String replacedString = toReplace;
		if(!UtilString.isBlank(toReplace)){
	         replacedString = toReplace.replaceAll("[\u00E3\u00E2\u00E0\u00E1\u00E4]", "a")
                     .replaceAll("[\u00EA\u00E8\u00E9\u00EB]", "e")
                     .replaceAll("[\u00EE\u00EC\u00ED\u00EF]", "i")
                     .replaceAll("[\u00F5\u00F4\u00F2\u00F3\u00F6]", "o")
                     .replaceAll("[\u00FB\u00FA\u00F9\u00FC]", "u")
                     .replaceAll("[\u00C3\u00C2\u00C0\u00C1\u00C4]", "A")
                     .replaceAll("[\u00CA\u00C8\u00C9\u00CB]", "E")
                     .replaceAll("[\u00CE\u00CC\u00CD\u00CF]", "I")
                     .replaceAll("[\u00D5\u00D4\u00D2\u00D3\u00D6]", "O")
                     .replaceAll("[\u00DB\u00D9\u00DA\u00DC]", "U")
                     .replace('\u00E7', 'c')
                     .replace('\u00C7', 'C')
                     .replace('\u00F1', 'n')
                     .replace('\u00D1', 'N');
		}
		return replacedString;
	}

	public static String replaceAll(String str, String regex, String replacement) {
		return isBlank(str) ? str : str.replaceAll(regex, replacement);
	}

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
		return new Locale(LANG_PT, COUNTRY_BR);
	}

	/**
	 * Verifica se uma string termina com uma das strings dos parametro.
	 * @param string
	 * @param ends
	 * @return
	 * @author Diego Amaral
	 */
	public static boolean endsWithAny(String string, String ...ends) {

		if(string != null){
			for(String end : ends){
				if(string.endsWith(end)){
					return true;
				}
			}
		}

		return false;
	}
	/**
	 * Verifica se uma string inicia com uma das strings dos parametro.
	 * @param string
	 * @param start
	 * @return
	 * @author Diego Amaral
	 */
	public static boolean startsWithAny(String string, String ...start) {

		if(string != null){
			for(String end : start){
				if(string.startsWith(end)){
					return true;
				}
			}
		}

		return false;
	}
	/**
	 * Verifica se uma string inicia com uma das strings dos parametro ignorando o case.
	 *
	 * @param string
	 * @param start
	 * @return
	 *
	 * @author diego.amaral
	 * @since 8 de abr de 2021
	 */
	public static boolean startsWithAnyIgnoreCase(String string, String ...start) {
		if(string != null){
			string = toUpper(string);
			for(String end : start){
				if(string.startsWith(toUpper(end))){
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Aplica trim a string.
	 * @param string
	 * @return
	 * @author Diego Amaral
	 */
	public static String trim(String string) {
		return string != null ? string.trim() : null;
	}
	/**
	 * Retorna os últimos n caracteres da string.
	 * @param string
	 * @param n
	 * @return
	 *
	 * @author Diego C. Amaral - 21/07/2014
	 */
	public static String lastChars(String string, int n) {
		String value = string;
		long len = UtilString.size(string);
		if(string != null && len > string.length() - n && len > n){
			value = string.substring(string.length() - n);
		}
		return value;
	}

	/**
	 * Constrói uma lista com os toString() de cada elemento SEM o [ e ] de indicação de collections.
	 *
	 * @param col
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 12/01/2018
	 */
	public static String toStringList(Collection<?> col, String listSeparator){
		StringBuilder buff = new StringBuilder();
		if(UtilString.isEmpty(listSeparator)){
			throw new SystemException("Um separador de lista deve ser informado.");
		}
		if(!UtilCollection.isEmpty(col)){
			for(Object o : col){
				buff.append(o);
				buff.append(listSeparator);
			}
			buff.deleteCharAt(buff.length()-1);
		}
		return buff.toString();
	}
	/**
	 * Constrói uma lista separada por , com os toString() de cada elemento SEM o [ e ] de indicação de collections.
	 *
	 * @param col
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 12/01/2018
	 */
	public static String toStringList(Collection<?> col){
		return toStringList(col, ",");
	}

	/**
	 * Verifica se alguma string eh blank.
	 *
	 * @param strArr
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 25/06/2018
	 */
	public static boolean isAnyNotBlank(String ...strArr) {
		if(strArr != null){
			for(String s : strArr){
				if(!isBlank(s)){
					return true;
				}
			}
		}
		return false;
	}

	public static String firstNotBlank(String ...values) {
		for(String v : values) {
			if(!isBlank(v)) {
				return v;
			}
		}
		return null;
	}

	/**
	 * Verifica se alguma string nao eh blank.
	 *
	 * @param strArr
	 * @return
	 *
	 * @author Renato Sukomine
	 * @since 28/01/2021
	 */
	public static boolean isAnyBlank(String ...strArr) {
		if(strArr != null){
			for(String s : strArr){
				if(isBlank(s)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static List<String> findAllMatches(String str, String match, int group) {
		Pattern p = Pattern.compile(match);
		Matcher m = p.matcher(str);
		List<String> all = new LinkedList<>();
		while(m.find()) {
			all.add(m.group(group));
		}
		return all;
	}

	public static boolean containsAnyIgnoreCaseAndSpecials(String str, String...any) {
		List<String> anyStr = Arrays.asList(any).stream().map(e -> removeAll(replaceAccents(e), "[^a-zA-Z0-9]")).collect(Collectors.toList());
		return containsAnyIgnoreCase(removeAll(replaceAccents(str), "[^a-zA-Z0-9]"), anyStr);
	}
	
	public static boolean containsAnyIgnoreCaseAndSpecials(String str, List<String> anyList) {
		if(UtilCollection.isEmpty(anyList)) {
			return false;
		}
		return containsAnyIgnoreCaseAndSpecials(str, anyList.toArray(new String[anyList.size()])); 
	}
	
	
}
