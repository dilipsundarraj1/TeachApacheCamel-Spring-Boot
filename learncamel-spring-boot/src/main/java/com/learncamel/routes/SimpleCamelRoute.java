package com.learncamel.routes;

import com.learncamel.alert.MailProcessor;
import com.learncamel.domain.Item;
import com.learncamel.exception.DataException;
import com.learncamel.processor.BuildSQLProcessor;
import org.apache.camel.LoggingLevel;
import com.learncamel.processor.SuccessMessageProcessor;
import org.apache.camel.Predicate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
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
    BuildSQLProcessor buildSQLProcessor;

    @Autowired
    SuccessMessageProcessor successMessageProcessor;

    @Autowired
    MailProcessor mailProcessor;

    @Override
    public void configure() throws Exception {

        DataFormat bindy = new BindyCsvDataFormat(Item.class);

        Predicate isNotDev =  header("env").isNotEqualTo("mock");

     /* errorHandler(defaultErrorHandler().log("Error Occured"));*/

      //Retry without any delay
       /* errorHandler(defaultErrorHandler().log("Error Occured").maximumRedeliveries(2)
                .retryAttemptedLogLevel(LoggingLevel.ERROR));*/

        //Retry with  delay
      /* errorHandler(defaultErrorHandler().log("Error Occured").maximumRedeliveries(2)
        .redeliveryDelay(3000).retryAttemptedLogLevel(LoggingLevel.ERROR));*/

        /**
         * // showProperties -> this will show complete set of properties in the exchange.
         * With DeadLetter Channel you can publish the message directly to a jms queue or to any other source.
         *
         */
       // errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showProperties=true"));// showProperties -> this will show complete set of properties in the exchange.
       /* errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showProperties=true")
                .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR));*/

       // Exception Policies : The main advantage is that it catch some particular exception and handle it and it can
        // even detour a message to a specific route.
        /*onException(PSQLException.class).log("PSQLException in the route").maximumRedeliveries(2)
                .redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR).process(errorProcessor).to("{{error}}");
*/

/*
        errorHandler(deadLetterChannel("file:data/error?fileName=ErrorFile.txt"));
*/

        onException(DataException.class).log("DataException in the route" ).process(mailProcessor);

        onException(PSQLException.class).log("PSQLException in the route" ).process(mailProcessor);

        onException(RuntimeException.class).log("RuntimeException in the route").maximumRedeliveries(2)
                .redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);

        from("{{startRoute}}").routeId("timerRoute")
                .log("Timer Triggered " + environment.getProperty("message") +" ${body} and headers are ${headers} ")
                .choice() // Content based EIP
                    .when(isNotDev) // not dev check
                        .pollEnrich("{{fromRoute}}" ) // read the file from the directory
                    .otherwise()
                         .log("otherwise flow and the body is ${body}")
                    .end()
                .to("{{toRoute}}") // copy the file to the destination
                .unmarshal(bindy)
                .split(body())
              //  .split(body()).parallelProcessing()
                    .process(buildSQLProcessor)
                        .to("{{toRoute1}}")
                .end()
                .process(successMessageProcessor)
                .log("Body from Success Message Processor is ${body}")
                .to("{{toRoute2}}");

        }
}
