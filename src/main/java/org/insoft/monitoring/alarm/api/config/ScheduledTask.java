package org.insoft.monitoring.alarm.api.config;

import org.insoft.monitoring.alarm.api.common.PropertyService;
import org.insoft.monitoring.alarm.api.common.ResultStatus;
import org.insoft.monitoring.alarm.api.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 **/

@Component
public class ScheduledTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);
    private final ConsumerService consumerService;
    private final PropertyService propertyService;

    @Autowired
    public ScheduledTask(ConsumerService consumerService, PropertyService propertyService) {
        this.consumerService = consumerService;
        this.propertyService = propertyService;
    }

    @PostConstruct
    public void init() {
        createConsumerInstance();
    }

    public void createConsumerInstance() {
        LOGGER.info("초기화 메서드!!!");

        // 1. consumer가 있는지 확인
        Object jsonMsgList = consumerService.getMessage(propertyService.getGroupId(), propertyService.getInstanceName());
        if(isResultStatusInstanceCheck(jsonMsgList)) {
            LOGGER.info("없어 컨슈머");

            // 2. 없으면 만들어줘요
            Map<String, String> params = new HashMap<>();
            params.put("name", propertyService.getInstanceName());
            params.put("format", "json");
            params.put("auto.offset.reset", propertyService.getOffsetReset());
            params.put("auto.commit.enable", propertyService.getEnableAutoCommit());

            consumerService.createConsumerInstance(propertyService.getGroupId(), params);

            Map<String, List> topics = new HashMap<>();
            List<String> topicList = new ArrayList<>();
            topicList.add(propertyService.getDefaultTopic());
            topics.put("topics", topicList);

            consumerService.subscriptionToConsumer(propertyService.getGroupId(), propertyService.getInstanceName(), topics);
        }
    }

    @Scheduled(fixedRateString = "${messaging.ready-fixed-rate}", initialDelayString = "${messaging.ready-initial-delay}")
    public void readyGetMessaging() {
        LOGGER.info("===================Kafka get Message ::: start===================");

        try {
            Object obj = consumerService.getMessage(propertyService.getGroupId(), propertyService.getInstanceName());
            List resultList = (List) obj;
            LOGGER.info("resultList ::: " + resultList.toString());
            LOGGER.info("resultList size ::: " + resultList.size());
        } catch (HttpClientErrorException ex) {
            LOGGER.info("ohoho");
        }


        LOGGER.info("===================Kafka get Message ::: end===================");
    }


    public static boolean isResultStatusInstanceCheck(Object object) {
        return object instanceof ResultStatus;
    }
}
