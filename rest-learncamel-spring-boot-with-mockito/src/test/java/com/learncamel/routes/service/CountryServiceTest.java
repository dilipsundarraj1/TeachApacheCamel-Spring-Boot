package com.learncamel.routes.service;

import com.learncamel.service.CountryService;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("dev")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CountryServiceTest {

    @Autowired
    CountryService countryService;

    @Test
    public void getCountryDetails(){
        String responseBody = countryService.getCountryDetails("us");
        System.out.println("responseBody : " + responseBody);
        assertNotNull(responseBody);
        assertTrue(responseBody.contains("United States of America"));
    }

}
