package br.com.palutec.core.util;

import org.apache.commons.lang3.ClassUtils;

import br.com.palutec.core.exception.SystemException;


/**
 *
 * <p><b>Description: </b><br>
 * Utilitário de manipulação de Objects.
 * </p>
 * <b>
 * Issues: <br>
 *
 * </b>
 * @author diego.amaral
 * @since 28/06/2011
 * @version
 */
public final class UtilObject {

	private UtilObject() {
		super();
	}

	/**
	 * Verifica se todas os objetos são vazias e/ou nulas.
	 * @param Conjunto de parâmetros de objetos a serem testados.
	 * @return todos são null?
	 * @author diego.amaral
	 */
	public static boolean allNull(final Object... objs) {
		if (objs != null) {
			for (final Object s : objs) {
				if (s != null) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Compara se todos os objetos são iguais (see Object.equals())
	 *
	 * @param objs
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 5 de mar de 2021
	 */
	public static boolean allEquals(final Object ...objs) {
		for(Object a : objs) {
			for(Object b : objs) {
				if(!isEquals(a, b)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se existe algum elemento null.
	 * <p>
	 * @param Conjunto de parâmetros de objetos a serem testados.
	 * @return Algum é null?
	 * @since 09/06/2011
	 * @author diego.amaral
	 */
	public static boolean anyNull(final Object... objs) {
		if (objs != null) {
			for (final Object s : objs) {
				if (s == null) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Verifica se todas as referências dos objetos não são vazias e/ou nulas.
	 * @param Conjunto de parâmetros de objetos a serem testados.
	 * @return Todos não são null?
	 * @author diego.amaral
	 */
	public static boolean allNotNull(final Object... objs) {
		if (objs != null) {
			for (final Object s : objs) {
				if (s == null) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Cast de objetos
	 * <p>
	 * @param Objeto fonte.
	 * @param Classe target.
	 * @return Objeto fonte cast para target.
	 * @since 07/12/2010
	 * @author diego.amaral
	 */
	public static <T> T cast(final Object src, final Class<T> targetClass){
		if(targetClass != null && targetClass.isInstance(src)) {
			return targetClass.cast(src);
		}
		return null;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Verifica se dois objetos são iguais.
	 * Se os dois são null, true é retornado.
	 * Se os dois não são null, A.equals(B) é invocado.
	 * Para qualquer outra situação, é retornado false.	 *
	 * <p>
	 * @param objA
	 * @param objB
	 * @return Todos são iguais?
	 *
	 * @since 07/12/2010
	 * @author diego.amaral
	 */
	public static boolean isEquals(final Object objA, final Object objB){
		if(objA == objB){
			return true;
		}

		if((objA instanceof Number || objA == null) && (objB instanceof Number || objB == null)) {
			return UtilNumber.isValueEquals((Number)objA, (Number)objB);
		}

		if(objA != null && objB != null){
			return objA.equals(objB);
		}

		return false;
	}



	/**
	 * Verifica Se o value é igual (referencia ou equals()) a um dos targets.
	 * @param value
	 * @param targets
	 * @return
	 * @author Diego Amaral
	 */
	public static boolean equalsAny(Object value, Object...targets) {
		for(Object t : targets){
			if(isEquals(value, t)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Oobtém a classe pelo nome (for name)
	 *
	 * @param clazz
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 30 de abr de 2019
	 */
	public static Class<?> forName(String clazz){
		try {
			return ClassUtils.getClass(clazz);
		} catch (Exception e) {
			throw new SystemException(e, "Error on find class %s for name",  clazz);

		}
	}


	public static <T> T firstNotNull(T ...obj) {
		if(obj == null) {
			return null;
		}

		for(T o : obj) {
			if(o != null) {
				return o;
			}
		}
		return null;
	}
}
