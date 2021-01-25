package org.insoft.monitoring.alarm.api.common;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Common Service 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 */
@Service
public class CommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
    private final Gson gson;
    private final PropertyService propertyService;

    /**
     * Instantiates a new Common service
     *
     * @param gson the gson
     */
    @Autowired
    public CommonService(Gson gson, PropertyService propertyService) {
        this.gson = gson;
        this.propertyService = propertyService;
    }


    /**
     * result object 설정(Set result object)
     *
     * @param <T>           the type parameter
     * @param requestObject the request object
     * @param requestClass  the request class
     * @return the result object
     */
    public <T> T setResultObject(Object requestObject, Class<T> requestClass) {
        return this.fromJson(this.toJson(requestObject), requestClass);
    }


    /**
     * json string 으로 변환(To json string)
     *
     * @param requestObject the request object
     * @return the string
     */
    private String toJson(Object requestObject) {
        return gson.toJson(requestObject);
    }


    /**
     * json 에서 t로 변환(From json t)
     *
     * @param <T>           the type parameter
     * @param requestString the request string
     * @param requestClass  the request class
     * @return the t
     */
    private <T> T fromJson(String requestString, Class<T> requestClass) {
        return gson.fromJson(requestString, requestClass);
    }

}