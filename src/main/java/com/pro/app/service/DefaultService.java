package com.pro.app.service;

import com.pro.app.component.CpuLoad;
import com.pro.app.component.ObjectForLeak;
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

    static List<ObjectForLeak> leak = new ArrayList<>();


    public void memoryLeak() {

        log.info("{} : memoryLeak is starting", this.hostname());

   //     MemoryLeak memoryLeak = new MemoryLeak();
    //    memoryLeak.run();


        while(true) {
            leak.add(new ObjectForLeak());
        }

    }


    public void cpuLoad(int min, int thread) {

        final long duration = min*60*1000;  // 3분동안

        log.info("{} : cpuLoad is starting ({} min)", this.hostname(), duration);

        double load = 0.8;  // 부하를 80%정도로 유지하도록 설정

        for (int cnt = 0; cnt < thread; cnt++) {
            new CpuLoad("Thread" + cnt, load, duration).start();
        }
        log.info("{} : cpuLoad is done", this.hostname());
    }


}
