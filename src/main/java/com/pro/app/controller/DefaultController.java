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
    @Value(value = "${spring.profiles.active}")
    private String applicationProfile;


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
    public ResponseEntity<Object> applicationRole(){
        String returnString = "[Application Role] : " + applicationRole + "<br> (option: ALL, GET, POST, PUT, DELETE)";
        return ResponseEntity.ok(returnString);
    }
    @GetMapping("/profile")
    public ResponseEntity<Object> applicationProfile(){
        String returnString = "[Application profile] : " + applicationProfile
                + "<br><br><b>application.yaml :</b> Common properties"
                + "<br>---"
                + "<br>datasource:"
                + "<br>&nbsp;&nbsp;driver-class-name:"
                + "<br>&nbsp;&nbsp;url:"
                + "<br>&nbsp;&nbsp;username:"
                + "<br>&nbsp;&nbsp;password:"
                + "<br>filepath:"
                + "<br>&nbsp;&nbsp;postgresql: "
                + "<br>&nbsp;&nbsp;persistent-volume-data:"
                + "<br>&nbsp;&nbsp;temp-volume-data:"
                + "<br><br>"
                + "<br><b>application-dev.yaml :</b> Dev properties"
                + "<br>---"
                + "<br>filepath:"
                + "<br>&nbsp;&nbsp;persistent-volume-data: \"/usr/src/myapp/dev/files/\""
                + "<br>&nbsp;&nbsp;temp-volume-data: \"/usr/src/myapp/dev/tmp/\""
                + "<br><br>"
                + "<br><b>application-qa.yaml :</b> QA properties"
                + "<br>---"
                + "<br>filepath:"
                + "<br>&nbsp;&nbsp;persistent-volume-data: \"/usr/src/myapp/qa/files/\""
                + "<br>&nbsp;&nbsp;temp-volume-data: \"/usr/src/myapp/qa/tmp/\""
                + "<br><br>"
                + "<br><b>application-prod.yaml :</b> Prod properties"
                + "<br>---"
                + "<br>filepath:"
                + "<br>&nbsp;&nbsp;persistent-volume-data: \"/usr/src/myapp/prod/files/\""
                + "<br>&nbsp;&nbsp;temp-volume-data: \"/usr/src/myapp/prod/tmp/\"";
        return ResponseEntity.ok(returnString);
    }
    @GetMapping("/version")
    public ResponseEntity<Object> applicationVersion(){
        String returnString = "[Application Version] : " + applicationVersion ;
        return ResponseEntity.ok(returnString);
    }

    @GetMapping(value="/database-info")
    @ResponseBody
    public ResponseEntity<Object> databaseInfo() {
        String returnString = "[Database Properties]"
                            + "<br>driver-class-name : " + datasourceProperties.getDriverClassName()
                            + "<br>url : "  + datasourceProperties.getUrl()
                            + "<br>username : "  + datasourceProperties.getUsername()
                            + "<br>password : "  + datasourceProperties.getPassword();
        return ResponseEntity.ok(returnString);
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
