package br.com.palutec.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.util.ClassUtils;

import br.com.palutec.core.exception.SystemException;
import br.com.palutec.core.util.assertion.SystemAssertion;


/**
 *
 * <P><B>Description :</B><BR>
 * 	Utilitário de java Reflection.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 01/08/2017
 */

public class UtilReflection {

	private static final Logger log = LoggerFactory.getLogger(UtilReflection.class);

	private UtilReflection(){}//singleton use

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Cria uma nova instância a partir de um Class.
	 * <p>
	 * @param Tipo da classe.
	 * @return Instância da classe.
	 * @since 24/05/2011
	 * @author diego.amaral
	 */
	public static <T> T newInstance(final Class<T> clazz, Object... args){
		T instance = null;
		try{
			instance = ConstructorUtils.invokeConstructor(clazz, args);
		}catch(final Exception e){
			throw new SystemException(e, "Error on instantiate class with args constructor '%s'", clazz);
		}
		return instance;
	}
	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Cria uma nova instância a partir de um Class.
	 * <p>
	 * @param Tipo da classe.
	 * @return Instância da classe.
	 * @since 24/05/2011
	 * @author diego.amaral
	 */
	public static Object newInstance(final String clazzName, Object... args){
		try{
			if(UtilCollection.isEmpty(args)) {
				return findClass(clazzName).newInstance();
			}
			return ConstructorUtils.invokeConstructor(findClass(clazzName), args);
		}catch(final Exception e){
			throw new SystemException(e, "Error on instantiate class '%s' with args constructor.", clazzName);
		}
	}

	/**
	 * Procura o field na classe e em suas classes em que ela herda.
	 *
	 * @param clazz
	 * @param name
	 * @return
	 * @throws Exception
	 *
	 * @author Diego C. Amaral
	 * @since 01/08/2017
	 */
	public static Field findField(Class<?> clazz, String fieldName){
		return FieldUtils.getDeclaredField(clazz, fieldName, true);
	}

	/**
	 *
	 * <p><b>Description:</b><br/>
	 * Cria uma nova instância de uma classe a partir do construtor padrão.
	 * <p>
	 * @param Descrição da classe. ex: "br.x.z.ClasseA"
	 * @return Instância da classe.
	 * @since 04/05/2011
	 * @author diego.amaral
	 */
	public static Object newInstanceNoArgs(final String clazz){
		try{
			return findClass(clazz).newInstance();
		}catch(final Exception e){
			throw new SystemException(e, "Error on instantiate '%s' without args constructor.", clazz);
		}
	}


	public static List<String> getClassNamesFromPackage(String packageName){
		List<String> clazzList = new ArrayList<>();
		try {
			ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
			provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
			Set<BeanDefinition> classes = provider.findCandidateComponents(packageName);

			for (BeanDefinition bean: classes) {
			    clazzList.add(bean.getBeanClassName());
			}
		}catch(Exception e) {
			throw new SystemException(e, "Error on trying find classes from package %s", packageName);
		}
		return clazzList;
	}

	/**
	 * Obtém o valor do field de uma instância de objeto.
	 * @param obj
	 * @param field
	 * @return
	 *
	 * @author Diego C Amaral
	 */
	public static Object getFieldValue(Object obj, Field field) {
		Object value = null;
		try {
			value = FieldUtils.readField(field, obj, true);
		}catch(Exception e) {
			throw new SystemException(e, "Error on get value field %s from object %s.", field, obj);
		}
		return value;
	}

	public static int findIntStaticField(String className, String fieldName) {

		try {
			Class<?> clazz = findClass(className);
			Field field = clazz.getDeclaredField(fieldName);
			return field.getInt((Object) null);
		} catch (Exception e) {
			throw new SystemException(e, "Error on get int static field %s from class %s", fieldName, className);
		}
	}

	public static Class<?> findClass(String name) {
		Class<?> result = findClass(name, UtilReflection.class.getClassLoader(), true);
		if (result == null) {
			result = findClass(name, Thread.currentThread().getContextClassLoader(), true);
			if (result == null) {
				result = findClass(name, ClassLoader.getSystemClassLoader(), false);
			}
		}

		return result;
	}

	public static ClassLoader defaultClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static Class<?> findClass(String name, ClassLoader classLoader) {
		return findClass(name, classLoader, false);
	}

