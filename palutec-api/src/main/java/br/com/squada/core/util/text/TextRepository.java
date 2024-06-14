package br.com.squada.core.util.text;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
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
 * @since 21 de abr de 2019
 */
@XmlRootElement(name="text-repository")
public class TextRepository {

	@XmlElement(name="text")
	protected List<TextItem> items;

}
