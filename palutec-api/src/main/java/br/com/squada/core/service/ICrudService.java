package br.com.squada.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

public interface ICrudService<D, M extends AbstractBeanModel> {
	
	D save(final D dto);
	
	void saveAll(final List<M> models);
	
	M findModelById(final String id);
	
	Optional<M> findModelByExample(final Example<M> id);
	
	D findDTOById(final String id);
	
	void delete(final String id);
	
	D update(final D dto, final String id);
	
	PageDTO<?> findByFilter(final D filter, final Pageable pageable);
	
}
