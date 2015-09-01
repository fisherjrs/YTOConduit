package com.jostens.ytoconduit.web;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jostens.ytoconduit.service.TaskService;

@RestController
@RequestMapping("/conduitservices")
public class AsyncCallableController {
	private static Logger LOG = LoggerFactory.getLogger(TaskService.class);
	@Autowired
	private final TaskService taskService;
    
    @Autowired
    public AsyncCallableController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @RequestMapping(value = "/callable", method = RequestMethod.GET, produces = "text/html")
    public Callable<String> executeSlowTask() {
        LOG.info("Request received");
        Callable<String> callable = taskService::execute;
        LOG.info("Servlet thread released");
        
        return callable;
    }
}