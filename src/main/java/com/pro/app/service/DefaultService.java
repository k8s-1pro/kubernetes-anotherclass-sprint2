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

@Service
public class DefaultService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Autowired
    private DatasourceProperties datasourceProperties;
    public Boolean isAppLive = false;
    public Boolean isAppReady = false;


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


    public Boolean probeCheck(String type){

        if(type.equals("startup") || type.equals("liveness")) {
            log.info("[Kubernetes] {}Probe is {}-> [System] isAppLive: {}", type, isAppLive?"Succeed":"Failed", isAppLive);
            return isAppLive;
        } else  {
            log.info("[Kubernetes] {}Probe is {}-> [System] isAppReady: {}", type, isAppReady?"Succeed":"Failed", isAppReady);
            return isAppReady;
        }
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

    @Value(value = "${secret-path.postgresql}")
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


    public String listFiles(String path) {

        File file = new File(path);
        String[] files = file.list();
        String filenameList = "";
        if (files == null){
            return filenameList;
        }

        for (String filename : files) {
            filenameList =   filename + " " +  filenameList;
        }
        return filenameList;
    }

    public String createFile(String path) {

        // 10자리 임의 문자 만들기
        String randomStr = "";
        for( int i=0 ; i< 10 ; i++){
            char sValue = (char)((int)(Math.random()*26)+97);
            randomStr += String.valueOf(sValue);
        }
        log.info("File created:"  + path);
        // 폴더 생성
        File filePath = new File(path);
        if(!filePath.exists()) {
            filePath.mkdir();
        }
        // 문자로 파일명 생성
        String filename = path + randomStr + ".txt";
        File file = new File(filename);
        try {
            if (file.createNewFile()) {
                log.info("File created");
            } else {
                log.info("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listFiles(path);
    }

}
