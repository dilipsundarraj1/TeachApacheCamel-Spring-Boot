package com.learncamel.routes;

import com.learncamel.exception.DataException;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
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

/**
 * Created by z001qgd on 1/13/18.
 */
@ActiveProfiles("mock")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SimpleCamelRouteMockTest extends CamelTestSupport{


    @Autowired
    private CamelContext context;

    @Autowired
    Environment environment;

    @Autowired
    protected CamelContext createCamelContext() {
        return context;
    }

    @BeforeClass()
    public static void cleanUp() throws IOException {
        FileUtils.cleanDirectory(new File("data/input"));
        FileUtils.deleteDirectory(new File("data/output"));

    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Test
    public void testMoveFileMock() throws InterruptedException, IOException {

        String message = "type,sku#,item description,price\nADD,100,Samsung TV,500.00\nADD,100,LG TV,300.00\n";

        MockEndpoint mockEndpoint = getMockEndpoint(environment.getProperty("toRoute"));
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived(message);


        producerTemplate.sendBodyAndHeader(environment.getProperty("startRoute"),
                message,"env" , environment.getProperty("spring.profiles.active"));

        assertMockEndpointsSatisfied();


    }

    @Test
    public void testMoveFileMockAndDB() throws InterruptedException, IOException {

        String message = "type,sku#,item description,price\nADD,100,Samsung TV,500.00\nADD,100,LG TV,300.00\n";

        MockEndpoint mockEndpoint = getMockEndpoint(environment.getProperty("toRoute"));
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived(message);

        MockEndpoint mockEndpoint1 = getMockEndpoint(environment.getProperty("toRoute2"));
        String outputMessage ="Data Updated Successfully";
        mockEndpoint1.expectedMessageCount(1);
        mockEndpoint1.expectedBodiesReceived(outputMessage);


        producerTemplate.sendBodyAndHeader(environment.getProperty("startRoute"),
                message,"env" , environment.getProperty("spring.profiles.active"));

        assertMockEndpointsSatisfied();


    }

    @Test(expected = CamelExecutionException.class)
    public void testMoveFileMock_DataException() throws InterruptedException, IOException {

        String message = "type,sku#,item description,price\nADD,,Samsung TV,500.00";


        producerTemplate.sendBodyAndHeader(environment.getProperty("startRoute"),
                message,"env" , environment.getProperty("spring.profiles.active"));

    }



}
