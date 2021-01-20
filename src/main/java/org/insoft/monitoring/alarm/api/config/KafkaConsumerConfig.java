//package org.insoft.monitoring.alarm.api.config;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author hrjin
// * @version 1.0
// * @since 2021.01.18
// **/
//@EnableKafka
//@Configuration
//public class KafkaConsumerConfig {
//    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String kafkaServer;
//
//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//
//    @Value("${spring.kafka.consumer.auto-offset-reset}")
//    private String offsetReset;
//
//    @Value("${spring.kafka.consumer.key-deserializer}")
//    private String keyDeserializer;
//
//    @Value("${spring.kafka.consumer.value-deserializer}")
//    private String valueDeserializer;
//
//    @Value("${spring.kafka.consumer.enable-auto-commit}")
//    private String enableAutoCommit;
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        logger.info("server :::  " + kafkaServer);
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
//
//        return new DefaultKafkaConsumerFactory<>(props);
//
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//
////    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
////    public void consume(@Headers MessageHeaders headers, @Payload String payload) {
////        logger.info("CONSUME HEADERS : " + headers.toString());
////        logger.info("CONSUME PAYLOAD : " + payload);
////    }
//}
