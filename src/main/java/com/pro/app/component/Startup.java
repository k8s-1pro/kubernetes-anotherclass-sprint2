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
            log.info("[System] App is initializing");
            Thread.sleep(5*1000);
            log.info("[System] Database is connecting");
            Thread.sleep(5*1000);
            log.info("[System] Database is connected");
            Thread.sleep(5*1000);
            log.info("[System] App is starting");
            Thread.sleep(5*1000);
            log.info("[System] App is started");
            Thread.sleep(2*1000);
            defaultService.isAppLive = true;
            Thread.sleep(5*1000);
            log.info("[System] ConfigMap data is loading..");
            Thread.sleep(5*1000);
            log.info("[System] ConfigMap data is loading..");
            Thread.sleep(5*1000);
            log.info("[System] ConfigMap data is loading..");
            Thread.sleep(5*1000);
            log.info("[System] Data loading is completed");
            Thread.sleep(2*1000);
            defaultService.isAppReady = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
