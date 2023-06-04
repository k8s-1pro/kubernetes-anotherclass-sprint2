package com.pro.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class DefaultController {
    @RequestMapping("hello")
    public String hello(){
        return "Test";
    }

    @RequestMapping("hostname")
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
}
