package com.jostens.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jostens.ytoconduit.service.TransferService;

@Configuration
public class SecondaryConfig {
	
	private static Logger LOG = LoggerFactory.getLogger(SecondaryConfig.class);

	@Autowired 
	YTOConfig ytoConfig;
	
	public SecondaryConfig() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	private void init() {
		LOG.info("Initialized SecondaryConfig.");
	}
	
	//Sample bean config that uses config values from a different config class.
	@Bean
    public TransferService transferService() {
        return new TransferService(ytoConfig.getPosition());
    }

}
