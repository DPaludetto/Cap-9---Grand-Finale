package br.com.palutec.core.db;

import java.io.Serializable;

public interface IModelDTO extends Serializable {

	String getId();
	
	void setId(String string);
}
