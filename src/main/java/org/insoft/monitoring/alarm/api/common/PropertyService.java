package org.insoft.monitoring.alarm.api.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Property Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 */
@Service
@Data
public class PropertyService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.instance.name}")
    private String instanceName;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String offsetReset;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String enableAutoCommit;

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    @Value("${ems.api}")
    private String emsApi;

    @Value("${ems.linkName}")
    private String emsLinkName;

    @Value("${ems.sendInfo}")
    private String emsSendInfo;

    @Value("${ems.receiver}")
    private String emsReceiver;

    @Value("${ums.api}")
    private String umsApi;

    @Value("${ums.linkName}")
    private String umsLinkName;

    @Value("${ums.sendNo}")
    private String sendNo;

    @Value("${ums.receiver}")
    private List<String> umsReceiver;

    @Value("${messaging.pool-size}")
    private int messagingPoolSize;

}
