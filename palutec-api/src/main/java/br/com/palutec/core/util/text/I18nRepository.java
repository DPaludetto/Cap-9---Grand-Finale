package br.com.palutec.core.util.text;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import br.com.palutec.core.exception.SystemException;
import br.com.palutec.core.util.UtilString;
import lombok.extern.slf4j.Slf4j;
/**
 *
 * <P><B>Description :</B><BR>
 * Concentrador de internacionalização de mensagens.
 * </P>
 * <P>
 * <B>
 * Issues : <BR>
 * None
 * </B>
 *
 * @author Diego C. Amaral
 * @since 21 de jan de 2021
 */
@Slf4j
public class I18nRepository {

	private Map<String, ResourceBundle> bundles = Collections.synchronizedMap(new HashMap<>());

	private static I18nRepository instance = new I18nRepository();

	//For singleteon
	private I18nRepository() {}

	/**
	 * Obtém a instância.
	 *
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 21 de jan de 2021
	 */
	public static I18nRepository getInstance() {
		return instance;
	}

	/**
	 * Registra um pacote de mensagens internacionalizadas.
	 *
	 * @param path
	 *
	 * @author Diego C. Amaral
	 * @since 14 de out de 2019
	 */
	private synchronized void addMessageBundle(String path) {
		try {
			if(UtilString.isBlank(path)) {
				return;
			}
			log.info("Loading i18n messages from bundle {} ", path);
	
			path = UtilString.removeAll(path, "_[a-z]{2}_[A-Z]{2}.properties");
	
			ResourceBundle bundle = ResourceBundle.getBundle(path, new Locale("pt", "BR"));
	
			if(bundle == null) {
				throw new SystemException("Bundle %s not found!", path);
			}
	
			log.info(String.format("Registered bundle [%s] for exception messages.", path));
			bundles.put(path, bundle);
		}catch(MissingResourceException e) {
			log.warn("No i18n message bundle {} found.", path);
		}
	}
	/**
	 * Registra um recurso (arquivo) de mensagens no classpath.
	 *
	 * @param path
	 *
	 * @author Diego C. Amaral
	 * @since 21 de jan de 2021
	 */
	public static void registerMessageBundle(String path) {
		getInstance().addMessageBundle(path);
	}

	/**
	 * Obtém uma mensagem.
	 *
	 * @param key
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 14 de out de 2019
	 */
	public String getRawMessage(String key) {
		for(ResourceBundle bundle : bundles.values()) {
			try {
				String message = bundle.getString(key);
				if(!UtilString.isBlank(message)) {
					return message;
				}
			}catch(MissingResourceException e) {
				log.debug("Message for key [{}] not found.");
			}catch(Exception e) {
				log.warn("Error on retrieve message for key [{}]: {}", key, e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Verifica se a key existe em algum arquivo de internacionalização registrado.
	 *
	 * @param key
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 3 de mar de 2021
	 */
	public boolean existsKey(String key) {
		for(ResourceBundle bundle : bundles.values()) {
			if(bundle.containsKey(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtém uma mensagem formatada.
	 *
	 * @param messageOrCode
	 * @param args
	 * @return
	 *
	 * @author Diego C. Amaral
	 * @since 14 de out de 2019
	 */
	public String getFormattedMessage(String messageOrCode, Object... args) {

		try {
			String bundledMessage = getRawMessage(messageOrCode);
			if(!UtilString.isBlank(bundledMessage)) {
				return MessageFormat.format(bundledMessage, args);
			}
			return String.format(messageOrCode, args);
		}catch(Exception e) {
			throw new SystemException(e, "Error on format message %s [%s]", messageOrCode, Arrays.toString(args));
		}
	}
}
