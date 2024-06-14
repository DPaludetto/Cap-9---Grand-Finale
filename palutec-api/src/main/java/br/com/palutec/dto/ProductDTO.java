package br.com.palutec.dto;

import br.com.squada.core.service.AbstractDTOModel;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class ProductDTO extends AbstractDTOModel{

	private String name;
	private String type;
	private String description;
	private Integer stockQuantity;
	
}
