package com.learncamel.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.spring.ws.SpringWebserviceConstants;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class RequestXMLBuildProcessor implements Processor{

    List<String> countryList  = Arrays.asList("US","IN", "GB","CN","JP");

    String countryWebServiceUri = "http://www.oorsprong.org/websamples.countryinfo/CountryInfoService.wso";

    @Override
    public void process(Exchange exchange) throws Exception {

        Random random = new Random();
        String countryCode =  countryList.get(random.nextInt(countryList.size()-1));

        String inputXML =  buildXmlString().replace("ABC",countryCode);

        log.info("Input XML is : " + inputXML );

        exchange.getIn().setBody(inputXML);
exchange.getIn().setHeader(SpringWebserviceConstants.SPRING_WS_ENDPOINT_URI, countryWebServiceUri);



    }

    private  String buildXmlString(){

        return "    <FullCountryInfo xmlns=\"http://www.oorsprong.org/websamples.countryinfo\">\n" +
                "      <sCountryISOCode>ABC</sCountryISOCode>\n" +
                "    </FullCountryInfo>";
    }
}
