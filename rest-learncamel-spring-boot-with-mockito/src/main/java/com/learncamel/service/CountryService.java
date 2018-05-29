package com.learncamel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
public class CountryService {


    @Autowired
    RestTemplate restTemplate;

    public  String getCurrentDateTime(){

        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.toString();
    }


    public String getCountryDetails(String countryCode){

        String url = "https://restcountries.eu/rest/v2/alpha/".concat(countryCode);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response.getBody();
    }



}
