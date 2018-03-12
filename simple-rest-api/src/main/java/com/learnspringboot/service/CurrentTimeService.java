package com.learnspringboot.service;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by z001qgd on 1/7/18.
 */
@Service
public class CurrentTimeService {

    @Autowired
    Environment environment;

    public String getCurrentDateTime() throws InterruptedException {

        LocalDateTime localDateTime = getCurrentTime();
        System.out.println("Inside getCurrentDateTime" + LocalDateTime.now().toString());
        String response =  environment.getProperty("message").concat("\n").concat(localDateTime.toString());
        System.out.println("after getCurrentDateTime call"+ LocalDateTime.now().toString());
        return response;
    }

    @Async
    public LocalDateTime getCurrentTime() throws InterruptedException {
        System.out.println("Inside getCurrentTime"+ LocalDateTime.now().toString());
        Thread.sleep(5000);
        return LocalDateTime.now();

    }
}
