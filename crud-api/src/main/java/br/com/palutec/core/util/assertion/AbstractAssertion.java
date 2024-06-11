package br.com.palutec.core.util.assertion;

import java.util.Collection;
import java.util.Map;

import br.com.palutec.core.util.UtilCollection;
import br.com.palutec.core.util.UtilNumber;
import br.com.palutec.core.util.UtilObject;
import br.com.palutec.core.util.UtilString;
import jakarta.validation.constraints.NotNull;
/**
 *
 * <P><B>Description :</B><BR>
 * Métodos de asserções de código.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 22 de jul de 2020
 */
public abstract class AbstractAssertion {

	/**
	 * Expressão deve ser VERDADEIRA
	 *
	 * @param expression
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 *
	 * @author Diego C. Amaral
	 * @since 22 de jul de 2020
	 */
	public boolean shouldTrue(boolean expression, String errorCodeOrMessage, Object ...messageArgs) {
		if (!expression) {
			whenFail(errorCodeOrMessage, messageArgs);
		}
		return expression;
	}
	/**
	 * Ação realizada quando a asserção falha.
	 *
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 *
	 * @author Diego C. Amaral
	 * @since 21 de jan de 2021
	 */
	public abstract void whenFail(String errorCodeOrMessage, Object ...messageArgs);

	/**
	 * Expressão deve ser FALSA
	 *
	 * @param expression
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 *
	 * @author Diego C. Amaral
	 * @since 22 de jul de 2020
	 */
	public boolean shouldFalse(boolean expression, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(expression == false, errorCodeOrMessage, messageArgs);
	}

	/**
	 * A coleção não deve ser blank (vazia, null ou só espaços).
	 * Se sim, emite uma exceção badrequest.
	 *
	 * @param o
	 * @param errorCodeOrMessage
	 *
	 * @author Diego C. Amaral
	 * @since 22 de jul de 2020
	 */
	public boolean notBlank(String o, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(!UtilString.isBlank(o), errorCodeOrMessage, messageArgs);
	}
	/**
	 * A coleção deve ser blank (vazia, null ou só espaços).
	 * Se sim, emite uma exceção badrequest.
	 *
	 * @param o
	 * @param errorCodeOrMessage
	 *
	 * @author Diego C. Amaral
	 * @since 22 de jul de 2020
	 */
	public boolean shouldBlank(String o, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(UtilString.isBlank(o), errorCodeOrMessage, messageArgs);
	}

	/**
	 * Objeto não pode ser null.
	 *
	 * @param obj
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 *
	 * @author Diego C. Amaral
	 * @since 23 de jul de 2020
	 */
	public boolean notNull(Object obj, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(obj != null, errorCodeOrMessage, messageArgs);
	}
	/**
	 * Numero deve ser nao null e maior q ZERO.
	 *
	 * @param number
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 *
	 * @author Diego C. Amaral
	 * @since 23 de jul de 2020
	 */
	public boolean greaterZero(Number number, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(UtilNumber.toLong(number, -1L) > 0L, errorCodeOrMessage, messageArgs);
	}
	/**
	 * Número deve ser null ou ZERO
	 *
	 * @param number
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 4 de mar de 2021
	 */
	public boolean nullOrZero(@NotNull Number number, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(UtilNumber.toDouble(number, 0D) == 0D, errorCodeOrMessage, messageArgs);
	}

	/**
	 * Coleção não deve ser vazia.
	 *
	 * @param col
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 *
	 * @author Diego C. Amaral
	 * @since 23 de jul de 2020
	 */
	public boolean notEmpty(Collection<?> col, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(!UtilCollection.isEmpty(col), errorCodeOrMessage, messageArgs);
	}
	/**
	 * Array não deve ser vazio.
	 *
	 * @param col
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 *
	 * @author Diego C. Amaral
	 * @since 23 de jul de 2020
	 */	
	public boolean notEmpty(Object[] arr, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(!UtilCollection.isEmpty(arr), errorCodeOrMessage, messageArgs);
	}
	/**
	 * Coleção deve ser vazia.
	 *
	 * @param col
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 16 de mar de 2021
	 */
	public boolean shouldEmpty(Collection<?> col, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(UtilCollection.isEmpty(col), errorCodeOrMessage, messageArgs);
	}


	/**
	 * Map não deve ser empty ou null.
	 *
	 * @param map
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 22 de fev de 2021
	 */
	public boolean notEmpty(Map<?, ?> map, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(!UtilCollection.isEmpty(map), errorCodeOrMessage, messageArgs);
	}
	/**
	 * Valida se os objetos são iguais.
	 * @see br.com.acecon.core.util.UtilObject.isEquals
	 * 
	 * @param objA
	 * @param objB
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 * @return
	 *
	 * @author diego.amaral
	 * @since 14 de mai de 2021
	 */
	public boolean shouldEquals(Object objA, Object objB, String errorCodeOrMessage, Object ...messageArgs) {
		return shouldTrue(UtilObject.isEquals(objA, objB), errorCodeOrMessage, messageArgs);
	}
	/**
	 * Valida se o número esperado é menor ao atual.
	 * 
	 * @param expected
	 * @param actual
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 *
	 * @author diego.amaral
	 * @since 14 de mai de 2021
	 */
	public void lessThan(Number expected, Number actual, String errorCodeOrMessage, Object ...messageArgs) {
		shouldTrue(UtilNumber.toDouble(actual, 0D) < UtilNumber.toDouble(expected, 0D), errorCodeOrMessage, messageArgs);
	}
	/**
	 * Valida se o número esperado é menor ou igual ao atual.
	 * 
	 * @param expected
	 * @param actual
	 * @param errorCodeOrMessage
	 * @param messageArgs
	 *
	 * @author diego.amaral
	 * @since 14 de mai de 2021
	 */
	public void lessOrEquals(Number expected, Number actual, String errorCodeOrMessage, Object ...messageArgs) {
		shouldTrue(UtilNumber.toDouble(actual, 0D) <= UtilNumber.toDouble(expected, 0D), errorCodeOrMessage, messageArgs);
	}
	
}
