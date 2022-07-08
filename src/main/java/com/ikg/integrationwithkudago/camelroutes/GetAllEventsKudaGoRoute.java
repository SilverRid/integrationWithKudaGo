package com.ikg.integrationwithkudago.camelroutes;

import static org.springframework.http.HttpMethod.GET;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;

/*****

 @author Sergei Iurochkin
 @link https://github.com/SilverRid

 */
public class GetAllEventsKudaGoRoute extends RouteBuilder {
    private String request = "kudago.com/public-api/v1.4/event-categories";

    @Override
    public void configure() throws Exception {

        from("direct:getAllEventsKudago")
            .setHeader(Exchange.HTTP_METHOD).constant(GET)
            .routeId("getAllEventsKudago")
            .log("Http Route started: \n ${headers}\n ${body} \n" + request)
            .to("direct:countinue").log("HTTP OK");

        from("direct:countinue")
            .routeId("CONTINUE")
            .to("https://" + request)
            .log(LoggingLevel.INFO, "Response :  \n ${headers}\n ${body} \n")
            .log("CONTINUE OK");

    }
}
