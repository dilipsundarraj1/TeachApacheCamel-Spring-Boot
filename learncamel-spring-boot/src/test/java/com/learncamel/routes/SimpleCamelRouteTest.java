package com.learncamel.routes;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.commons.io.FileUtils;
import org.junit.*;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Dilip on 1/4/18.
 */
@ActiveProfiles("dev")
@RunWith(CamelSpringBootRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@Ignore
public class SimpleCamelRouteTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Autowired
    Environment environment;

    @BeforeClass()
    public static void startCleanUp() throws IOException {
            FileUtils.cleanDirectory(new File("data/input"));
            FileUtils.deleteDirectory(new File("data/output"));
            FileUtils.deleteDirectory(new File("data/error"));
    }

   /* @AfterClass
    public static void afterCleanUp() throws IOException {
        FileUtils.cleanDirectory(new File("data/input"));
        FileUtils.deleteDirectory(new File("data/output"));
    }*/

    @Test
    public void testMoveFile() throws InterruptedException, IOException {

        String message = "type,sku#,item description,price\nADD,100,Samsung TV,500.00\nADD,101,LG TV,300.00\n";
        String fileName = "fileTest.txt";

        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute"),
                message, Exchange.FILE_NAME, fileName);

        Thread.sleep(3000);
        File file = new File("data/output/"+fileName);
        String output = new String(Files.readAllBytes(Paths.get("data/output/fileTest.txt")));
        assertTrue(file.exists());
        assertEquals(message,output);

    }

    @Test
    public void testMoveFile_ADD() throws InterruptedException, IOException {

        String message = "type,sku#,item description,price\nADD,100,Samsung TV,500.00\nADD,101,LG TV,300.00\n";
        String fileName = "fileAdd.txt";

        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute"),
                message, Exchange.FILE_NAME, fileName);

        Thread.sleep(3000);
        System.out.println("output directory is : " + environment.getProperty("toRoute"));
        File file = new File("data/output/"+fileName);
        assertTrue(file.exists());
        String outputMessage ="Data Updated Successfully";
        String outputSuccessMessage = new String(Files.readAllBytes(Paths.get("data/output/Success.txt")));
        assertEquals(outputMessage,outputSuccessMessage);


    }


    @Test
    public void testMoveFile_EXCEPTION() throws InterruptedException, IOException {

        String message = "type,sku#,item description,price\nADD,,Samsung TV,500.00";
        String fileName = "fileAdd.txt";

        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute"),
                message, Exchange.FILE_NAME, fileName);

        Thread.sleep(3000);
        System.out.println("output directory is : " + environment.getProperty("toRoute"));
        File file = new File("data/output/"+fileName);
        assertTrue(file.exists());
        File errorFile = new File("data/input/error");
        assertTrue(file.exists());

    }

    @Test
    public void testMoveFile_UPDATE() throws InterruptedException, IOException {

        String message = "type,sku#,item description,price\nUPDATE,100,Samsung TV,600.00";

        String fileName = "fileUpdate.txt";
        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute"),
                message, Exchange.FILE_NAME, fileName);

        Thread.sleep(6000);
        File file = new File("data/output/"+fileName);
        assertTrue(file.exists());
        String outputMessage ="Data Updated Successfully";
        String outputSuccessMessage = new String(Files.readAllBytes(Paths.get("data/output/Success.txt")));
        assertEquals(outputMessage,outputSuccessMessage);

    }

    @Test
    public void testMoveFile_DELETE() throws InterruptedException, IOException {

        String message = "type,sku#,item description,price\nDELETE,100,Samsung TV,600.00";
        String fileName = "fileDelete.txt";

        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRoute"),
                message, Exchange.FILE_NAME, fileName);

        Thread.sleep(6000);
        File file = new File("data/output/"+fileName);
        assertTrue(file.exists());
        String outputMessage ="Data Updated Successfully";
        String outputSuccessMessage = new String(Files.readAllBytes(Paths.get("data/output/Success.txt")));
        assertEquals(outputMessage,outputSuccessMessage);

    }


}
