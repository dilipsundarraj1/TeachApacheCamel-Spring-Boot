package com.learncamel.routes;

import com.learncamel.alert.MailProcessor;
import com.learncamel.domain.Country;
import com.learncamel.exception.DataException;
import com.learncamel.processor.BuildSQLProcessor;
import com.learncamel.processor.RequestXMLBuildProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.model.dataformat.XStreamDataFormat;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by z001qgd on 1/3/18.
 */
@Component
public class SoapCamelRoute extends RouteBuilder {

    @Autowired
    Environment environment;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    private WebServiceTemplate webServiceTemplate;


    // @Autowired
    MailProcessor mailProcessor;

    @Autowired
    RequestXMLBuildProcessor requestXMLBuildProcessor;

    @Autowired
    BuildSQLProcessor buildSQLProcessor;


    @Override
    public void configure() throws Exception {


        Predicate isNotMock = header("env").isNotEqualTo("mock");

        Map<String, String> reference = new HashMap<String, String>();
        reference.put("FullCountryInfoResult", Country.class.getName());

        XStreamDataFormat xstreamDataFormat = new XStreamDataFormat();
        xstreamDataFormat.setAliases(reference);
        /**
         * Omitted Fields SetUp
         */
        String[] omittedFieldsArr = new String[]{"sCapitalCity", "sPhoneCode", "sContinentCode", "sCurrencyISOCode", "sCountryFlag", "Languages"};
        Map<String, String[]> omitedFields = new HashMap<>();
        omitedFields.put(Country.class.getName(), omittedFieldsArr);
        xstreamDataFormat.setOmitFields(omitedFields);
        xstreamDataFormat.setPermissions(Country.class.getName()); //Need permission on this class otherwise


        onException(PSQLException.class).log(LoggingLevel.ERROR, "PSQLException in the route ${body}")
                .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);

        onException(DataException.class, RuntimeException.class).log(LoggingLevel.ERROR, "DataException in the route ${body}");
        //.process(mailProcessor);


        from("{{fromRoute}}")
                .log("Route Started")
                .choice()
                .when(isNotMock)
                    .log("Inside first choice")
                    .process(requestXMLBuildProcessor)
                    .to("{{toRoute}}")
                    .end()
                    .log("Response from the SOAP call is : ${body}")
                .transform().xpath("/n:FullCountryInfoResponse/n:FullCountryInfoResult", new Namespaces("n", "http://www.oorsprong.org/websamples.countryinfo"))
                .log("xpath first level : ${body}")
                    .convertBodyTo(String.class)
                    .unmarshal(xstreamDataFormat)
                    .log("UnMarshalled  xstream output is : ${body}")
                .choice()
                .when(isNotMock)
                    .process(buildSQLProcessor)
                    .to("{{dbNode}}")
                    .to("{{selectNode}}")
                    .log("Selected Country result is : ${body}")
                    .convertBodyTo(String.class)
                    .log("Inserted Country is : ${body}")
                .end();

    }


}
