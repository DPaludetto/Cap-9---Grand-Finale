package br.com.squada.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import br.com.squada.core.db.IModelDTO;

public class DefaultBeanConverter<D, M extends AbstractBeanModel> implements IConverter<D, M>{

	private ModelMapper modelMapper;
	
	private Class<M> modelClass;
	private Class<D> dtoClass;
	
	public DefaultBeanConverter(ModelMapper modelMapper, Class<M> modelClass, Class<D> dtoClass) {
		this.modelMapper = modelMapper;
		this.modelClass = modelClass;
		this.dtoClass = dtoClass;
	}

	/**
	 * Converts a DTO into a Model entity.
	 * 
	 * @param demandCreationDTO Demand creation DTO
	 * @return Demand entity
	 */
	public M toModel(final D dto) {
		M model = this.modelMapper.map(dto, getClassModel());
		if(IModelDTO.class.isInstance(dto)) {
			model.setId(((IModelDTO)dto).getId());
		}
		return model;
	}

	/**
	 * Converts a Model entity into a DTO object.
	 * 
	 * @param demand Demand entity
	 * @return DemandDTO object
	 */
	public D toDTO(final M model) {
		D dto = modelMapper.map(model, this.getClassDTO());
		if(IModelDTO.class.isAssignableFrom(this.getClassDTO())) {
			((IModelDTO) dto).setId(model.getId());
		}

		return dto;
	}
	
	/**
	 * Merge data from DTO object into Model entity.
	 * 
	 * @param demandDTO DemandDTO object
	 * @param demand Demand entity
	 */
	public void merge(final D dto, final M model) {
		this.modelMapper.map(dto, model);
	}
	
	private Class<D> getClassDTO() {
		return this.dtoClass;
//		@SuppressWarnings("unchecked")
//		Class<D>  clazz = (Class<D>) ((ParameterizedType) getClass()
//                .getGenericSuperclass()).getActualTypeArguments()[0];
//		return clazz;
	}
	
	private Class<M> getClassModel() {
		return this.modelClass;
//		return (Class<M>) ((TypeVariable)((ParameterizedType) getClass()
//                .getGenericInterfaces()[0]).getActualTypeArguments()[1]).getBounds()[0];

	}
	
	public List<D> toDTOList(final Page<M> models) {
		return models.stream()
				.map(model -> toDTO(model))
				.collect(Collectors.toList());
	}
	public List<D> toDTOList(final Iterable<M> iterator) {
		List<D> actualList = new ArrayList<D>();
		iterator.forEach(e -> actualList.add(toDTO(e)));
		return actualList;
	}	
	
	public List<D> toDTOList(final List<M> models) {
		return models.stream()
				.map(model -> toDTO(model))
				.collect(Collectors.toList());
	}	
	
	public List<M> toModels(final List<D> dtos) {
		return dtos.stream()
				.map(dto -> toModel(dto))
				.collect(Collectors.toList());
	}
}
