package com.pro.app.service;

import com.pro.app.component.CpuLoad;
import com.pro.app.component.ObjectForLeak;
import com.pro.app.domain.DatasourceProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class DefaultService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Autowired
    private DatasourceProperties datasourceProperties;


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

    @Value(value = "${filepath.postgresql}")
    private String filepathPostgresql;

    @PostConstruct
    public void datasourceSecretLoad() {
        Yaml y = new Yaml();
        Reader yamlFile = null;
        try {
            yamlFile = new FileReader(filepathPostgresql);
        } catch (FileNotFoundException e) {

        }

        if (yamlFile != null) {
            Map<String, Object> yamlMaps = y.load(yamlFile);

            datasourceProperties.setDriverClassName(yamlMaps.get("driver-class-name").toString());
            datasourceProperties.setUrl(yamlMaps.get("url").toString());
            datasourceProperties.setUsername(yamlMaps.get("username").toString());
            datasourceProperties.setPassword(yamlMaps.get("password").toString());
        }
    }

    @Value(value = "${filepath.persistent-volume-data}")
    private String filepathPersistentVolumeData;
    public String getPersistentVolumeFiles() {


        File file = new File(filepathPersistentVolumeData);
        String[] files = file.list();
        String filenameList = "";
        for (String filename : files) {
            filenameList =   filename + " " +  filenameList;
        }
        return filenameList;
    }

    public String createPersistentVolumeFile() {

        String randomStr = "";

        for( int i=0 ; i< 10 ; i++){
            char sValue = (char)((int)(Math.random()*26)+97);
            randomStr += String.valueOf(sValue);
        }


        String filename = filepathPersistentVolumeData + randomStr + ".txt";

        System.out.println(filename);

        File file = new File(filename);

        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getPersistentVolumeFiles();
    }

}