	private static Class<?> findClass(String name, ClassLoader classLoader, boolean returnNull) {
		Class<?> clazz = null;

		try {
			clazz = ClassUtils.forName(name, classLoader);
		} catch (Exception e) {
			if (!returnNull) {
				throw new SystemException(e.getMessage());
			}
		}

		return clazz;
	}

	public static Object invokeMethod(String methodName, Class[] arguments, Object[] parameters, Object source,
			Class<?> sourceClass) {
		try {
			Method method = findMethod(sourceClass, methodName, arguments);
			return method.invoke(source, parameters);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public static Object invokeMethod(String methodName, Class[] arguments, Object[] parameters, Object source,
			String className) {
		Class<?> sourceClass = findClass(className);
		return invokeMethod(methodName, arguments, parameters, source, sourceClass);
	}

	public static Method findMethod(Class<?> clazz, String name, Class[] types) {
		try {
			return clazz.getMethod(name, types);
		} catch (Exception e) {
			throw new SystemException(e.getMessage());
		}
	}
	/**
	 * Verifica se a classe é instanciável.
	 * - Não é um abstract
	 * - Não é um interface
	 * - Nâo é primitivo
	 *
	 * @param clazz
	 * @return
	 *
	 * @author diego.amaral
	 * @since 9 de abr de 2021
	 */
	public static boolean isInstantiable(Class clazz) {
		if(clazz == null) {
			return false;
		}
		int mod = clazz.getModifiers();
		return  !Modifier.isAbstract(mod) && 
				!Modifier.isInterface(mod) && 
				!clazz.isPrimitive() && 
				!clazz.isEnum() && 
				!Number.class.isAssignableFrom(clazz);
	}
	/**
	 * Obtém um java.lang.Package com um dos classloaaders na ordem:
	 * 1. de classe
	 * 2. da thread
	 * 3. do sistema
	 *
	 *
	 * @param name
	 * @param failIfNotExists
	 * @return
	 *
	 * @author diego.amaral
	 * @since 9 de abr de 2021
	 */
	public static Package findPackage(String name, boolean failIfNotExists) {

		Package result = findPackage(name, UtilReflection.class.getClassLoader());
		if (result == null) {
			result = findPackage(name, Thread.currentThread().getContextClassLoader());
			if (result == null) {
				result = findPackage(name, ClassLoader.getSystemClassLoader());
			}
		}
		if(result == null) {
			log.warn("Fail on find package [{}] not found or it is empty.", name);
		}
		SystemAssertion.instance.shouldFalse(failIfNotExists && result == null, "Fail on find package [%s] not found or it is empty.", name);

		return result;

	}
	/**
	 * Tenta obter um java.lang.Package a partir de um classloader.
	 *
	 * @param name
	 * @param cl
	 * @return
	 *
	 * @author diego.amaral
	 * @since 9 de abr de 2021
	 */
	public static Package findPackage(String name, ClassLoader cl) {
		try {
			return UtilObject.anyNull(cl, name) ? null : cl.getDefinedPackage(name);
		}catch(Exception e) {
			log.warn("Fail on trying Package {} with class loader {}", name, cl);
		}
		return null;
	}
	/**
	 * Tenta encontrar Class no package recursivamente.
	 *
	 * @param <T>
	 * @param subtype
	 * @param packageName
	 * @return
	 *
	 * @author diego.amaral
	 * @since 9 de abr de 2021
	 */
	public static <T> Set<Class<? extends T>> findSubtypesOfInPackage(Class<T> subtype, String packageName){
		UtilReflection.findPackage(packageName, false);
		Reflections ref = new Reflections(packageName);
		return ref.getSubTypesOf(subtype);
	}

	public static Class<?> forName(String name) {
		try {
			return ClassUtils.forName(name, defaultClassLoader());
		}catch(Exception e) {
			throw new SystemException(e);
		}
	}


	
	/**
	 * Retorna os argumentos de parametrização de uma classe.
	 * Por exemplo: public class IExample<TestA, TestB> {}.
	 *
	 * É retornado Class[] = {TestA.class, TestB.class}
	 *
	 * @param clazz
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 4 de fev de 2021
	 */
	public static Type[] getParametrizedType(Class<?> clazz) {
		if(clazz == null) {
			return new Type[0];
		}
		Type sooper = clazz.getGenericInterfaces()[0];
		return ((ParameterizedType)sooper).getActualTypeArguments();
	}	
}
