package org.insoft.monitoring.alarm.api.service;

import org.insoft.monitoring.alarm.api.common.Constants;
import org.insoft.monitoring.alarm.api.common.RestTemplateService;
import org.insoft.monitoring.alarm.api.model.Ems;
import org.insoft.monitoring.alarm.api.model.EmsDetail;
import org.insoft.monitoring.alarm.api.model.Ums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * EMS 및 UMS API 호출 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2021.01.21
 **/
@Service
public class SendEmsUmsService {
    private final RestTemplateService restTemplateService;

    @Autowired
    public SendEmsUmsService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public Object sendEms(EmsDetail emsDetail) {
        Ems ems = new Ems();
        ems.setData(emsDetail);
        return restTemplateService.sendMsg(Constants.TARGET_EMS_API, "/api/sendMail.do", HttpMethod.POST, ems, Object.class);
    }

    public Object sendUms(Ums ums) {
        return restTemplateService.sendMsg(Constants.TARGET_UMS_API, "/api/sendSms.do", HttpMethod.POST, ums, Object.class);
    }
}
