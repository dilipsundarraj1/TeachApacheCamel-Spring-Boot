package com.learncamel.alert;


import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by z001qgd on 1/20/18.
 */
@Component
@Slf4j
public class MailProcessor implements Processor {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    Environment environment;

    @Override
    public void process(Exchange exchange) throws Exception {

        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        log.info("Error Message in MailProcessor : " + e.getMessage());

        String messageBody = "Exception happened in the route and the exception is  " + e.getMessage();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("mailFrom"));
        message.setTo(environment.getProperty("mailto"));
        message.setSubject("Exception in Camel Route");
        message.setText(messageBody);

        emailSender.send(message);

    }
}
