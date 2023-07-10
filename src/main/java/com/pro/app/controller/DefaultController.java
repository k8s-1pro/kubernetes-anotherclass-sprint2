package com.pro.app.controller;

import com.pro.app.domain.DatasourceProperties;
import com.pro.app.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("hello")
    public String hello(){
        return "Hello Kubernetes!";
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


    @GetMapping(value="/database")
    @ResponseBody
    public ResponseEntity<Object> database() {
        String name = datasourceProperties.getDriverClassName();
        return ResponseEntity.ok(datasourceProperties.toString());
    }

}
