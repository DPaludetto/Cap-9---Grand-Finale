package br.com.palutec.claro.pagadoria.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	/**
	 * Create {@link ModelMapper} with custom mappings.
	 * 
	 * @return Configured {@link ModelMapper}
	 */
	@Bean
	public ModelMapper modelMapper() {
		final ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.LOOSE);

		return modelMapper;
	}
}
