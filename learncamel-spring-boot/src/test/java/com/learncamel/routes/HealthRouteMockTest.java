package com.learncamel.routes;

import com.learncamel.processor.HealthCheckProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
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
 * Created by z001qgd on 2/10/18.
 */
@ActiveProfiles("mock")
@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class HealthRouteMockTest extends CamelTestSupport {

    @Autowired
    protected RouteBuilder createRouteBuilder() {
        return new HealthCheckRoute();
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

   @Autowired
    private CamelContext context;

    @Autowired
    Environment environment;

    @Autowired
    protected CamelContext createCamelContext() {
        return context;
    }

    @Autowired
    HealthCheckProcessor healthCheckProcessor;

    @Before
    public void setUp(){

      //  System.setProperty("healthRoute","direct:health");
    }

    @Test
    public void healthRouteTest() throws InterruptedException {

        String input ="{\"status\":\"UP\",\"camel\":{\"status\":\"DOWN\",\"name\":\"camel-1\",\"version\":\"2.20.1\",\"contextStatus\":\"Started\"},\"camel-health-checks\":{\"status\":\"UP\",\"route:healthRoute\":\"UP\",\"route:timerRoute\":\"UP\"},\"mail\":{\"status\":\"UP\",\"location\":\"smtp.gmail.com:587\"},\"diskSpace\":{\"status\":\"UP\",\"total\":499071844352,\"free\":192335450112,\"threshold\":10485760},\"db\":{\"status\":\"UP\",\"database\":\"PostgreSQL\",\"hello\":1}}";

        String response = (String) producerTemplate.requestBodyAndHeader(environment.getProperty("healthRoute"),input,
                "env" , environment.getProperty("spring.profiles.active"));

        //assertMockEndpointsSatisfied();
        String expectedMessage = "camel component in the route is Down\n";
        assertEquals(expectedMessage,response);

    }

}
