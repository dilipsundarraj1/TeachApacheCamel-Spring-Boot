package com.learncamel.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Created by Dilip on 1/18/18.
 */
@Component
@Slf4j
public class SuccessMessageProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("Data Updated Successfully");
    }
}
