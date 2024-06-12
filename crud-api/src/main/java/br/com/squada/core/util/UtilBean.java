package br.com.squada.core.util;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.squada.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilBean {
	
	private static final Map<Class<?>, Class<?>> PREFERRED_INSTANCE_MAPPING = new HashMap<>();
	private static final List<Class<?>> NOT_INSTANCIABLE_MAPPING = new LinkedList<>();

	static {
		PREFERRED_INSTANCE_MAPPING.put(List.class, LinkedList.class);
		PREFERRED_INSTANCE_MAPPING.put(Map.class, HashMap.class);

		NOT_INSTANCIABLE_MAPPING.add(Number.class);
	}

	/**
	 * Oobtém uma propriedade aninhada do bean.
	 *
	 * @param bean
	 * @param name
	 * @param failOnError - Se true, propaga a exceção. Senão, retorna null.
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 12/05/2016
	 */
	public static Object getNestedProperty(Object bean, String name, boolean failOnError){
		Object o = null;
		try{
			o = PropertyUtils.getNestedProperty(bean, name);
		}catch(NestedNullException | NoSuchMethodException e){
			String message = String.format("Property not found: %s.%s.", bean.getClass().getName(), name);
			if(failOnError) {
				throw new RuntimeException(message, e);
			}else {
				log.trace(message);
			}
		}catch(Exception e){
			String message = String.format("Error on getting property  %s.%s", classNameDescription(bean), name);
			if(failOnError){
				throw new RuntimeException(message, e);
			}else{
				log.warn(message, e);
			}
		}
		return o;
	}
	/**
	 * Obtém a descrição da classe para logs internos.
	 * 
	 * @param bean
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 28 de abr de 2021
	 */
	public static String classNameDescription(Object bean) {
		return bean == null ? "<null>" : StringUtils.defaultString(bean.getClass().getName(), null);
	}
	
	public static void copyProperties(Object target, Object source, boolean throwIfError) {
		try {
			BeanUtils.copyProperties(target, source);
		} catch (Exception e) {
			String srcDesc = classNameDescription(source);
			String targetDesc = classNameDescription(target);
			String message = String.format("Error on copy property beans. %s->%s", srcDesc, targetDesc);
			if(throwIfError) {
				throw new SystemException(e, message);
			}else {
				log.warn(message);
			}
		}
	}
	
	/**
	 * Realiza a cópia das propriedades.
	 *
	 * @param target
	 * @param propertyTarget
	 * @param source
	 * @param prorpertySource
	 * @param failOnError
	 *
	 * @author diego.amaral
	 * @since 6 de abr de 2021
	 */
	public static void copyProperty(Object target, String propertyTarget, Object source, String prorpertySource, boolean failOnError) {
		Object value = UtilBean.getNestedProperty(source, prorpertySource, failOnError);
		setNestedProperty(target, propertyTarget, value, failOnError);
	}
	
	/**
	 * Cria instâncias do path de um objeto.
	 * Por exemplo:
	 * Tendo uma property Z.a.b.c do objeto Z. Se a é null, o método tenta instanciar os objetos a, b e c.
	 * Para isso todos os objetos devem ser java beans. Ou seja, ter um construtor padrão sem argumentos.
	 *
	 * @param obj
	 * @param fieldName
	 *
	 * @author Diego C. Amaral
	 * @since 17 de dez de 2020
	 */
	public static void instantiateNestedProperties(Object obj, String fieldName) {

		try {
	        String[] fieldNames = fieldName.split("\\.");
	        if (fieldNames.length > 1) {
	            StringBuilder nestedProperty = new StringBuilder();
	            for (int i = 0; i < fieldNames.length; i++) {
	                String fn = fieldNames[i];
	                if (i != 0) {
	                    nestedProperty.append(".");
	                }
	                nestedProperty.append(fn);

	                Object value = PropertyUtils.getProperty(obj, nestedProperty.toString());

	                if (value == null) {
	                	String propertyName = nestedProperty.toString();
	                    PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(obj, nestedProperty.toString());
	                    Class<?> propertyType = propertyDescriptor.getPropertyType();
	                    Object newInstance = findInstance(propertyType);
	                    PropertyUtils.setProperty(obj, propertyName, newInstance);
	                }
	            }
	        }

		} catch (NoSuchMethodException e) {
	        log.debug("Sub instantantiation failed. Path not found {}.{}", obj.getClass().getName(), fieldName);
	    } catch (Exception e) {
	        throw new SystemException(e, "Error on instantiate nested bean property %s", fieldName);
	    }

	}
	
	public static void setNestedProperty(Object target, String property, Object value, boolean failOnError){
		try{

			if(value != null) {
				if(property.contains(".") && !UtilBean.isPropertyWriteable(target, property)) {
					UtilBean.instantiateNestedProperties(target, property);
				}

				Class<?> targetClazz = null;
				PropertyDescriptor desc = PropertyUtils.getPropertyDescriptor(target, property);

				if(desc != null) {
					targetClazz = desc.getPropertyType();
				}

//				UtilConverter converter = UtilConverter.getInstance();
//				if(converter.hasRegistered(Hibernate.getClass(value), targetClazz)){
//					value = converter.convert(value, targetClazz);
//				}
			}
			PropertyUtils.setProperty(target, property, value);

		}catch(NoSuchMethodException e){
			log.debug("Property {}.{} not accessible.",classNameDescription(target), property);
		}catch(IllegalArgumentException e) {
			log.warn("Error on set property {}.{} <{}>", classNameDescription(target), property, e.getMessage());
		}catch(Exception e){
			treatException(e, failOnError, "Error on set property %s.%s", classNameDescription(target), property);
		}
	}
	
	/**
	 * Verifica se uma propriedade é writeable.
	 *
	 * @param o
	 * @param property
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 19 de dez de 2020
	 */
	public static boolean isPropertyWriteable(Object o, String property) {
		try {
			return PropertyUtils.isWriteable(o, property);
		}catch(Exception e) {
			return false;
		}
	}
	
	private static void treatException(Exception e, boolean failOnError, String message, Object ...messageValues) {
		message = String.format(message, messageValues);
		if(failOnError){
			throw new SystemException(e, message);
		}else{
			log.warn(message, e);
		}
	}
	
	private static Object findInstance(Class<?> clazz) {
		Class<?> targetClass = clazz;

		for(Class c : NOT_INSTANCIABLE_MAPPING) {
			if(c.isAssignableFrom(clazz)) {
				return null;
			}
		}
		if(targetClass.isInterface()) {
			targetClass = PREFERRED_INSTANCE_MAPPING.get(targetClass);
			if(targetClass == null) {
				throw new SystemException("%s is an interface but has a not default implementations mapped in %s.", clazz, PREFERRED_INSTANCE_MAPPING.values());
			}
		}

		return UtilReflection.newInstance(targetClass);
	}
}
