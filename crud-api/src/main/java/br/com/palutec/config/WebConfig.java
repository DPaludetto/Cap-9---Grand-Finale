package br.com.palutec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.squada.core.api.http.response.VersionProperties;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry
        .addMapping("/**")
        .allowedOrigins("*")
        .exposedHeaders("*")
        .allowedMethods("*");
	}
	
	@Bean
	public String compactVersion(VersionProperties version) {
		return version.getVersion();
	}
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        return http.build();
    }
}
