package org.insoft.monitoring.alarm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonitoringAlarmApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringAlarmApiApplication.class, args);
	}

}
