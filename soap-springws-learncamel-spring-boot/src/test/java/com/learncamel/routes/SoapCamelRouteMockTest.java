package com.learncamel.routes;

import com.learncamel.domain.Country;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.spring.ws.SpringWebserviceConstants;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by z001qgd on 1/13/18.
 */
@ActiveProfiles("mock")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisableJmx(true)
public class SoapCamelRouteMockTest extends CamelTestSupport{


    @Autowired
    private CamelContext context;

    @Autowired
    protected CamelContext createCamelContext() {
        return context;
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Override
    protected RouteBuilder createRouteBuilder(){
        return new SoapCamelRoute();
    }

    @Autowired
    Environment environment;

    @Before
    public void setUp(){

    }

    private String getCountrybyCountryCodeRequest = "<FullCountryInfo xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
            "      <sCountryISOCode>GB</sCountryISOCode>\n" +
            "    </FullCountryInfo>";

    private String countryWebServiceUri = "http://www.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";


    @Test
    public void countryNameByCountryCode() throws Exception {
        String result = (String) producerTemplate.requestBodyAndHeader("direct:input", getCountrybyCountryCodeRequest,
                SpringWebserviceConstants.SPRING_WS_ENDPOINT_URI, countryWebServiceUri);
        System.out.println("result : " + result);
        assertTrue(result.contains("Great Britain"));
    }

    @Test
    public void parseCountryCode(){

        String input = "<m:FullCountryInfoResponse xmlns:m=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
                "      <m:FullCountryInfoResult>\n" +
                "        <m:sISOCode>IN</m:sISOCode>\n" +
                "        <m:sName>India</m:sName>\n" +
                "        <m:sCapitalCity>New Delhi</m:sCapitalCity>\n" +
                "        <m:sPhoneCode>91</m:sPhoneCode>\n" +
                "        <m:sContinentCode>AS</m:sContinentCode>\n" +
                "        <m:sCurrencyISOCode>INR</m:sCurrencyISOCode>\n" +
                "        <m:sCountryFlag>http://www.oorsprong.org/WebSamples.CountryInfo/Images/India.jpg</m:sCountryFlag>\n" +
                "        <m:Languages>\n" +
                "          <m:tLanguage>\n" +
                "            <m:sISOCode>hin</m:sISOCode>\n" +
                "            <m:sName>Hindi</m:sName>\n" +
                "          </m:tLanguage>\n" +
                "        </m:Languages>\n" +
                "      </m:FullCountryInfoResult>\n" +
                "    </m:FullCountryInfoResponse>";

        Country country = (Country)
        producerTemplate.requestBodyAndHeader(environment.getProperty("fromRoute"),input,"env",environment.getProperty("spring.profiles.active"));

       assertEquals("IN", country.getsISOCode());
    }
}
