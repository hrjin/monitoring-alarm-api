package org.insoft.monitoring.alarm.api.controller;

import org.insoft.monitoring.alarm.api.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author hrjin
 * @version 1.0
 * @since 2021.01.19
 **/
@RestController
@RequestMapping("/topics")
public class TopicsController {
    private final TopicsService topicsService;

    @Autowired
    public TopicsController(TopicsService topicsService) {
        this.topicsService = topicsService;
    }

    @GetMapping
    public List<String> getTopics() {
        return topicsService.getTopics();
    }

    @GetMapping("/{topicName}")
    public Map<String, Object> getTopic(@PathVariable("topicName") String topicName) {
        return topicsService.getTopic(topicName);
    }

    @GetMapping("/{topicName}/partitions")
    public List<Map<String, Object>> getTopicPartitions(@PathVariable("topicName") String topicName) {
        return topicsService.getTopicPartitions(topicName);
    }

    @GetMapping("/{topicName}/partitions/{partitionId)/offsets")
    public Map<String, Object> getTopicPartitionOffset(@PathVariable("topicName") String topicName, @PathVariable("partitionId") int partitionId) {
        return topicsService.getTopicPartitionOffset(topicName, partitionId);
    }
}
