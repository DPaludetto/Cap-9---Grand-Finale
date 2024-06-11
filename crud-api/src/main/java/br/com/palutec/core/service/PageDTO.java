package br.com.palutec.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDTO<T> {

	private List<T> content;

	@ApiModelProperty("Quantidade de registros por página")
	private int size;

	@ApiModelProperty("Total de registros")
	private Long totalElements;

	@ApiModelProperty("Total de páginas")
	private int totalPages;

	@ApiModelProperty("Número da página")
	private int number;

	/**
	 * Constructor with parameters.
	 * 
	 * @param content List of page elements
	 * @param pageable {@link Pageable} with page data
	 * @param totalElements Total number of elements
	 */
	public PageDTO(final List<T> content, final Pageable pageable, final Number totalElements) {
		this.content = content;
		this.totalElements = totalElements.longValue();
		if(pageable != null) {
			this.totalPages = pageable.getPageSize() == 0 ? 1
					: (int) Math.ceil((double) totalElements.longValue() / (double) pageable.getPageSize());
			this.size = pageable.getPageSize();
			this.number = pageable.getPageNumber();
		}
	}
	
	
}
