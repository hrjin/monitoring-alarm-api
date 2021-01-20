package org.insoft.monitoring.alarm.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Rest Template Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.08.26
 */
@Service
public class RestTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateService.class);
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String CONTENT_TYPE = "Content-Type";

    private final RestTemplate restTemplate;
    private final PropertyService propertyService;

    private String base64Authorization;


    /**
     * Instantiates a new Rest template service
     * @param restTemplate                   the rest template
     * @param propertyService                the property service
     */
    @Autowired
    public RestTemplateService(RestTemplate restTemplate, PropertyService propertyService) {
        this.restTemplate = restTemplate;
        this.propertyService = propertyService;
//
//        this.commonApiBase64Authorization = "Basic "
//                + Base64Utils.encodeToString(
//                (commonApiAuthorizationId + ":" + commonApiAuthorizationPassword).getBytes(StandardCharsets.UTF_8));
    }


    public <T> T send(String reqUrl, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {
        return send(reqUrl, httpMethod, bodyObject, responseType, "application/vnd.kafka.v2+json, application/vnd.kafka+json, application/json", "application/vnd.kafka.v2+json");
    }

    public <T> T getMsg(String reqUrl, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {
        return send(reqUrl, httpMethod, bodyObject, responseType, "application/vnd.kafka.json.v2+json", "application/vnd.kafka.v2+json");
    }



    /**
     * t 전송(Send t)
     *
     * @param <T>          the type parameter
     * @param reqUrl       the req url
     * @param httpMethod   the http method
     * @param bodyObject   the body object
     * @param responseType the response type
     * @param acceptType   the accept type
     * @param contentType  the content type
     * @return the t
     */
    public <T> T send(String reqUrl, HttpMethod httpMethod, Object bodyObject, Class<T> responseType, String acceptType, String contentType) {
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(CONTENT_TYPE, contentType);
        reqHeaders.add("ACCEPT", acceptType);

        HttpEntity<Object> reqEntity;
        if (bodyObject == null) {
            reqEntity = new HttpEntity<>(reqHeaders);
        } else {
            reqEntity = new HttpEntity<>(bodyObject, reqHeaders);
        }

        LOGGER.info("<T> T SEND :: REQUEST: {} BASE-URL: {}, CONTENT-TYPE: {}", httpMethod, reqUrl, reqHeaders.get(CONTENT_TYPE));
        ResponseEntity<T> resEntity = restTemplate.exchange(propertyService.getKafkaServer() + reqUrl, httpMethod, reqEntity, responseType);

        if (resEntity.getBody() != null) {
            LOGGER.info("RESPONSE-TYPE: {}", resEntity.getBody().getClass());
        } else {
            LOGGER.info("RESPONSE-TYPE: RESPONSE BODY IS NULL");
        }

        return resEntity.getBody();
    }

}
