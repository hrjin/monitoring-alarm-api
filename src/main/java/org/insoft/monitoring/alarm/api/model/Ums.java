package org.insoft.monitoring.alarm.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * UMS Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.21
 **/
@Data
@Builder
public class Ums {
    // 옵션
    private String umsTitle;
    private String umsMsg;
    private String sendNo;
    private List<String> rcvNos;

    // 옵션
    private String sendDate;

    private String linkNm;

    // 옵션
    private String umsKind;
}
