package org.insoft.monitoring.alarm.api.config;

import org.insoft.monitoring.alarm.api.common.Constants;
import org.insoft.monitoring.alarm.api.common.PropertyService;
import org.insoft.monitoring.alarm.api.model.EmsDetail;
import org.insoft.monitoring.alarm.api.model.ResultEmsUms;
import org.insoft.monitoring.alarm.api.model.ResultStatus;
import org.insoft.monitoring.alarm.api.model.Ums;
import org.insoft.monitoring.alarm.api.service.ConsumerService;
import org.insoft.monitoring.alarm.api.service.SendEmsUmsService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Base64.Decoder;

/**
 * Kafka Scheduling 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 **/

@Component
public class ScheduledTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);
    private final ConsumerService consumerService;
    private final PropertyService propertyService;
    private final SendEmsUmsService sendEmsUmsService;

    @Autowired
    public ScheduledTask(ConsumerService consumerService, PropertyService propertyService, SendEmsUmsService sendEmsUmsService) {
        this.consumerService = consumerService;
        this.propertyService = propertyService;
        this.sendEmsUmsService = sendEmsUmsService;
    }

    @PostConstruct
    public void init() {
        createConsumerInstance();
    }


    /**
     * Consumer Instance 유뮤 확인 후 없을 시 생성 및 Topic 구독
     *
     */
    public void createConsumerInstance() {
        LOGGER.info("Init Method!!!");

        // 1. consumer가 있는지 확인
        Object rsMap = consumerService.getSubscription(propertyService.getGroupId(), propertyService.getInstanceName());
        if(isResultStatusCheck(rsMap)) {
            LOGGER.info("Consumer Instance isn't exist...");

            // 2. 없으면 만들어줘요
            Map<String, String> params = new HashMap<>();
            params.put("name", propertyService.getInstanceName());
            params.put("format", "binary");
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


    /**
     * Get Kafka Message Periodically
     *
     */
    @Scheduled(fixedRateString = "${messaging.ready-fixed-rate}", initialDelayString = "${messaging.ready-initial-delay}")
    public void readyGetMessaging() {
        LOGGER.info("===================Kafka get Message & Sending Email, SMS ::: start===================");

        try {
            Object obj = consumerService.getMessage(propertyService.getGroupId(), propertyService.getInstanceName());
            if(isResultStatusCheck(obj)) {
                LOGGER.info("Message Get Error");
                return;
            }

            List<Map> resultList = (List<Map>) obj;
            LOGGER.info("resultList ::: " + resultList.toString());
            LOGGER.info("resultList size ::: " + resultList.size());

            if(resultList.size() > 0) {
                for (Map map : resultList) {
                    String content = "";
                    String trigger = "";
                    LOGGER.info("result ::: " + map);

                    // Base64 value
                    String value = (String) map.get(Constants.DATA_KEY);

                    // Base64 value to Json Object
                    JSONObject resultJson = decodeBase64String(value);

                    LOGGER.info("result >>> " + value);
                    LOGGER.info("result json >>> " + resultJson);

                    String currentDateTime = LocalDateTime.now(ZoneId.of(Constants.STRING_TIME_ZONE_ID)).format(DateTimeFormatter.ofPattern(Constants.STRING_DATE_TYPE));
                    
                    String title = null;
                    String finalMsg = currentDateTime + "에 ";
                    
                    if (value != null) {
                        // alarm이 발생한 VM info
                        trigger = resultJson.get(Constants.DATA_DETAILS_KEY).toString();
                        LOGGER.info("trigger ::: " + trigger);

                        // 최종 메시지
                        finalMsg += trigger + " 에서";
                        content = resultJson.get(Constants.DATA_DESC_KEY).toString();
                        LOGGER.info("content ::: " + content);

                        if(content.contains("-")) {
                            String[] desc = content.split("-");
                            title = desc[0];
                            finalMsg += desc[1];
                        }
                    }
                    LOGGER.info("title ::: " + title);
                    LOGGER.info("finalMsg ::: " + finalMsg);
                    
                    String emsResultMsg = callEmsApi(title, finalMsg);
                    LOGGER.info("Email Sending Result ::: " + emsResultMsg);

                    String umsResultMsg = callUmsApi(title, finalMsg);
                    LOGGER.info("SMS Sending Result ::: " + umsResultMsg);

                }
            }

        } catch (HttpClientErrorException ex) {
            LOGGER.info("HttpClientErrorException...");
            LOGGER.info(ex.getLocalizedMessage());
        } catch (NullPointerException ex) {
            LOGGER.info("Null...");
            LOGGER.info(ex.getLocalizedMessage());
        } catch (ClassCastException ex) {
            LOGGER.info("Casting Exception...");
            LOGGER.info(ex.getLocalizedMessage());
        } catch (ParseException e) {
            LOGGER.info("Parse Exception...");
        }

        LOGGER.info("===================Kafka get Message & Sending Email, SMS  ::: end===================");
    }


    /**
     * Error 일 경우 Error 객체로 리턴됐는지 확인
     *
     * @param object the object
     * @return the boolean
     */
    public static boolean isResultStatusCheck(Object object) {
        return object instanceof ResultStatus;
    }


    /**
     * EMS 전송 API 호출
     *
     * @param content the content
     * @return the String
     */
    private String callEmsApi(String title, String content) {
        LOGGER.info("Email send start!!!");
        EmsDetail emsDetail = EmsDetail.builder()
                .title(title)
                .content(content)
                .sendInfo(propertyService.getEmsSendInfo())
                .rcvInfo(propertyService.getEmsReceiver())
                .categoryNm("전체공지")
                .linkNm(propertyService.getEmsLinkName()).build();

        // EMS api 서버 호출
        ResultEmsUms resultEms = sendEmsUmsService.sendEms(emsDetail);
        LOGGER.info("result Ems :: " + resultEms.getResult().getResult());

        return resultEms.getResult().getResult();
    }


    /**
     * UMS 전송 API 호출
     *
     * @param content the content
     * @return the String
     */
    private String callUmsApi(String title, String content) {
        LOGGER.info("SMS send start!!!");
        LOGGER.info("send no ::: " + propertyService.getSendNo());

        for (String num:propertyService.getUmsReceiver()) {
            LOGGER.info("receiver no ::: " + num);
        }

        Ums ums = Ums.builder()
                .msgTitle(title)
                .umsMsg(content)
                .sendNo(propertyService.getSendNo())
                .rcvNos(propertyService.getUmsReceiver())
                .linkNm(propertyService.getUmsLinkName()).build();

        // UMS api 서버 호출
        ResultEmsUms resultUms = sendEmsUmsService.sendUms(ums);
        LOGGER.info("result Ums :: " + resultUms.getResult().getResult());

        return resultUms.getResult().getResult();
    }


    /**
     * Base64 to Json Object
     *
     * @param value the value
     * @return the Json Object
     * @throws ParseException
     */
    private JSONObject decodeBase64String(String value) throws ParseException {
        Decoder decoder = Base64.getDecoder();
        byte[] decodeBytes = decoder.decode(value);
        String stringFrmJsonByteArray = new String(decodeBytes);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(stringFrmJsonByteArray);
        JSONObject jsonObj = (JSONObject) obj;

        return jsonObj;
    }
}
