package com.learncamel.routes;

import com.learncamel.alert.MailProcessor;
import com.learncamel.exception.DataException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by z001qgd on 1/3/18.
 */
@Component
public class SimpleCamelRoute extends RouteBuilder{

    @Autowired
    Environment environment;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    MailProcessor mailProcessor;


    @Override
    public void configure() throws Exception {

        onException(PSQLException.class).log(LoggingLevel.ERROR,"PSQLException in the route ${body}")
                .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);

        onException(DataException.class,RuntimeException.class).log(LoggingLevel.ERROR, "DataException in the route ${body}")
                .process(mailProcessor);


        from("{{fromRoute}}")
                    .log("Current Environment is "+ environment.getProperty("message"))
                .to("{{toRoute}}");

        }
}
