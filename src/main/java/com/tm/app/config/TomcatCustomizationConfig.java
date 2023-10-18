package com.tm.app.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatCustomizationConfig {

	 @Bean
	    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
	        return factory -> {
	            factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
	                connector.setProperty("relaxedPathChars", "<>[\\]^`{|}");
	                connector.setProperty("relaxedQueryChars", "<>[\\]^`{|}");
	            });
	        };
	    }
}
