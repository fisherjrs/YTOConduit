package com.jostens.config;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.jostens.ytoconduit.service.DefinitionsService;
import com.jostens.ytoconduit.service.MessageService;
import com.jostens.ytoconduit.service.TaskService;
import com.jostens.ytoconduit.service.TransferService;

@Configuration
@ComponentScan
public class YTOConfig extends AbstractConfiguration {

	private static Logger LOG = LoggerFactory.getLogger(YTOConfig.class);
	
	@Autowired
	private Environment env;
	
	public YTOConfig() {
		// TODO Auto-generated constructor stub
	}
	
	@Bean
    public DefinitionsService definitionsService() {
        return new DefinitionsService();
    }
	
	@Bean
	public MessageService messageService() {
		return new MessageService();
	}
	
	@Bean
	public TaskService taskService() {
		return new TaskService();
	}
	
	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.indentOutput(true);
		builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		builder.failOnEmptyBeans(false);
		return builder;
	}
	
	/*
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties(){
		PropertySourcesPlaceholderConfigurer configurer =   new PropertySourcesPlaceholderConfigurer();		
		Resource[] resources = new ClassPathResource[ ]
		    { new ClassPathResource( "application.yml" ) };
		configurer.setLocations( resources );
		configurer.setIgnoreUnresolvablePlaceholders( true );
		 return configurer;
	}
	*/
	
	@PostConstruct
	private void init() {
		LOG.info("Initialized YTOConfig.");
		if (env instanceof ConfigurableEnvironment) {
			LOG.info("We have an environment that is ConfigurableEnvironment.");
			LOG.info("Profiles :: " + env.getActiveProfiles());
			LOG.info("Property :: " + env.getProperty("spring.profiles.active"));
			for (String profile : env.getActiveProfiles()) {
				LOG.info("profile :: " + profile);
			}
			/*
			String resource = env.getProperty("spring.profiles.sub") +".main.properties";
		        Resource props = new ClassPathResource(resource);
		        if (env instanceof ConfigurableEnvironment && props.exists()) {
		            MutablePropertySources sources = ((ConfigurableEnvironment) env).getPropertySources();
		            sources.addBefore("main", new ResourcePropertySource(props)); 
		        }
		    */
		} else {
			LOG.info("Not a configurable environment.");
		}
	}
	
	public Integer getPosition() {
		return Integer.valueOf(44);
	}
	
	@Override
	public Object getProperty(String key) {
		return null;
	}
	
	@Override
	public boolean containsKey(String key) {
		return getProperty(key) != null;
	}

	@Override
	public Iterator<String> getKeys() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return getKeys().hasNext();
	}
	
	@Override
	protected void addPropertyDirect(String key, Object value) {
		
	}

}
