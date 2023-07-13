package com.pro.app.controller;

import com.pro.app.domain.DatasourceProperties;
import com.pro.app.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @Autowired
    private DatasourceProperties datasourceProperties;

    @Autowired
    private DefaultService defaultService;

    @Value(value = "${filepath.persistent-volume-data}")
    private String filepathPersistentVolumeData;

    @Value(value = "${filepath.temp-volume-data}")
    private String filepathTempVolumeData;

    @Value(value = "${application.role}")
    private String applicationRole;
    @Value(value = "${application.version}")
    private String applicationVersion;


    @GetMapping("/hello")
    public String hello(){
        return "Hello! I'm 1pro!";
    }

    @GetMapping("/ready")
    @ResponseBody
    public ResponseEntity<Object> ready() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/hostname")
    public String hostname(){
        return defaultService.hostname();
    }

    @GetMapping("/memory-leak")
    public void memoryLeak(){
        defaultService.memoryLeak();
    }

    @GetMapping("/cpu-load")
    public void cpuLoad(@RequestParam(defaultValue = "2") int min, @RequestParam(defaultValue = "10") int thread){
        defaultService.cpuLoad(min, thread);
    }



    @GetMapping("/application-role")
    public String applicationRole(){
        String returnString = "[Application Role] \nCurrent : " + applicationRole + "\nDefault : ALL \nOption: GET, POST, PUT, DELETE";
        return  returnString;
    }
    @GetMapping("/version")
    public String applicationVersion(){
        return applicationVersion;
    }

    @GetMapping(value="/database-info")
    @ResponseBody
    public ResponseEntity<Object> databaseInfo() {
        String name = datasourceProperties.getDriverClassName();
        return ResponseEntity.ok(datasourceProperties.toString());
    }

    @GetMapping(value="/create-pv-file")
    @ResponseBody
    public ResponseEntity<Object> createPvFile() {
        String filenameList = defaultService.createVolumeFile(filepathPersistentVolumeData);
        return ResponseEntity.ok(filenameList);
    }

    @GetMapping(value="/list-pv-file")
    @ResponseBody
    public ResponseEntity<Object> listPvFile() {
        String filenameList = defaultService.getVolumeFiles(filepathPersistentVolumeData);
        return ResponseEntity.ok(filenameList);
    }

    @GetMapping(value="/create-temp-file")
    @ResponseBody
    public ResponseEntity<Object> createTempFile() {
        String filenameList = defaultService.createVolumeFile(filepathTempVolumeData);
        return ResponseEntity.ok(filenameList);
    }

    @GetMapping(value="/list-temp-file")
    @ResponseBody
    public ResponseEntity<Object> listTempFile() {
        String filenameList = defaultService.getVolumeFiles(filepathTempVolumeData);
        return ResponseEntity.ok(filenameList);
    }
}
