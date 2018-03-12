package com.learncamel.processor;

import com.learncamel.domain.Country;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BuildSQLProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Country country = (Country) exchange.getIn().getBody();

        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO COUNTRIES (NAME, COUNTRY_CODE) VALUES ('");
        query.append(country.getName()+"','"+country.getCountrycode()+"');");
        log.info("Final Query is : " + query);
        exchange.getIn().setBody(query.toString());
        exchange.getIn().setHeader("countryCode",country.getCountrycode());

    }


}
