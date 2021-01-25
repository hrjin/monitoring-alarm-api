package org.insoft.monitoring.alarm.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hrjin
 * @version 1.0
 * @since 2021.01.21
 **/
@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/hello")
    public Object hello(HttpServletRequest request) {
        Object obj = null;
        LOGGER.info("scheme ::: " + request.getScheme());
        return obj;
    }
}
