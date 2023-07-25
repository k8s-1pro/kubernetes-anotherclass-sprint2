package com.pro.app.controller;

import com.pro.app.domain.DatasourceProperties;
import com.pro.app.service.DefaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private DatasourceProperties datasourceProperties;

    @Autowired
    private DefaultService defaultService;

    @Value(value = "${filepath.persistent-volume-data}")
    private String filepathPersistentVolumeData;

    @Value(value = "${filepath.pod-volume-data}")
    private String filepathPodVolumeData;

    @Value(value = "${application.role}")
    private String applicationRole;
    @Value(value = "${application.version}")
    private String applicationVersion;
    @Value(value = "${spring.profiles.active}")
    private String applicationProfile;

    public DefaultController() {
    }

    @GetMapping("/hello")
    public String hello(){
        return "Welcome to Kubernetes Another Class";
    }

    @GetMapping("/ready")
    public String ready(){
        return "ok";
    }

    @GetMapping("/startup")
    @ResponseBody
    public ResponseEntity<Object> startup() {
        if(defaultService.probeCheck("startup")){
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/readiness")
    @ResponseBody
    public ResponseEntity<Object> readiness() {
        if(defaultService.probeCheck("readiness")){
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/liveness")
    @ResponseBody
    public ResponseEntity<Object> liveness() {
        if(defaultService.probeCheck("liveness")){
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/server-error")
    @ResponseBody
    public ResponseEntity<Object> serverError() {
        defaultService.isAppLive = false;
        log.info("[SyStem] An error occurred on the server");
        return ResponseEntity.ok("ok");
    }
    @GetMapping("/server-load-on")
    @ResponseBody
    public ResponseEntity<Object> serverLoadOn() {
        defaultService.isAppReady = false;
        defaultService.isAppLive = false;
        log.info("[SyStem] The system load has occurred");
        return ResponseEntity.ok("ok");
    }
    @GetMapping("/server-load-off")
    @ResponseBody
    public ResponseEntity<Object> serverLoadOff() {
        defaultService.isAppReady = true;
        defaultService.isAppLive = true;
        log.info("[SyStem] The System load has decreased");
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

    @GetMapping("/info")
    public ResponseEntity<Object> applicationInfo(){
        String returnString = "<b>[Version] :</b> " + applicationVersion ;
               returnString += "<br><b>[Profile] :</b> " + applicationProfile;
               returnString += "<br><b>[Role] :</b> " + applicationRole + " (option: ALL, GET, POST, PUT, DELETE)" ;
               returnString += "<br><b>[Database]</b>"
                             + "<br>driver-class-name : " + datasourceProperties.getDriverClassName()
                             + "<br>url : "  + datasourceProperties.getUrl()
                             + "<br>username : "  + datasourceProperties.getUsername()
                             + "<br>password : "  + datasourceProperties.getPassword();
        return ResponseEntity.ok(returnString);
    }

    @GetMapping("/properties")
    public ResponseEntity<Object> properties(){
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
                + "<br>&nbsp;&nbsp;pod-volume-data:"
                + "<br><br>"
                + "<br><b>application-dev.yaml :</b> Dev properties"
                + "<br>---"
                + "<br>filepath:"
                + "<br>&nbsp;&nbsp;persistent-volume-data: \"/usr/src/myapp/dev/files/\""
                + "<br>&nbsp;&nbsp;pod-volume-data: \"/usr/src/myapp/dev/tmp/\""
                + "<br><br>"
                + "<br><b>application-qa.yaml :</b> QA properties"
                + "<br>---"
                + "<br>filepath:"
                + "<br>&nbsp;&nbsp;persistent-volume-data: \"/usr/src/myapp/qa/files/\""
                + "<br>&nbsp;&nbsp;pod-volume-data: \"/usr/src/myapp/qa/tmp/\""
                + "<br><br>"
                + "<br><b>application-prod.yaml :</b> Prod properties"
                + "<br>---"
                + "<br>filepath:"
                + "<br>&nbsp;&nbsp;persistent-volume-data: \"/usr/src/myapp/prod/files/\""
                + "<br>&nbsp;&nbsp;pod-volume-data: \"/usr/src/myapp/prod/tmp/\"";
        return ResponseEntity.ok(returnString);
    }


    @GetMapping(value="/create-file-pv")
    @ResponseBody
    public ResponseEntity<Object> createFilePv() {
        String filenameList = defaultService.createFile(filepathPersistentVolumeData);
        return ResponseEntity.ok(filenameList);
    }

    @GetMapping(value="/list-file-pv")
    @ResponseBody
    public ResponseEntity<Object> listFilePv() {
        String filenameList = defaultService.listFiles(filepathPersistentVolumeData);
        return ResponseEntity.ok(filenameList);
    }

    @GetMapping(value="/create-file-pod")
    @ResponseBody
    public ResponseEntity<Object> createFilePod() {
        String filenameList = defaultService.createFile(filepathPodVolumeData);
        return ResponseEntity.ok(filenameList);
    }

    @GetMapping(value="/list-file-pod")
    @ResponseBody
    public ResponseEntity<Object> listFilePod() {
        String filenameList = defaultService.listFiles(filepathPodVolumeData);
        return ResponseEntity.ok(filenameList);
    }
}
