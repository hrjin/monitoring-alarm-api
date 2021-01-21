package org.insoft.monitoring.alarm.api.controller;

import org.insoft.monitoring.alarm.api.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 **/
@RestController
@RequestMapping("/consumers")
public class ConsumerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerController.class);
    private final ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping("/{groupName}")
    public Object createConsumerInstance(@PathVariable String groupName, @RequestBody Map parameter) {
        return consumerService.createConsumerInstance(groupName, parameter);
    }

    @DeleteMapping("/{groupName}/instances/{instance}")
    public Object deleteConsumerInstance(@PathVariable String groupName, @PathVariable String instance) {
        return consumerService.deleteConsumerInstance(groupName, instance);
    }

    @PostMapping("/{groupName}/instances/{instance}/offsets")
    public Map<String, Object> createConsumerOffset(@PathVariable String groupName, @PathVariable String instance) {
        return consumerService.createConsumerOffset(groupName, instance);
    }

    @PostMapping("/{groupName}/instances/{instance}/subscription")
    public void subscriptionToConsumer(@PathVariable String groupName, @PathVariable String instance, @RequestBody Map parameter) {
        consumerService.subscriptionToConsumer(groupName, instance, parameter);
    }

    @PostMapping("/{groupName}/instances/{instance}/assignments")
    public void assignPartitionsToConsumer(@PathVariable String groupName, @PathVariable String instance, @RequestBody Map parameter) {
        consumerService.assignPartitionsToConsumer(groupName, instance, parameter);
    }

    @GetMapping("/{groupName}/instances/{instance}/records")
    public Object getMessage(@PathVariable String groupName, @PathVariable String instance) {
        return consumerService.getMessage(groupName, instance);
    }
}
