package org.insoft.monitoring.alarm.api.exception;

import org.insoft.monitoring.alarm.api.common.CommonStatusCode;
import org.insoft.monitoring.alarm.api.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

/**
 * GlobalController Exception Handler 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 **/
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseBody
    public ErrorMessage handleException(HttpClientErrorException ex) {
        LOGGER.info("HttpClientErrorException >>> " + ex.getLocalizedMessage());
        for (CommonStatusCode code : CommonStatusCode.class.getEnumConstants()) {
            if(code.getCode() == ex.getRawStatusCode()) {
                return new ErrorMessage(Constants.RESULT_STATUS_FAIL, code.getMsg(), code.getCode(), code.getMsg());
            }
        }

        return new ErrorMessage(Constants.RESULT_STATUS_FAIL, ex.getStatusText(), ex.getRawStatusCode(), ex.getResponseBodyAsString());
    }
}

