package com.pro.app.component;

public class CpuLoad extends Thread  {

    private double load;
    private long duration;


    public CpuLoad(String name, double load, long duration) {
        super(name);
        this.load = load;
        this.duration = duration;
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - startTime < duration) {
                if (System.currentTimeMillis() % 100 == 0) {
                    Thread.sleep((long) Math.floor((1 - load) * 100));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
