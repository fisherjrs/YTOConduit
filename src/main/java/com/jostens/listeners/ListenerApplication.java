package com.jostens.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class ListenerApplication implements ApplicationListener<ApplicationEvent> {
	private static Logger LOG = LoggerFactory.getLogger(ListenerApplication.class);

	public ListenerApplication() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		//Generally, using a custom event handler is unnecessary.
		LOG.info("CUSTOM EVENT HANDLER  :: " + event.getClass() + " " + event.getSource());
	}

}
