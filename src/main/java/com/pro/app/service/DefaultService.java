package com.pro.app.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DefaultService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public static List<Double> memoryleakList = new ArrayList<>();

    @PostConstruct
    public void startupTime() {

        // App 기동시간 (10초에서 20초가 걸리도록)
        Random random = new Random();
        int min = 1000*1;
        int max = 1000*2;

        int randomCnt = random.nextInt(max - min + 1) + min;

        log.info("App is starting");
        try {
            Thread.sleep(randomCnt);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("App is started : {} Sec", randomCnt / 1000);
    }

    public void memoryLeak() {
        log.info("memoryLeak is starting");
        for (int i = 0; i < 1000000; i++) {
            if(i%10==0) {
                log.info("memory overflow - i: {}", i);
            }
            memoryleakList.add(Math.random());
        }
    }
}
