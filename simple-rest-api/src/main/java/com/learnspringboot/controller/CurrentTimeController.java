package com.learnspringboot.controller;

import com.learnspringboot.service.CurrentTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by Dilip on 1/7/18.
 */
@RestController
public class CurrentTimeController {

    @Autowired
    CurrentTimeService currentTimeService;

    @RequestMapping(value = "/currentTime")
    public String getCurrentDateTime() throws InterruptedException {
        System.out.println("controller getCurrentDateTime call"+ LocalDateTime.now().toString());
        return currentTimeService.getCurrentDateTime();

    }
}
