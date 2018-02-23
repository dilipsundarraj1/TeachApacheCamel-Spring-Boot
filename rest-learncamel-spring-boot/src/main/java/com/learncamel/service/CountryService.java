package com.learncamel.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CountryService {

    public  String getCurrentDateTime(){

        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.toString();
    }

}
