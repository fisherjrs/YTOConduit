package com.jostens.ytoconduit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
	private static Logger LOG = LoggerFactory.getLogger(TransferService.class);

	public TransferService() {
		// TODO Auto-generated constructor stub
	}
	
	public TransferService(Integer position) {
		LOG.info("TransferService constructed with postion : " + position);
	}

	public String getServiceDetails() {
		return "This is a placeholder service class and function that is used for wiring up a configuration.";
	}
}
