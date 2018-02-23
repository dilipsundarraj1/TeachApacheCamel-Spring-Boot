package com.learncamel.routes;

import com.learncamel.domain.Item;
import com.learncamel.exception.DataException;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

/**
 * Created by z001qgd on 1/13/18.
 */
@ActiveProfiles("mock")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisableJmx(true)
public class KafkaRouteMockTest extends CamelTestSupport{


    @Autowired
    private CamelContext context;

    @Autowired
    Environment environment;


    @Autowired
    protected CamelContext createCamelContext() {
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    protected RouteBuilder createRouteBuilder(){
        return new KafkaRoute();
    }

    @Before
    public void setUp(){

    }

    /**
     * Invalid because the body is changes with query
     * @throws InterruptedException
     */
    @Test
    public void unMarshalItem_success() throws InterruptedException {
        String input = "{\"transactionType\":\"ADD\", \"sku\":\"100\", \"itemDescription\":\"SamsungTV\", \"price\":\"500\"}";

        String output = "INSERT INTO ITEMS (SKU, ITEM_DESCRIPTION,PRICE) VALUES ('100','SamsungTV',500);";
        String item = (String) producerTemplate.requestBodyAndHeader(environment.getProperty("fromRoute"),input
                ,"env","mock");

        assertEquals(output,item);



    }

    @Test(expected = CamelExecutionException.class)
    @Ignore
    public void unMarshalItem_failure() throws InterruptedException {
        String input = "{\"transactionType\":\"ADD\", \"sku\":\"\", \"itemDescription\":\"SamsungTV\", \"price\":\"500\"}";


        Item item = (Item) producerTemplate.requestBodyAndHeader(environment.getProperty("fromRoute"),input,"env","mock");

    }


}
