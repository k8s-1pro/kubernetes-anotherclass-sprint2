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
        for (int i = 0; i < 10000000; i++) {
            if(i%10000==0) {
                log.info("memory overflow - i: {}", i);
            }
            memoryleakList.add(Math.random());
        }
    }

    public void cpuLoad() {

        log.info("cpuLoad is starting");

        int numCore = 4;
        int numThreadsPerCore = 2;
        double load = 0.8;
        final long duration = 600000;
        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new BusyThread("Thread" + thread, load, duration).start();
        }
    }

    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        System.out.printf("cpuLoad is sleept");
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
