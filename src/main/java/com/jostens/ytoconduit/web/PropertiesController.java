package com.jostens.ytoconduit.web;

import java.util.List;
import java.util.Map;

import org.apache.tools.ant.taskdefs.optional.junit.SummaryJUnitResultFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextPropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.jostens.config.YTOConfig;
import com.jostens.ytoconduit.model.Message;
import com.jostens.ytoconduit.model.View;
import com.jostens.ytoconduit.model.YTODesignDefinition;
import com.jostens.ytoconduit.service.DefinitionsService;
import com.jostens.ytoconduit.service.MessageService;

@Controller
@RequestMapping("/conduitservices")
public class PropertiesController {
	private static Logger LOG = LoggerFactory.getLogger(PropertiesController.class);
	
	@Autowired
	YTOConfig ytoConfig;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private DefinitionsService definitionsService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private PropertySourcesPlaceholderConfigurer configurer;
	@Autowired
	private Environment env;
	

	@JsonView(View.Summary.class)
	@RequestMapping("/getmessages.json")
	@ResponseBody()
	public List<Message> getAllMessages() {
		return messageService.getAll();
	}
	
	@JsonView(View.SummaryWithRecipients.class)
	@RequestMapping("/getmessagesextended.json")
	@ResponseBody()
	public List<Message> getAllMessagesExtended() {
		return messageService.getAll();
	}
	
	@RequestMapping("/listproperties")
	@ResponseBody()
	public String listProperties (Model model) {
		String allProperties = "";
		
    	if (env instanceof ConfigurableEnvironment) {
			LOG.info("We have an environment that is ConfigurableEnvironment.");
			LOG.info("Property :: " + env.getProperty("spring.profiles.active"));
			for (String profile : env.getActiveProfiles()) {
				LOG.info("profile :: " + profile);
			}
			
			for (String profile : env.getDefaultProfiles()) {
				LOG.info("defaultprofile :: " + profile);
			}

			//Loop over config property sources
			for (org.springframework.core.env.PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources() ) {
				LOG.info("property source " + propertySource.getName() + " " + propertySource.getClass());
				
				//ServletContextPropertySource
				if ("servletContextInitParams".equals(propertySource.getName()) & propertySource instanceof ServletContextPropertySource) {
					LOG.info("PropertySource ::: ServletContextPropertySource");
					String[] servletContextPropertyNames = ((ServletContextPropertySource) propertySource).getPropertyNames();
					for(String propertyName : servletContextPropertyNames) {
						LOG.info("Servlet Property :: " + propertyName + " :: " + env.getProperty(propertyName));
					}
				}
				
				//MapPropertySource ... application config ... the name is pretty hokey ... find a better way to isolate the application.yml file
				if ( "applicationConfig: [classpath:/application.yml]".equals(propertySource.getName()) & propertySource instanceof MapPropertySource) {
					LOG.info("The property source is a MappedPropertySource");
					String[] applicationConfigPropertyNames = ((MapPropertySource) propertySource).getPropertyNames();
					for(String propertyName : applicationConfigPropertyNames) {
						LOG.info("App Config Property :: " + propertyName + " :: " + env.getProperty(propertyName));
					}
				}
			}
			
			Map<String,Object> properties = ((ConfigurableEnvironment) env).getSystemProperties();
			for(Map.Entry<String,Object> item : properties.entrySet()) {
				LOG.info("system property :: " + item.getKey() + " ::: " + item.getValue());
			}
			
			Map<String,Object> systemEnv = ((ConfigurableEnvironment) env).getSystemEnvironment();
			for(Map.Entry<String,Object> item : systemEnv.entrySet()) {
				LOG.info("env property :: " + item.getKey() + " ::: " + item.getValue());
			}

		} else {
			LOG.info("Not a configurable environment.");
		}
    	

		
		return "{\"id\":2634,\"content\":\"Hello, World!\"}";
	}
	
	
    @RequestMapping("/properties")
    public String showProperties(
    		@RequestParam(value="name", required=false, defaultValue="World") String name,
    		@RequestParam(value="designId", required=false, defaultValue="9") String designId,
    		Model model) {
        
    	if (env instanceof ConfigurableEnvironment) {
			LOG.info("We have an environment that is ConfigurableEnvironment.");
			LOG.info("Property :: " + env.getProperty("spring.profiles.active"));
			for (String profile : env.getActiveProfiles()) {
				LOG.info("profile :: " + profile);
			}
			
			for (String profile : env.getDefaultProfiles()) {
				LOG.info("defaultprofile :: " + profile);
			}

			//Loop over config property sources
			for (org.springframework.core.env.PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources() ) {
				LOG.info("property source " + propertySource.getName() + " " + propertySource.getClass());
				
				//ServletContextPropertySource
				if ("servletContextInitParams".equals(propertySource.getName()) & propertySource instanceof ServletContextPropertySource) {
					LOG.info("PropertySource ::: ServletContextPropertySource");
					String[] servletContextPropertyNames = ((ServletContextPropertySource) propertySource).getPropertyNames();
					for(String propertyName : servletContextPropertyNames) {
						LOG.info("Servlet Property :: " + propertyName + " :: " + env.getProperty(propertyName));
					}
				}
				
				//MapPropertySource ... application config ... the name is pretty hokey ... find a better way to isolate the application.yml file
				if ( "applicationConfig: [classpath:/application.yml]".equals(propertySource.getName()) & propertySource instanceof MapPropertySource) {
					LOG.info("The property source is a MappedPropertySource");
					String[] applicationConfigPropertyNames = ((MapPropertySource) propertySource).getPropertyNames();
					for(String propertyName : applicationConfigPropertyNames) {
						LOG.info("App Config Property :: " + propertyName + " :: " + env.getProperty(propertyName));
					}
				}
			}
			
			Map<String,Object> properties = ((ConfigurableEnvironment) env).getSystemProperties();
			for(Map.Entry<String,Object> item : properties.entrySet()) {
				LOG.info("system property :: " + item.getKey() + " ::: " + item.getValue());
			}
			
			Map<String,Object> systemEnv = ((ConfigurableEnvironment) env).getSystemEnvironment();
			for(Map.Entry<String,Object> item : systemEnv.entrySet()) {
				LOG.info("env property :: " + item.getKey() + " ::: " + item.getValue());
			}

		} else {
			LOG.info("Not a configurable environment.");
		}
    	
    	
        LOG.info("configurer :: " + configurer.getAppliedPropertySources().get("application.yml"));
        
        
        model.addAttribute("name", name);
        YTODesignDefinition designDefinition = definitionsService.getDesignDefinition(Long.valueOf(designId));
        model.addAttribute("designDefinition", designDefinition);
        
        
        
        return "properties";
    }


}
