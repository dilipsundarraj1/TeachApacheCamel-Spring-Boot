package com.learncamel.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
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
import java.util.Properties;

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
public class KafkaRouteTest extends CamelTestSupport{


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
        return new KafkaRoute();
    }

    @Autowired
    Environment environment;

    @Before
    public void setUp(){

    }

   @Test
    public void kafkaRoute(){

       String input = "{\"transactionType\":\"ADD\", \"sku\":\"200\", \"itemDescription\":\"SamsungTV\", \"price\":\"500\"}";

       String response = (String) producerTemplate.requestBody("kafka:inputItemTopic?brokers=localhost:9092",input);

       System.out.println("Response : " + response);
       assertNotNull(response);

   }

    @Test
    public void kafkaRoute_Error(){

        String input = "{\"transactionType\":\"ADD\", \"sku\":\"\", \"itemDescription\":\"SamsungTV\", \"price\":\"500\"}";

        producerTemplate.sendBody("kafka:inputItemTopic?brokers=localhost:9092",input);
       // String response = (String) producerTemplate.requestBody("kafka:inputItemTopic?brokers=localhost:9092",input);

        String response = (String) consumerTemplate.receiveBody("kafka:errorTopic?brokers=localhost:9092");

        System.out.println("response : " + response);

        assertNotNull(response);

    }

}
