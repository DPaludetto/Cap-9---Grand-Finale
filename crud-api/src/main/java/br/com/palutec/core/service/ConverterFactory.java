package br.com.palutec.core.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ConverterFactory {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private ModelMapper mapper;
	
	public IConverter findConverter(Class<? extends AbstractBeanModel> modelClass, Class<?> dtoClass) {
		return new DefaultBeanConverter(mapper, modelClass, dtoClass);
	}

}
