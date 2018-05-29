package com.learncamel.routes;

import com.learncamel.alert.MailProcessor;
import com.learncamel.exception.DataException;
import com.learncamel.processor.BuildSQLProcessor;
import com.learncamel.processor.CountrySelectProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by z001qgd on 1/3/18.
 */
@Component
public class RestCamelRoute extends RouteBuilder{

    @Autowired
    Environment environment;



    @Autowired
    MailProcessor mailProcessor;

    @Autowired
    CountrySelectProcessor countrySelect;


    @Override
    public void configure() throws Exception {

        onException(PSQLException.class).log(LoggingLevel.ERROR,"PSQLException in the route ${body}")
                .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);

        onException(DataException.class,RuntimeException.class).log(LoggingLevel.ERROR, "DataException in the route ${body}")
                .process(mailProcessor);

        from("{{fromRoute}}")
                .process(countrySelect)
                    .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                    .setHeader(Exchange.HTTP_URI, simple("http://restcountries.eu/rest/v2/alpha/${header.countryId}"))
                    .log("Headers are : ${headers}")
                .to("https://restcountries.eu/rest/v2/alpha/us").convertBodyTo(String.class)
                    .log("The REST API resopnse is : ${body}")
                    .removeHeader(Exchange.HTTP_URI)
                    .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .to("{{toRoute}}");


        }
}
