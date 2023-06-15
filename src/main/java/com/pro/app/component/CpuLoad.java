package com.pro.app.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CpuLoad {

    private double load;
    private long duration;
    private String name;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public CpuLoad(String name, double load, long duration) {
        this.name = name;
        this.load = load;
        this.duration = duration;
    }


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
