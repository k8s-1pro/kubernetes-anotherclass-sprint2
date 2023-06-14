package com.pro.app.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DefaultService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public static List<Double> memoryleakList = new ArrayList<>();


    public String hostname(){
        String hostname = "";
        InetAddress inetadd = null;
        try {
            inetadd = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        hostname = inetadd.getHostName();
        return hostname;
    }


    @PostConstruct
    public void startupTime() {

        // App 기동시간 (5초에서 10초가 걸리도록)
        Random random = new Random();
        int min = 1000*5;
        int max = 1000*10;

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
        log.info("{} : memoryLeak is starting", this.hostname());
        for (int i = 0; i < 10000000; i++) {
            if(i%10000==0) {
                log.info("memory overflow - cnt: {}", i);
            }
            memoryleakList.add(Math.random());
        }
    }


    public void cpuLoad() {

        log.info("{} : cpuLoadForOnePod is starting", this.hostname());

        final long duration = 60*1000;  // 1분동안
        double load = 0.9;  // 부하를 90%정도로 유지하도록 설정

        for (int thread = 0; thread < 6; thread++) {
            new CpuLoad("Thread" + thread, load, duration).start();
        }
        log.info("{} : cpuLoadForOnePod is done", this.hostname());
    }


    private static class CpuLoad extends Thread {
        private double load;
        private long duration;
        private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

        public CpuLoad(String name, double load, long duration) {
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
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
