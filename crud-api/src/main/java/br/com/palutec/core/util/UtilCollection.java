package br.com.palutec.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import br.com.palutec.core.exception.SystemException;

/**
 *
 * <P><B>Description :</B><BR>
 * Utilitário de manipulação de Collections e suas implementaões.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 * @author Diego C. Amaral - 11/05/2014
 *
 */
public final class UtilCollection {

	private UtilCollection() {
		super();
	}

	/**
	 * Verifica se a coleção é vazia.
	 * @param col - Coleção.
	 * @return é vazia?
	 * @author diego.amaral
	 */
	public static boolean isEmpty(final Collection<?> col) {
		return col == null || col.isEmpty();
	}
	/**
	 * Verifica se a coleção é vazia.
	 * @param col - Coleção.
	 * @return é vazia?
	 * @author diego.amaral
	 */
	public static boolean isEmpty(final Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * Verifica se o map não é vazio..
	 * @param col - Map.
	 * @return Não é vazia?
	 * @author diego.amaral
	 */
	public static boolean isNotEmpty(final Collection<?> col) {
		return !isEmpty(col);
	}
	/**
	 * Verifica se o map é vazio
	 * @param col - Coleção
	 * @return Não é vazia?
	 * @author diego.amaral
	 */
	public static boolean isNotEmpty(final Map<?, ?> col) {
		return !isEmpty(col);
	}
	/**
	 * Retorna a primeira ocorrência da collection. Retorna null se a coleção é vazia.
	 * @param c - Coleção.
	 * @return Primeiro objeto da coleção.
	 * @author diego.amaral
	 */
	public static <T> T getFirst(final Collection<T> col) {
		T value = null;
		if(!isEmpty(col)){
			value = (T) col.toArray()[0];
		}
		return value;
	}
	/**
	 * Retorna a primeira ocorrência do array. Retorna null se o array é vazia.
	 *
	 * @param arr
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 23/03/2018
	 */
	public static <T>T getFirst(T ...arr) {
		return arr != null && arr.length > 0 ? arr[0] : null ;
	}


	/**
	 * Retorna a última ocorrência da collection. Retorna null se a coleção é vazia.
	 * @param c - Coleção.
	 * @return Primeiro objeto da coleção.
	 * @author diego.amaral
	 */
	public static <T> T getLast(final Collection<T> col) {
		T value = null;
		if(!isEmpty(col)){
			value = (T) col.toArray()[col.size()-1];
		}
		return value;
	}


	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Retorna o tamanho da coleção. Se for null, 0 é retornado.
	 * <p>
	 * @param col - Coleção.
	 * @return tamanho da coleção.
	 * @since 05/10/2010
	 * @author diego.amaral
	 */
	public static int size(final Collection<?> col){
		return col == null ? 0 : col.size();
	}
	/**
	 * Retorna o tamanho da coleção. Se for null, 0 é retornado.
	 *
	 * @param col
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 4 de fev de 2021
	 */
	public static int size(Object ...col) {
		return col == null ? 0 : col.length;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Oobtém o objeto de um posição da coleção.
	 * <p>
	 * @param <T>
	 * @param col - Lista de objetos.
	 * @param pos - Posição do índice da coleção.
	 * @return Objeto do índice indicado.
	 * @since 06/06/2011
	 * @author diego.amaral
	 */
	public static <T>T getAt(final List<T> col, final int pos) {
		T element = null;
		if(isNotEmpty(col)){
			element = col.get(pos);
		}
		return element;
	}
	
	public static <T>T getAt(T[] arr, int idx, boolean mandatory){
		if(isNotEmpty(arr) && arr.length > idx) {
			return arr[idx];
		}
		if(mandatory) {
			if(arr == null) {
				throw new NullPointerException("Array is null.");
			}
			if(arr.length == 0) {
				throw new IndexOutOfBoundsException("Array is empty.");
			}
			if(arr.length > idx) {
				throw new IndexOutOfBoundsException(String.format("Array size: %d Requested idx: %d", arr.length, idx));
			}
		}
		return null;
	}
	
	private static boolean isNotEmpty(Object[] arr) {
		return arr != null && arr.length > 0;
	}

	/**
	 * Oobtém uma lista ordenada e com itens únicos. Se lista vazia, retorna a coleção de entrada.
	 * @param col
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>List<T> asOrderedAndUniqueList(Collection<T> col){
		if(!isEmpty(col)){
			Set<T> set = new HashSet<>(col);
			List list = new ArrayList<>(set);
			Collections.sort(list);
			return list;
		}else{
			return new ArrayList<>();
		}
	}
	
	public static <T>List<T> asOrderedList(Collection<T> col){
		if(!isEmpty(col)){
			List list = new ArrayList<>(col);
			Collections.sort(list);
			return list;
		}else{
			return new ArrayList<>();
		}	
	}
	/**
	 * Retorna uma string dos toString dos objetos da lista separados por um separador.
	 * @param list
	 * @param separator
	 * @return Lista separada pelo separador. "" se col == null.
	 */
	public static <T> String asSeparatedString(Collection<T> col, String separator){
		if(isEmpty(col)){
			return UtilString.EMPTY;
		}
		StringBuilder buf = new StringBuilder();
		List<T> list = new ArrayList<>(col);
		for(int i=0; i<list.size(); i++){
			buf.append(String.valueOf(list.get(i)));
			if(i < list.size()-1){
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	/**
	 * Retorna uma string dos toString dos objetos da lista separados por virgula ','.
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String asSeparatedString(List<?> list){
		return asSeparatedString(list, ",");
	}

	/**
	 * Verifica se todos os elementos estão presentes na coleção.
	 * @param col
	 * @param elements
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean containsAll(Collection<T> col, T...elements){
		if(col == null){
			return false;
		}
		return col.containsAll(Arrays.asList(elements));
	}
	/**
	 * Cria um Set indicada e adiciona os objetos.
	 *
	 * @param collectionClazz
	 * @param obj
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 05/04/2016
	 */
	@SafeVarargs
	public static <T> Set<T> asSet(T ...objs) {
		Set<T> instance = new LinkedHashSet<>();
		try {
			for(T obj : objs){
				instance.add(obj);
			}
		} catch (Exception e) {
			throw new SystemException(e, "Error on create collection with single");
		}
		return instance;
	}
	/**
	 * Cria uma List indicada e adiciona os objetos.
	 *
	 * @param objs
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 28/04/2016
	 */
	@SafeVarargs
	public static <T>List<T> asList(T ...objs) {
		List<T> instance = new ArrayList<>();
		try {
			for(T obj : objs){
				instance.add(obj);
			}
		} catch (Exception e) {
			throw new SystemException(e, "Error on create collection with single");
		}
		return instance;
	}

	/**
	 * Varargs to array.
	 *
	 * @param arr
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 19/01/2018
	 */
	@SafeVarargs
	public static <T> T[] toArray(T ...arr){
		return arr;
	}
	
	/**
	 * Divide o array em porções de tamanho x e oobtém a posição desta porção.
	 *
	 * @param list
	 * @param portionSize
	 * @param portion
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 06/09/2018
	 */
	public static <T>List<T> getPortion(List<T> list, int portionSize, int portion){

		int to = portionSize*(portion + 1);
		int from = to - portionSize;
		if(from != to){
			if(isEmpty(list)){
				return list;
			}
			if(from < 0){
				from = 0;
			}
			if(from > list.size()){
				return new ArrayList<>(0);
			}
			if(to > list.size()){
				to = list.size();
			}
		}


		return list.subList(from, to);
	}
	/**
	 * Testa se um array[] é vazio.
	 *
	 * @param params
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 21 de jan de 2021
	 */
	public static boolean isEmpty(Object[] params) {
		return params == null || params.length == 0;
	}

	/**
	 * Processa o foreach se o iterator não for vazio.
	 *
	 * Não falha em null para ambos os parametros.
	 *
	 * @param <T>
	 * @param i
	 * @param action
	 *
	 * @author diego.amaral
	 * @since 9 de abr de 2021
	 */
	public static <T> void forEach(Iterable<T> i, Consumer<T> action) {
		if(UtilObject.allNotNull(i, action)) {
			i.forEach(action);
		}
	}
	/**
	 * Processa o foreach se o map não for vazio.
	 *
	 * Não falha em null para ambos os parametros.
	 *
	 * @param <T>
	 * @param i
	 * @param action
	 *
	 * @author diego.amaral
	 * @since 9 de abr de 2021
	 */
	public static <K, V> void forEach(Map<K, V> map, BiConsumer<? super K, ? super V> action) {
		if(UtilObject.allNotNull(map, action)) {
			map.forEach(action);
		}
	}
	/**
	 * Adiciona um objeto à coleção se não null.
	 * 
	 * @param col
	 * @param o
	 *
	 * @author Diego C. Amaral
	 * @since 30 de abr de 2021
	 */
	public void add(Collection col, Object o) {
		if(col != null) {
			col.add(o);
		}
	}

	public static <T> List<T> getListOrCreate(List<T> list) {
		return list == null ? new LinkedList<>() : list;
	}

	public static boolean containsAny(Object[] arr, Object ...objs) {
		if(arr == null && objs == null) {
			return true;
		}
		if(arr == null || objs == null) {
			return false;
		}
		
		for(Object o : arr) {
			for(Object f : objs) {
				if(o.equals(f)) {
					return true;
				}
			}
		}
		return false;
		
	}

}
