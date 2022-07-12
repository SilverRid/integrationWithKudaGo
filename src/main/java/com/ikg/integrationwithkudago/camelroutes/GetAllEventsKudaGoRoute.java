package com.ikg.integrationwithkudago.camelroutes;

import static org.springframework.http.HttpMethod.GET;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.http.HttpStatus;

/*****

 @author Sergei Iurochkin
 @link https://github.com/SilverRid

 */
public class GetAllEventsKudaGoRoute extends RouteBuilder {
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
            .process(this::checkRequeest)
            .log(LoggingLevel.INFO, "Response :  \n ${headers}\n ${body} \n")
            .log("CONTINUE OK ");

    }

    private void checkRequeest(final Exchange exchange) {
        Message message = exchange.getMessage();
        String statusResponce = String.valueOf(message.getHeader("CamelHttpResponseCode"));

        if (!statusResponce.equals("200")) {
            Message newMessage = new DefaultMessage(exchange);
            newMessage.setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.OK.value());
            newMessage.setBody("Wrong response!!!!");
            exchange.setMessage(newMessage);
        }
    }
}
