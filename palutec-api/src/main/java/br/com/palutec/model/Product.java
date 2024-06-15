package br.com.palutec.model;

import br.com.palutec.core.service.AbstractBeanModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter 
@Setter
public class Product extends AbstractBeanModel{

	@Column(name = "nm_product")
	private String name;

	@Column(name = "ds_brand")
	private String brand;
	
	@Column(name = "tp_product")
	private String type;
	
	@Column(name = "ds_description")
	private String description;
	
	@Column(name = "vl_price")
	private Double price;

	@Column(name = "qt_stock_quantity")
	private Integer stockQuantity;
	
}
