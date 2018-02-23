package com.learncamel.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by z001qgd on 2/10/18.
 */
@Component
@Slf4j
public class HealthCheckProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String healthCheckResult = (String) exchange.getIn().getBody(String.class);

        log.info("Health String of the APP is" + healthCheckResult);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(healthCheckResult, new TypeReference<Map<String,Object>>(){});
        StringBuilder builder = null;

        for(String  key : map.keySet()){

            if(map.get(key).toString().contains("DOWN")){

                if(builder==null)
                    builder = new StringBuilder();

                builder.append(key+ " component in the route is Down\n");
            }
        }

        if(builder!=null){
            log.info("Exception Message is" + builder.toString());
            exchange.getIn().setHeader("error", true);
            exchange.getIn().setBody(builder.toString());
            exchange.setProperty(Exchange.EXCEPTION_CAUGHT,builder.toString());
        }


    }
}
