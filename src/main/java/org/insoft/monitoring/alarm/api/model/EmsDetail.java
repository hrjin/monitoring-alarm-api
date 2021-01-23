package org.insoft.monitoring.alarm.api.model;

import lombok.Builder;
import lombok.Data;

/**
 * EMS Detail 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.21
 **/
@Data
@Builder
public class EmsDetail {
    private String title;
    private String content;
    private String sendInfo;
    private String rcvInfo;

    // 옵션
    private String sendDate;

    // 옵션
    private String sendType;
    private String categoryNm;
    private String linkNm;

    // 옵션
    private String memo;
}
