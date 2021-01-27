package org.insoft.monitoring.alarm.api.common;

/**
 * Constants 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 */
public class Constants {

    public static final String RESULT_STATUS_SUCCESS = "SUCCESS";
    public static final String RESULT_STATUS_FAIL = "FAIL";

    public static final String TARGET_KAFKA_API = "kafkaApi";
    public static final String TARGET_UMS_API = "umsApi";
    public static final String TARGET_EMS_API = "emsApi";

    public static final String DEFAULT_EMS_TITLE = "PaaS-TA 이메일 테스트입니다.";
    public static final String DEFAULT_UMS_TITLE = "PaaS-TA SMS 테스트입니다.";

    public static final String DATA_KEY = "value";
    public static final String DATA_DESC_KEY = "description";
    public static final String DATA_DETAILS_KEY = "details";

    public static final String STRING_DATE_TYPE = "yyyy-MM-dd HH:mm:ss";
    public static final String STRING_TIME_ZONE_ID = "Asia/Seoul";

    public Constants() {
        throw new IllegalStateException();
    }

}
