package br.com.palutec.core.util.text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.com.palutec.core.exception.SystemException;
import br.com.palutec.core.util.UtilIO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

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
 * @since 22 de abr de 2019
 */
@Slf4j
public class TextRepositoryProvider {

	private Map<String, String> cache = new HashMap<>();

	private static TextRepositoryProvider instance = new TextRepositoryProvider();

	//Singleton
	private TextRepositoryProvider() {}

	public static TextRepositoryProvider getInstance() {
		return instance;
	}

	public String get(String name){
		String text = cache.get(name);

		if(!cache.containsKey(name)){
			log.warn(String.format("No text named [%s] found in SQL repository.", name));
		}
		return text;
	}


	public void addInputStream(InputStream ...inputStream){
		for(InputStream is : inputStream){
			if(!UtilIO.isReady(is)) {
				throw new SystemException("Error on add inputStream to TextRepository. Maybe resource was not found.");
			}
			InputStreamReader piece = new InputStreamReader(is);
			addFile(piece);
		}
	}
	public void addText(String ...xmlStringFile){
		for(String s : xmlStringFile){
			addFile(new StringReader(s));
		}
	}

	public synchronized void addFile(Reader reader){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(TextRepository.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			TextRepository repository = (TextRepository)jaxbUnmarshaller.unmarshal(reader);

			if(repository == null){
				return;
			}
			for(TextItem item : repository.items){
				if(StringUtils.isBlank(item.name)){
					throw new SystemException("Tag <text> must have 'name' attribute.");
				}
				String textName = item.name.trim();

				if(!StringUtils.isBlank(item.text)){
					if(cache.containsKey(textName)){
						log.warn(String.format("Text named [%s] already registred in SQL repository!", textName));
					}
					log.debug(String.format("Loading text named [%s] in repository.", textName));
					cache.put(textName, item.text.trim());

				}else{
					log.warn(String.format("Text item named %s have empty content.", item.name));
				}
			}
			log.debug(String.format("Found %d texts in repository.", cache.size()));

			reader.close();
		}catch(Exception e){
			throw new SystemException(e);
		}
	}

	public void loadResourceInClassPath(String ...resourceName) {

		for(String resource : resourceName) {
			try {
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
				addInputStream(is);
				UtilIO.closeSilently(is);
			}
			catch(Exception e) {
				log.error(String.format("Error on add resource [%s] to TextRepository.", resource), e);
			}
		}

	}
}
