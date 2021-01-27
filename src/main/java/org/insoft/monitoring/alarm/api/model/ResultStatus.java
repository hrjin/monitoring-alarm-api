package org.insoft.monitoring.alarm.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Result Status model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.20
 **/
@Data
public class ResultStatus {
    @JsonProperty("error_code")
    private int errorCode;
    private String message;

    public ResultStatus(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
