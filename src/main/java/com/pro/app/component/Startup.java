package com.pro.app.component;

import com.pro.app.service.DefaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Startup implements
        ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private DefaultService defaultService;

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            log.info("[SyStem] App is initializing");
            Thread.sleep(5*1000);
            log.info("[SyStem] Database is connecting");
            Thread.sleep(5*1000);
            log.info("[SyStem] Database is connected");
            Thread.sleep(5*1000);
            log.info("[SyStem] App is starting");
            Thread.sleep(5*1000);
            log.info("[SyStem] App is started");

            defaultService.isAppLive = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
