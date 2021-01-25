package org.insoft.monitoring.alarm.api.model;

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
    private String resultCode;
    private String resultMessage;

    public ResultStatus(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
