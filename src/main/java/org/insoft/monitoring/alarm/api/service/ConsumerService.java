package org.insoft.monitoring.alarm.api.service;

import org.insoft.monitoring.alarm.api.common.Constants;
import org.insoft.monitoring.alarm.api.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Consumer Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 **/
@Service
public class ConsumerService {
    private final RestTemplateService restTemplateService;

    @Autowired
    public ConsumerService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public Object createConsumerInstance(String groupName, Map<String, String> parameter) {
        return restTemplateService.send(Constants.TARGET_KAFKA_API, "/consumers/" + groupName, HttpMethod.POST, parameter, Object.class);
    }

    public Object deleteConsumerInstance(String groupName, String instance) {
        return restTemplateService.send(Constants.TARGET_KAFKA_API,"/consumers/" + groupName + "/instances/" + instance, HttpMethod.DELETE, null, Object.class);
    }

    public Map<String, Object> createConsumerOffset(String groupName, String instance) {
        return restTemplateService.send(Constants.TARGET_KAFKA_API,"/consumers/" + groupName + "/instances/" + instance, HttpMethod.POST, null, Map.class);
    }

    public Object getSubscription(String groupName, String instance) {
        return restTemplateService.send(Constants.TARGET_KAFKA_API, "/consumers/" + groupName + "/instances/" + instance + "/subscription", HttpMethod.GET, null, Object.class);
    }

    public Object getMessage(String groupName, String instance) {
        return restTemplateService.getMsg(Constants.TARGET_KAFKA_API,"/consumers/" + groupName + "/instances/" + instance + "/records", HttpMethod.GET, null, Object.class);
    }

    public void subscriptionToConsumer(String groupName, String instance, Map parameter) {
        restTemplateService.send(Constants.TARGET_KAFKA_API,"/consumers/" + groupName + "/instances/" + instance + "/subscription", HttpMethod.POST, parameter, Map.class);
    }

    public void assignPartitionsToConsumer(String groupName, String instance, Map parameter) {
        restTemplateService.send(Constants.TARGET_KAFKA_API,"/consumers/" + groupName + "/instances/" + instance + "/assignments", HttpMethod.POST, parameter, Map.class);
    }

}
