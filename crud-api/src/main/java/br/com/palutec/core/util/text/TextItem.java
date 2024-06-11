package br.com.palutec.core.util.text;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

public class TextItem {

	@XmlAttribute
	public String name;

	@XmlValue
	public String text;

}
