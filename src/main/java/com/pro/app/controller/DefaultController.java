package com.pro.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pro.app.domain.Database;
import com.pro.app.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class DefaultController {

    @Autowired
    private Database databaseProperties;

    @Autowired
    private DefaultService defaultService;

    @GetMapping("hello")
    public String hello(){
        return "Hello Kubernetes!";
    }

    @GetMapping("/hostname")
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


    @GetMapping("/memory-leak")
    public void memoryLeak(){
        defaultService.memoryLeak();
    }

    @GetMapping(value="/database", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object>  database() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return ResponseEntity.ok(databaseProperties);
    }
}
