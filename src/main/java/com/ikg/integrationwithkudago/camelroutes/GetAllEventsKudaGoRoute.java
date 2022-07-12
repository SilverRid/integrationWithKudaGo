package com.ikg.integrationwithkudago.camelroutes;

import static org.springframework.http.HttpMethod.GET;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

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
//            .setHeader(Exchange.HTTP_QUERY, constant("lang=&fields=&expand=&order_by=&text_format=&ids=&location=&actual_since=1444385206&actual_until=&is_free=&categories=cinema&lon=&lat=&radius="))
            .log("HTTP_QUERY >>>>>>>>>> \n ${headers}\n ")
            .to("https://" + REQUEST)
            .log(LoggingLevel.INFO, "Response :  \n ${headers}\n ${body} \n")
            .log("CONTINUE OK ");

    }
}
