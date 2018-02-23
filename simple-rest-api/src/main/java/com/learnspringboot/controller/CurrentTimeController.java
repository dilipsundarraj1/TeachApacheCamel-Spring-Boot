package com.learnspringboot.controller;

import com.learnspringboot.service.CurrentTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by z001qgd on 1/7/18.
 */
@RestController
public class CurrentTimeController {

    @Autowired
    CurrentTimeService currentTimeService;

    @RequestMapping(value = "/currentTime")
    public String getCurrentDateTime(){

        return currentTimeService.getCurrentDateTime();

    }
}
