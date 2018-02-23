package com.learncamel.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by z001qgd on 1/4/18.
 */
@ActiveProfiles("dev")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class ActiveMQRouteTest extends CamelTestSupport{


    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext() {
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private  ConsumerTemplate consumerTemplate;

    @Override
    protected RouteBuilder createRouteBuilder(){
        return new ActiveMQRoute();
    }

    @Autowired
    Environment environment;

    @Before
    public void setUp(){

    }

   @Test
    public void activeMQRoute(){
       String input = "{\"transactionType\":\"ADD\", \"sku\":\"200\", \"itemDescription\":\"SamsungTV\", \"price\":\"500\"}";

       ArrayList response = (ArrayList) producerTemplate.requestBody("activemq:inputItemQueue",input);
       System.out.println("Response is  : " + response);
       assertNotNull(response);

   }

    @Test(expected = CamelExecutionException.class)
    public void activeMQRoute_Exception(){
        String input = "{\"transactionType\":\"ADD\", \"sku\":\"\", \"itemDescription\":\"SamsungTV\", \"price\":\"500\"}";

        String response = (String) producerTemplate.requestBody("activemq:inputItemQueue",input);

    }

}
