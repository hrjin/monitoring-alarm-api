package org.insoft.monitoring.alarm.api.service;

import lombok.extern.slf4j.Slf4j;
import org.insoft.monitoring.alarm.api.common.Constants;
import org.insoft.monitoring.alarm.api.common.RestTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Topic 관리 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.19
 **/
@Slf4j
@Service
public class TopicsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicsService.class);
    private final RestTemplateService restTemplateService;

    @Autowired
    public TopicsService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public List<String> getTopics() {
        return restTemplateService.send(Constants.TARGET_KAFKA_API,"/topics", HttpMethod.GET, null, List.class);
    }

    public Map<String, Object> getTopic(String topicName) {
        return restTemplateService.send(Constants.TARGET_KAFKA_API,"/topics/" + topicName, HttpMethod.GET, null, Map.class);
    }

    public List<Map<String, Object>> getTopicPartitions(String topicName) {
        return restTemplateService.send(Constants.TARGET_KAFKA_API,"/topics/" + topicName + "/partitions", HttpMethod.GET, null, List.class);
    }

    public Map<String, Object> getTopicPartitionOffset(String topicName, int partitionId) {
        LOGGER.info("url ::: " + "/topics/" + topicName + "/partitions/" + partitionId + "/offsets");
        return restTemplateService.send(Constants.TARGET_KAFKA_API,"/topics/" + topicName + "/partitions/" + partitionId + "/offsets", HttpMethod.GET, null, Map.class);
    }
}
