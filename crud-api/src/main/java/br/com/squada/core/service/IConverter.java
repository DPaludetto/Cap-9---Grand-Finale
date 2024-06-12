package br.com.squada.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

public interface IConverter<D, M extends AbstractBeanModel> {

	M toModel(final D dto);
	
	D toDTO(M model);
	
	void merge(final D dto, final M model);
	
	List<D> toDTOList(final Page<M> models);
	
	List<D> toDTOList(final List<M> models);
	
	List<D> toDTOList(final Iterable<M> iterator);
	
	List<M> toModels(final List<D> dtos);
	
}
