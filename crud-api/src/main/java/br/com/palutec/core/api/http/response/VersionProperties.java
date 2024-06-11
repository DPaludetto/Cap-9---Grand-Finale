package br.com.palutec.core.api.http.response;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class VersionProperties {
    
	private String name;
	private String version;
    private String buildTime;
    private String javaSourceVersion;
    private String javaTargetVersion;
    private String mavenVersion;
    private String gitHash;
    private String gitBranch;

    @Bean
    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
    	return String.format("%s %s", getName(), getVersion());
    }
}
