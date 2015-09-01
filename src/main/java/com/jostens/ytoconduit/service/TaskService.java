package com.jostens.ytoconduit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskService{
    private static Logger LOG = LoggerFactory.getLogger(TaskService.class);
    
    public String execute() {
        try {
            Thread.sleep(5000);
            LOG.info("Slow task executed");
            return "Task finished";
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
