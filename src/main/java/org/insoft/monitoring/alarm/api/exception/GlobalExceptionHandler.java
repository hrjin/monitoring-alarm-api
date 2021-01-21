package org.insoft.monitoring.alarm.api.exception;

import org.insoft.monitoring.alarm.api.common.CommonStatusCode;
import org.insoft.monitoring.alarm.api.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * GlobalException Handler 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 **/
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseBody
    public ErrorMessage handleException(HttpClientErrorException ex) {
        LOGGER.info("HttpClientErrorException >>> " + ex.getStatusText());
        for (CommonStatusCode code : CommonStatusCode.class.getEnumConstants()) {
            if(code.getCode() == ex.getRawStatusCode()) {
                return new ErrorMessage(Constants.RESULT_STATUS_FAIL, code.getMsg(), code.getCode(), code.getMsg());
            }
        }

        return new ErrorMessage(Constants.RESULT_STATUS_FAIL, ex.getStatusText(), ex.getRawStatusCode(), ex.getResponseBodyAsString());
    }


    @ExceptionHandler({Exception.class})
    public ErrorMessage handleAll(final Exception ex) {
        if(ex.getMessage().contains("404")) {
            return new ErrorMessage(Constants.RESULT_STATUS_FAIL, CommonStatusCode.NOT_FOUND.getMsg(), HttpStatus.NOT_FOUND.value(), CommonStatusCode.NOT_FOUND.getMsg());
        }

        LOGGER.info("Exception >>> {}", ex.getLocalizedMessage());
        return new ErrorMessage(Constants.RESULT_STATUS_FAIL, CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR.value(), CommonStatusCode.INTERNAL_SERVER_ERROR.getMsg());
    }

}
