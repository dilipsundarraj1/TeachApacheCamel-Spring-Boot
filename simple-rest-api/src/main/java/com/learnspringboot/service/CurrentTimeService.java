package com.learnspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by z001qgd on 1/7/18.
 */
@Service
public class CurrentTimeService {

    @Autowired
    Environment environment;

    public String getCurrentDateTime() {

        LocalDateTime localDateTime = LocalDateTime.now();
        return environment.getProperty("message").concat("\n").concat(localDateTime.toString());
    }
}
