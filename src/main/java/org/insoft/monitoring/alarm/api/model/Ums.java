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
    private String msgTitle;
    private String umsMsg;
    private String sendNo;
    private List<String> rcvNos;
    private String sendDate;
    private String linkNm;
    private String umsKind;
}
