package com.learncamel.routes;

import com.learncamel.processor.HealthCheckProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by z001qgd on 2/11/18.
 */
@ActiveProfiles("mock")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HealthCheckMockTest extends CamelTestSupport{

    @Override
    public RouteBuilder createRouteBuilder(){
        return new HealthCheckRoute();
    }

    @Autowired
    CamelContext context;

    @Autowired
    Environment environment;

    @Autowired
    protected CamelContext createCamelContext(){

        return context;
    }

    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    HealthCheckProcessor healthCheckProcessor;

    @Before
    public void setUp(){


    }



    @Test
    public void healthRouteTest(){

        String input =" {\"status\":\"DOWN\",\"camel\":{\"status\":\"UP\",\"name\":\"camel-1\",\"version\":\"2.20.1\",\"contextStatus\":\"Started\"},\"camel-health-checks\":{\"status\":\"UP\",\"route:healthRoute\":\"UP\",\"route:mainRoute\":\"UP\"},\"mail\":{\"status\":\"UP\",\"location\":\"smtp.gmail.com:587\"},\"diskSpace\":{\"status\":\"UP\",\"total\":499071844352,\"free\":192566607872,\"threshold\":10485760},\"db\":{\"status\":\"DOWN\",\"error\":\"org.springframework.jdbc.CannotGetJdbcConnectionException: Could not get JDBC Connection; nested exception is org.postgresql.util.PSQLException: Connection to localhost:54321 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.\"}}";


        String response = (String) producerTemplate.requestBodyAndHeader(environment.getProperty("healthRoute"), input,"env", environment.getProperty("spring.profiles.active"));

        System.out.println("Response :  " + response);

        String expectedMessae = "status component in the route is Down\n" +
                "db component in the route is Down\n";

        assertEquals(expectedMessae,response);
    }

}
