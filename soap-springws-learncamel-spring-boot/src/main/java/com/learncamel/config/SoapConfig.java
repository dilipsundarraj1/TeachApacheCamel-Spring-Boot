package com.learncamel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class SoapConfig {

    @Bean(value = "webServiceTemplate")
    public WebServiceTemplate createWebServiceTemplate(){
        WebServiceTemplate template = new WebServiceTemplate();
        template.setDefaultUri("http://www.oorsprong.org/websamples.countryinfo/CountryInfoService.wso");
        return template;
    }

   /* @Bean(value = "cdataTransformer")
    public CDataTransformer cDataTransformer(){
        return new CDataTransformer();
    }*/
}
