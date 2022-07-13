package com.ikg.integrationwithkudago.camelroutes;

import java.util.ArrayList;
import static org.springframework.http.HttpMethod.GET;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikg.integrationwithkudago.entity.Event;
import com.ikg.integrationwithkudago.entity.AllEvents;
import com.ikg.integrationwithkudago.services.GetAllEventsServices;
import com.ikg.integrationwithkudago.testdb.MyDb;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.support.DefaultMessage;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/*****

 @author Sergei Iurochkin
 @link https://github.com/SilverRid

 */
@Component
public class GetAllEventsKudaGoRoute extends RouteBuilder {
    @Autowired
    MyDb myDb;
    private final String REQUEST = "kudago.com/public-api/v1.4/events/";
    @Override
    public void configure() throws Exception {

        from("direct:getAllEventsKudago")
            .setHeader(Exchange.HTTP_METHOD).constant(GET)
            .routeId("getAllEventsKudago")
            .log("Http Route started: \n ${headers}\n ${body} \n" + REQUEST)
            .to("direct:countinueGetAllEventsKudago")
            .log("HTTP OK");

        from("direct:countinueGetAllEventsKudago")
            .routeId("CONTINUE >>> GetAllEventsKudago")
            .log("HTTP_QUERY >>>>>>>>>> \n ${headers}\n ")
            .to("https://" + REQUEST + "?throwExceptionOnFailure=false")
            .log("After GET REQUEST ${body} ${headers}")
            .choice()
            .when(exchange -> exchange.getMessage().getHeader("CamelHttpResponseCode").equals(HttpStatus.OK.value()))
            .unmarshal(new JacksonDataFormat(AllEvents.class))
            .log(LoggingLevel.INFO, "Response :  \n ${headers}\n ${body} \n")
            .process(this::checkRequeest)
            .log("After GET PROCESS ${body} ${headers}")
            .log("ATFTER 1 WHEN OK")
            .otherwise()
            .log(LoggingLevel.INFO, "AFTER RESPONCE BAD :  \n ${headers}\n ${body} \n")
            .log("CONTINUE OK ");

    }

    private void checkRequeest(final Exchange exchange) throws JsonProcessingException {
        Message message = exchange.getMessage();
        String statusResponce = String.valueOf(message.getHeader("CamelHttpResponseCode"));

        if (!statusResponce.equals("200")) {
            Message newMessage = new DefaultMessage(exchange);
            newMessage.setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value());
            newMessage.setHeader(Exchange.CONTENT_TYPE, ContentType.APPLICATION_JSON);
            newMessage.setBody(new ObjectMapper().writeValueAsString(new AllEvents()));
            exchange.setMessage(newMessage);
        } else {
            System.out.println(exchange.getIn().getBody(AllEvents.class));
            myDb.getAllEvents().add( (AllEvents) exchange.getIn().getBody());
        }
    }
}
