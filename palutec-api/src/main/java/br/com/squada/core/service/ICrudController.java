package br.com.squada.core.service;

import org.springframework.data.domain.Pageable;

public interface ICrudController<D> {

	public D post(final D dto);
	
	public D getById(final String id);
	
	public PageDTO<?> getByFilterPageable(D filter, final Pageable pageable);
	
	public void delete(final String id);
	
	public D put(final String id, final D dto);
	
}
